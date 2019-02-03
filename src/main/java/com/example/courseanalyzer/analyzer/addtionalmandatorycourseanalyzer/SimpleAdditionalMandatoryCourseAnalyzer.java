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
import com.example.courseanalyzer.dto.MandatoryCoursesDto;

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
    private static final String EXAM_MODULE_TITLE_FORMAT = "Prüfungsfach „[\\w|äöüÄÖÜß| |(|)|:|-]+“?";
    private static final String MANDATORY_COURSE_TITLE = "Pflichtlehrveranstaltungen";
    private static final String ADDITIONAL_MANDATORY_COURSE_TITLE = "Ergänzende Pflichtlehrveranstaltungen";

    @Override
    public TransitionalProvision analyzeAdditionalMandatoryCourses(MandatoryCoursesDto mandatoryCoursesDto) {
        //TODO: refactor
        Scanner scanner = new Scanner(mandatoryCoursesDto.getAdditionalMandatoryCourses());
        TransitionalProvision transitionalProvision = new TransitionalProvision();
        Set<ExamModule> examModules = new HashSet<>();
        ExamModule examModule = null;
        Set<Course> mandatoryCourses = null;
        Set<Course> additionalMandatoryCourses = null;
        boolean isStartMandatoryCourses = false;
        boolean isAdded = false;

        for (String line : getProcessedLines(mandatoryCoursesDto)) {

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
            if (CourseLineUtil.isLineValidCourseInformation(line)) {
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
        if (!isAdded) {
            examModule.setMandatoryCourses(mandatoryCourses);
            examModule.setAdditionalMandatoryCourses(additionalMandatoryCourses);
            examModules.add(examModule);
        }

        transitionalProvision.setExamModules(examModules);
        return transitionalProvision;
    }

    private List<String> getProcessedLines(MandatoryCoursesDto mandatoryCoursesDto) {
        List<String> lines = new ArrayList<>();
        Scanner scanner = new Scanner(mandatoryCoursesDto.getAdditionalMandatoryCourses());

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
}
