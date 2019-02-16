package com.example.courseanalyzer.analyzer.transitionalprovisionanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.transitionalprovisionanalyzer
 * @Class: SimpleTransitionalProvisionAnalyzer
 * @Author: Jan
 * @Date: 02.02.2019
 */

import com.example.courseanalyzer.analyzer.exception.ReadFileException;
import com.example.courseanalyzer.analyzer.exception.WrongFormatException;
import com.example.courseanalyzer.analyzer.model.CourseGroup;
import com.example.courseanalyzer.util.CourseLineUtil;
import com.example.courseanalyzer.analyzer.model.Course;
import com.example.courseanalyzer.analyzer.model.TransitionalProvision;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extracts the additional mandatory courses for the transitional provisions of
 * TU Wien. It is assumed that the informations are in consistent order.
 *
 * <p>The informations are divided into each exam module(<i>Prüfungsfach
 *    [module name]</i>) and these are organized into mandatory courses and the
 *    additional mandatory courses.</p>
 * <p>The order of the course is <i>[ects]_[courseType]_[course name]</i>.</p>
 */
@Component("SimpleTransitionalProvisionAnalyzer")
public class SimpleTransitionalProvisionAnalyzer implements TransitionalProvisionAnalyzer {

    private static final Logger logger = LogManager.getLogger(SimpleTransitionalProvisionAnalyzer.class);

    private static final int PAGE_NR = 3;

    private static final String EXAM_MODULE_TITLE_FORMAT = "Prüfungsfach „[\\w|äöüÄÖÜß| |(|)|:|-]+“?";

    private static final String MANDATORY_COURSE_TITLE = "Pflichtlehrveranstaltungen";

    private static final String ADDITIONAL_MANDATORY_COURSE_TITLE = "Ergänzende Pflichtlehrveranstaltungen";

    private PDDocument pdDocument;

    private TransitionalProvision transitionalProvision;

    private String fileName;

    private CourseGroup mandatoryCourseGroup;

    private CourseGroup additionalMandatoryCourseGroup;

    @Override
    public void analyzeTransitionalProvision(ServletRequest request) {

        try {
            initPdf(request);

            if (pdDocument == null) {
                return;
            }
            analyzeTransitionalProvision();

            pdDocument.close();
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
            String errorMsg = String.format(
                    "An error occured while reading the transitional provision file",
                    fileName != null ? fileName : "");
            throw new ReadFileException(errorMsg, e);
        }
    }

    private void initPdf(ServletRequest request) throws IOException {

        this.pdDocument = getWorkBookFromMultiPartRequest(request);

        if (pdDocument == null) {
            return;
        }

        PDPage page = new PDPage();

        this.pdDocument.addPage(page);
        validatePDF();
    }

    private PDDocument getWorkBookFromMultiPartRequest(ServletRequest request) throws IOException {
        //TODO: extract method-SimpleFinishedCoursesAnalyzer uses similar method too
        MultipartHttpServletRequest multiPartRequest = (MultipartHttpServletRequest) request;
        multiPartRequest.getParameterMap();

        Iterator<String> iterator = multiPartRequest.getFileNames();

        // Only one file is uploaded
        if (iterator.hasNext()) {
            this.fileName = iterator.next();
            logger.debug("File %s is uploaded", fileName != null ? fileName : "");
            MultipartFile multipartFile = multiPartRequest.getFile(fileName);

            return PDDocument.load(multipartFile.getInputStream());
        }
        return null;
    }

    private void validatePDF() {
        if (pdDocument.getNumberOfPages() == 1) {
            throw new ReadFileException("The transitional provision file only contains 1 page.");
        }
    }

    private void analyzeTransitionalProvision() throws IOException, WrongFormatException {
        PDFTextStripper pdfTextStripper = new PDFTextStripper();

        pdfTextStripper.setStartPage(PAGE_NR);
        pdfTextStripper.setEndPage(PAGE_NR);

        String text = pdfTextStripper.getText(pdDocument);
        if (text == null || text.isEmpty()) {
            throwExtractInformationException();
        }
        analyzeAdditionalMandatoryCourses(text);
    }

    private void throwExtractInformationException() throws WrongFormatException {
        String errorMsg = String.format(
                "The text on the page %d in the passed file %s is empty.",
                PAGE_NR,
                fileName != null ? fileName : "");

        logger.error(errorMsg);
        throw new WrongFormatException(errorMsg);
    }

    private void analyzeAdditionalMandatoryCourses(String transitionalProvisionText) {
        //TODO: refactor
        this.transitionalProvision = new TransitionalProvision();
        Set<CourseGroup> mandatoryCourseGroups = new HashSet<>();
        Set<CourseGroup> additionalMandatoryCourseGroups = new HashSet<>();

        boolean isStartMandatoryCourses = false;

        for (String line : getProcessedLines(transitionalProvisionText)) {

            if (isStartOfExamModule(line)) {
                additionalMandatoryCourseGroup = null;
            }

            if (line.equals(MANDATORY_COURSE_TITLE)) {
                isStartMandatoryCourses = true;
            } else if (line.equals(ADDITIONAL_MANDATORY_COURSE_TITLE)) {
                isStartMandatoryCourses = false;
            }

            if (CourseLineUtil.isLineValidCourseWithoutWeeklyHoursInformation(line)) {
                Course course = CourseLineUtil.getCourseFromLine(line);
                //TODO: regex
                if (line.contains("•")) {
                    if (isStartMandatoryCourses) {
                        if (isMandatoryCourseGroupFilled()) {
                            mandatoryCourseGroups.add(mandatoryCourseGroup);
                        }
                        mandatoryCourseGroup = new CourseGroup();
                    } else {
                        if (isAdditionalMandatoryCourseGroupFilled()) {
                            additionalMandatoryCourseGroups.add(additionalMandatoryCourseGroup);
                        }
                        additionalMandatoryCourseGroup = new CourseGroup();
                    }
                }

                if (isStartMandatoryCourses) {
                    mandatoryCourseGroup.addCourse(course);
                } else {
                    additionalMandatoryCourseGroup.addCourse(course);
                }
            } else if (isMandatoryCourseGroupFilled() &&
                    isAdditionalMandatoryCourseGroupFilled()) {

                additionalMandatoryCourseGroups.add(additionalMandatoryCourseGroup);
                isStartMandatoryCourses = false;
            } else if (isMandatoryCourseGroupFilled()) {
                mandatoryCourseGroups.add(mandatoryCourseGroup);
            }
        }

        transitionalProvision.setMandatoryCourseGroups(mandatoryCourseGroups);
        transitionalProvision.setAdditionalMandatoryCourseGroups(additionalMandatoryCourseGroups);
    }

    private List<String> getProcessedLines(String transitionalProvision) {
        List<String> lines = new ArrayList<>();
        Scanner scanner = new Scanner(transitionalProvision);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.contains("+")) {
                String[] splittedLines = line.split(" \\+ ");

                for (String splittedLine : splittedLines) {
                    lines.add(splittedLine);
                }
            } else {
                lines.add(line);
            }
        }

        return lines;
    }

    private boolean isStartOfExamModule(String line) {
        Pattern pattern = Pattern.compile(EXAM_MODULE_TITLE_FORMAT);
        Matcher matcher = pattern.matcher(line);

        return matcher.matches();
    }

    private boolean isMandatoryCourseGroupFilled() {
        return mandatoryCourseGroup != null && !mandatoryCourseGroup.isCourseGroupEmpty();
    }

    private boolean isAdditionalMandatoryCourseGroupFilled() {
        return additionalMandatoryCourseGroup != null && !additionalMandatoryCourseGroup.isCourseGroupEmpty();
    }

    @Override
    public TransitionalProvision getTransitionalProvision() {
        return transitionalProvision;
    }
}
