package com.example.courseanalyzer.analyzer.addtionalmandatorycourseanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.addtionalmandatorycourseanalyzer
 * @Class: SimpleAdditionalMandatoryCourseAnalyzer
 * @Author: Jan
 * @Date: 02.02.2019
 */

import com.example.courseanalyzer.Util.CourseLineUtil;
import com.example.courseanalyzer.analyzer.model.Course;
import com.example.courseanalyzer.analyzer.model.ExamModule;
import com.example.courseanalyzer.analyzer.model.TransitionalProvision;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
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
 *    <module name></i>) and these are organized into mandatory courses and the
 *    additional mandatory courses.</p>
 * <p>The order of the course is <i><ects>_<courseType>_<course name></i>.</p>
 */
public class SimpleAdditionalMandatoryCourseAnalyzer implements AdditionalMandatoryCourseAnalyzer {

    private static final Logger logger = LogManager.getLogger(SimpleAdditionalMandatoryCourseAnalyzer.class);
    private static final int PAGE_NR = 3;
    private static final String EXAM_MODULE_TITLE_FORMAT = "Prüfungsfach „[\\w|äöüÄÖÜß| |(|)|:|-]+“?";
    private static final String MANDATORY_COURSE_TITLE = "Pflichtlehrveranstaltungen";
    private static final String ADDITIONAL_MANDATORY_COURSE_TITLE = "Ergänzende Pflichtlehrveranstaltungen";
    private PDDocument pdDocument;
    private TransitionalProvision transitionalProvision;

    @Override
    public void analyzeTransitionalProvision(ServletRequest request) {

        try {
            initPdf(request);
            analyzeTransitionalProvision();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initPdf(ServletRequest request) throws IOException {

        this.pdDocument = getWorkBookFromMultiPartRequest(request);

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

    private void analyzeTransitionalProvision() throws IOException {
        PDFTextStripper pdfTextStripper = new PDFTextStripper();

        pdfTextStripper.setStartPage(PAGE_NR);
        pdfTextStripper.setEndPage(PAGE_NR);

        String text = pdfTextStripper.getText(pdDocument);

        analyzeAdditionalMandatoryCourses(text);
    }

    private void analyzeAdditionalMandatoryCourses(String transitionalProvisionText) {
        this.transitionalProvision = new TransitionalProvision();
        Set<ExamModule> examModules = new HashSet<>();
        ExamModule examModule = null;
        Set<Course> mandatoryCourses = null;
        Set<Course> additionalMandatoryCourses = null;
        boolean isStartMandatoryCourses = false;
        boolean isAdded = false;

        for (String line : getProcessedLines(transitionalProvisionText)) {

            if (isStartOfExamModule(line)) {
                examModule = new ExamModule();
                additionalMandatoryCourses = null;
                isAdded = false;
            }

            if (line.equals(MANDATORY_COURSE_TITLE)) {
                isStartMandatoryCourses  = true;
                mandatoryCourses = new HashSet<>();
            } else if (line.equals(ADDITIONAL_MANDATORY_COURSE_TITLE)) {
                isStartMandatoryCourses = false;
                additionalMandatoryCourses = new HashSet<>();
            }
            if (CourseLineUtil.isLineValidCourseWithoutWeeklyHoursInformation(line)) {
                Course course = CourseLineUtil.getCourseFromLine(line);

                if (isStartMandatoryCourses) {
                    mandatoryCourses.add(course);
                } else {
                    additionalMandatoryCourses.add(course);
                }
            } else if(additionalMandatoryCourses != null &&
                    !mandatoryCourses.isEmpty() &&
                    !additionalMandatoryCourses.isEmpty()) {
                examModule.setMandatoryCourses(mandatoryCourses);
                examModule.setAdditionalMandatoryCourses(additionalMandatoryCourses);

                examModules.add(examModule);
                isAdded = true;
                examModule = null;
                additionalMandatoryCourses = null;
                isStartMandatoryCourses = false;
            }
        }

        //if exam module is not added because it is the last, it is added too
        if (!isAdded && mandatoryCourses != null && additionalMandatoryCourses != null) {
            examModule.setMandatoryCourses(mandatoryCourses);
            examModule.setAdditionalMandatoryCourses(additionalMandatoryCourses);
            examModules.add(examModule);
        }

        transitionalProvision.setExamModules(examModules);
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

    @Override
    public TransitionalProvision getTransitionalProvision() {
        return transitionalProvision;
    }
}
