package com.example.courseanalyzer.analyzer.studyplananalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.studyplananalyzer
 * @Class: SimpleStudyPlanAnalyzer
 * @Author: Jan
 * @Date: 03.02.2019
 */

import com.example.courseanalyzer.analyzer.studyplananalyzer.mandatorycourseanalyzer.MandatoryCourseAnalyzer;
import com.example.courseanalyzer.analyzer.studyplananalyzer.mandatorycourseanalyzer.SimpleMandatoryCourseAnalyzer;
import com.example.courseanalyzer.analyzer.model.Course;
import com.example.courseanalyzer.analyzer.studyplananalyzer.model.Module;
import com.example.courseanalyzer.analyzer.studyplananalyzer.model.Chapter;
import com.example.courseanalyzer.analyzer.studyplananalyzer.model.TableOfContent;
import com.example.courseanalyzer.analyzer.studyplananalyzer.moduleanalyzer.ModuleAnalyzer;
import com.example.courseanalyzer.analyzer.studyplananalyzer.moduleanalyzer.SimpleModuleAnalyzer;
import com.example.courseanalyzer.analyzer.studyplananalyzer.transferableskillsanalyzer.SimpleTransferableSkills;
import com.example.courseanalyzer.analyzer.studyplananalyzer.transferableskillsanalyzer.TransferableSkillsAnalyzer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletRequest;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * Analyzes the study plan for the computer science curriculum of the TU Wien.
 * This analysis heavily relies on the formatting of each page.
 *
 * <p>Each step of the analysis can be exchanged with a new implementation, if
 *    necessary.</p>
 *
 */
public class SimpleStudyPlanAnalyzer implements StudyPlanAnalyzer {

    private static final Logger logger = LogManager.getLogger(SimpleStudyPlanAnalyzer.class);
    private static final String CHAPTER_TITLE_SUGGSTED_STUDY_COURSE = "Semestereinteilung der Lehrveranstaltungen";
    private static final String CHAPTER_TITLE_TRANSFERABLE_SKILL = "Wahlfachkatalog ”Transferable Skills“";
    private static final String CHAPTER_TITLE_EXAM_COURSES_MODULES = "Prüfungsfächer mit den zugeordneten Modulen und Lehrveranstaltungen";
    private static final int PAGE_NR_TABLE_OF_CONTENT = 2;
    private PDDocument pdDocument;
    private TableOfContent tableOfContent;
    private MandatoryCourseAnalyzer mandatoryCourseAnalyzer;
    private ModuleAnalyzer moduleAnalyzer;
    private TransferableSkillsAnalyzer transferableSkillsAnalyzer;
    private Set<Course> mandatoryCourses;
    private Set<Module> modules;
    private Set<Course> transferableSkills;

    public SimpleStudyPlanAnalyzer() {
        this.mandatoryCourseAnalyzer = new SimpleMandatoryCourseAnalyzer();
        this.moduleAnalyzer = new SimpleModuleAnalyzer();
        this.transferableSkillsAnalyzer = new SimpleTransferableSkills();
    }

    @Override
    public void analyzeStudyPlan(ServletRequest request) {
        try {
            initPdf(request);
            retrieveTableOfContent();
            analyzeMandatoryCourses();
            analyzeModules();
            analyzeTransferableSkills();
        } catch (IOException e) {
            //TODO: exception handling
            e.printStackTrace();
        }
    }

    private void initPdf(ServletRequest request) throws IOException {

        this.pdDocument = getWorkBookFromMultiPartRequest(request);
        this.tableOfContent = new TableOfContent();

        PDPage page = new PDPage();

        this.pdDocument.addPage(page);
    }

    private PDDocument getWorkBookFromMultiPartRequest(ServletRequest request) throws IOException {
        //TODO: extract method-SimpleCertificateAnalyzer uses similar method too
        MultipartHttpServletRequest multiPartRequest = (MultipartHttpServletRequest) request;
        multiPartRequest.getParameterMap();

        Iterator<String> iterator = multiPartRequest.getFileNames();

        // Only one file is uploaded
        if (iterator.hasNext()) {
            String fileName = iterator.next();
            logger.debug("File %s is uploaded", fileName);
            MultipartFile multipartFile = multiPartRequest.getFile(fileName);

            return PDDocument.load(multipartFile.getInputStream());
        }
        return null;
    }

    private void retrieveTableOfContent() throws IOException {
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        pdfTextStripper.setStartPage(PAGE_NR_TABLE_OF_CONTENT);
        pdfTextStripper.setEndPage(PAGE_NR_TABLE_OF_CONTENT);

        String parsedText = pdfTextStripper.getText(pdDocument);
        Scanner scanner = new Scanner(parsedText);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            tableOfContent.addChapterFromLine(line);
        }

        tableOfContent.determinePageEndsOfAllChapters();
    }

    private void analyzeMandatoryCourses() throws IOException {
        Chapter suggestedStudyCourseChapter = tableOfContent.getChapter(CHAPTER_TITLE_SUGGSTED_STUDY_COURSE);
        PDFTextStripper pdfTextStripper = new PDFTextStripper();

        pdfTextStripper.setStartPage(suggestedStudyCourseChapter.getPageStart());
        pdfTextStripper.setEndPage(suggestedStudyCourseChapter.getPageEnd());

        String text = pdfTextStripper.getText(pdDocument);
        this.mandatoryCourses = mandatoryCourseAnalyzer.analyzeMandatoryCourses(text);

    }

    private void analyzeModules() throws IOException {
        Chapter suggestedStudyCourseChapter = tableOfContent.getChapter(CHAPTER_TITLE_EXAM_COURSES_MODULES);
        PDFTextStripper pdfTextStripper = new PDFTextStripper();

        pdfTextStripper.setStartPage(suggestedStudyCourseChapter.getPageStart());
        pdfTextStripper.setEndPage(suggestedStudyCourseChapter.getPageEnd());

        String text = pdfTextStripper.getText(pdDocument);
        this.modules = moduleAnalyzer.analyzeModule(text);
    }

    private void  analyzeTransferableSkills() throws IOException {
        Chapter transferableSkillsChapter = tableOfContent.getChapter(CHAPTER_TITLE_TRANSFERABLE_SKILL);
        PDFTextStripper pdfTextStripper = new PDFTextStripper();

        pdfTextStripper.setStartPage(transferableSkillsChapter.getPageStart());
        pdfTextStripper.setEndPage(transferableSkillsChapter.getPageEnd());

        String text = pdfTextStripper.getText(pdDocument);
        this.transferableSkills = transferableSkillsAnalyzer.analyzeTransferableSkills(text);
    }

    @Override
    public Set<Course> getMandatoryCourses() {
        return mandatoryCourses;
    }

    @Override
    public Set<Module> getModules() {
        return modules;
    }

    public Set<Course> getTransferableSkills() {
        return transferableSkills;
    }

}
