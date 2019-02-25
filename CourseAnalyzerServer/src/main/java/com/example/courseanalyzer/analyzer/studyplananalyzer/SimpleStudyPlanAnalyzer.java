package com.example.courseanalyzer.analyzer.studyplananalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.studyplananalyzer
 * @Class: SimpleStudyPlanAnalyzer
 * @Author: Jan
 * @Date: 03.02.2019
 */

import com.example.courseanalyzer.analyzer.exception.ReadFileException;
import com.example.courseanalyzer.analyzer.exception.WrongFormatException;
import com.example.courseanalyzer.analyzer.studyplananalyzer.mandatorycoursesanalyzer.MandatoryCoursesAnalyzer;
import com.example.courseanalyzer.analyzer.model.Course;
import com.example.courseanalyzer.analyzer.studyplananalyzer.model.Module;
import com.example.courseanalyzer.analyzer.studyplananalyzer.model.Chapter;
import com.example.courseanalyzer.analyzer.studyplananalyzer.model.TableOfContent;
import com.example.courseanalyzer.analyzer.studyplananalyzer.modulesanalyzer.ModulesAnalyzer;
import com.example.courseanalyzer.analyzer.studyplananalyzer.transferableskillsanalyzer.TransferableSkillsAnalyzer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletRequest;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * Analyzes the study plan for the computer science curriculum of the TU Wien.
 * This analysis heavily relies on the formatting of each page.
 *
 * <p>Each step of the analysis can be exchanged with a new implementation, if
 *    necessary.</p>
 */
@Component("SimpleStudyPlanAnalyzer")
public class SimpleStudyPlanAnalyzer implements StudyPlanAnalyzer {

    private static final Logger logger = LogManager.getLogger(SimpleStudyPlanAnalyzer.class);

    private static final String CHAPTER_TITLE_SUGGSTED_STUDY_COURSE = "Semestereinteilung der Lehrveranstaltungen";

    private static final String CHAPTER_TITLE_TRANSFERABLE_SKILL = "Wahlfachkatalog ”Transferable Skills“";

    private static final String CHAPTER_TITLE_EXAM_COURSES_MODULES = "Prüfungsfächer mit den zugeordneten Modulen und Lehrveranstaltungen";

    private static final int PAGE_NR_TABLE_OF_CONTENT = 2;

    @Autowired
    @Qualifier("SimpleMandatoryCoursesAnalyzer")
    private MandatoryCoursesAnalyzer mandatoryCoursesAnalyzer;

    @Autowired
    @Qualifier("SimpleModulesAnalyzer")
    private ModulesAnalyzer modulesAnalyzer;

    @Autowired
    @Qualifier("SimpleTransferableSkills")
    private TransferableSkillsAnalyzer transferableSkillsAnalyzer;

    private PDDocument pdDocument;

    private TableOfContent tableOfContent;

    private Set<Course> mandatoryCourses;

    private Set<Module> modules;

    private Set<Course> transferableSkills;

    private String fileName;

    @Override
    public void analyzeStudyPlan(MultipartFile multipartFile) {
        try {
        initPdf(multipartFile);
        retrieveTableOfContent();
        analyzeMandatoryCourses();
        analyzeModules();
        analyzeTransferableSkills();

        pdDocument.close();
        } catch (IOException e) {
            String errorMsg = String.format(
                    "An IO problem occured while reading the passed study plan file %s",
                    getFileName());
            logger.error(e.getLocalizedMessage(), e);

            throw new ReadFileException(errorMsg, e);
        }
    }

    @Override
    public void deleteStudyPlanFile() {
        this.pdDocument = null;
        this.fileName = null;
        this.tableOfContent = null;
        mandatoryCourses = null;
        this.modules = null;
        this.transferableSkills = null;
    }

    private void initPdf(MultipartFile multipartFile) throws IOException {

        this.pdDocument =  PDDocument.load(multipartFile.getInputStream());
        this.fileName = multipartFile.getName();
        this.tableOfContent = new TableOfContent();

        validateStudyPlanPDF();

        PDPage page = new PDPage();

        this.pdDocument.addPage(page);
    }

    private void validateStudyPlanPDF() {
        if (pdDocument.getNumberOfPages() == 1) {
            throw new ReadFileException("The study plan file only contains 1 page.");
        }
    }

    private void retrieveTableOfContent() throws IOException {
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        pdfTextStripper.setStartPage(PAGE_NR_TABLE_OF_CONTENT);
        pdfTextStripper.setEndPage(PAGE_NR_TABLE_OF_CONTENT);

        String parsedText = pdfTextStripper.getText(pdDocument);
        Scanner scanner = null;

        if (parsedText.isEmpty()) {
            String errorMsg = String.format(
                    "The table of content on page %d is empty",
                    PAGE_NR_TABLE_OF_CONTENT);
            throw new WrongFormatException(errorMsg);
        }

        scanner = new Scanner(parsedText);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            tableOfContent.addChapterFromLine(line);
        }

        if (!tableOfContent.containsChapter()) {
            String errorMsg = String.format(
                    "The file %s does not contain a valid table of content",
                    fileName);

            throw new WrongFormatException(errorMsg);
        }

        tableOfContent.determinePageEndsOfAllChapters();
    }

    private void analyzeMandatoryCourses() throws IOException {
        Chapter suggestedStudyCourseChapter = tableOfContent.getChapter(CHAPTER_TITLE_SUGGSTED_STUDY_COURSE);
        PDFTextStripper pdfTextStripper = new PDFTextStripper();

        pdfTextStripper.setStartPage(suggestedStudyCourseChapter.getPageStart());
        pdfTextStripper.setEndPage(suggestedStudyCourseChapter.getPageEnd());

        String text = pdfTextStripper.getText(pdDocument);
        mandatoryCoursesAnalyzer.analyzeMandatoryCourses(text);
        this.mandatoryCourses = mandatoryCoursesAnalyzer.getMandatoryCourses();

    }

    private void analyzeModules() throws IOException {
        Chapter suggestedStudyCourseChapter = tableOfContent.getChapter(CHAPTER_TITLE_EXAM_COURSES_MODULES);
        PDFTextStripper pdfTextStripper = new PDFTextStripper();

        pdfTextStripper.setStartPage(suggestedStudyCourseChapter.getPageStart());
        pdfTextStripper.setEndPage(suggestedStudyCourseChapter.getPageEnd());

        String text = pdfTextStripper.getText(pdDocument);

        modulesAnalyzer.analyzeModule(text);

        this.modules = modulesAnalyzer.getModules();
    }

    private void  analyzeTransferableSkills() throws IOException {
        Chapter transferableSkillsChapter = tableOfContent.getChapter(CHAPTER_TITLE_TRANSFERABLE_SKILL);
        PDFTextStripper pdfTextStripper = new PDFTextStripper();

        pdfTextStripper.setStartPage(transferableSkillsChapter.getPageStart());
        pdfTextStripper.setEndPage(transferableSkillsChapter.getPageEnd());

        String text = pdfTextStripper.getText(pdDocument);

        transferableSkillsAnalyzer.analyzeTransferableSkills(text);

        this.transferableSkills = transferableSkillsAnalyzer.getTransferableSkills();
    }

    @Override
    public Set<Course> getMandatoryCourses() {
        return mandatoryCourses;
    }

    @Override
    public Set<Module> getModules() {
        return modules;
    }

    @Override
    public Set<Course> getTransferableSkills() {
        return transferableSkills;
    }

    public String getFileName() {
        return fileName != null ? fileName : "";
    }
}
