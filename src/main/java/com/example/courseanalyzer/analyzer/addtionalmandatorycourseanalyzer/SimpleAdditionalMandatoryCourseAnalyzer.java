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

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
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
    private static final String ADDITIONAL_MANDATORY_COURSE_TITLE = "Ergänzende Pflichtlehrveranstaltungen";

    @Override
    public TransitionalProvision analyzeAdditionalMandatoryCourses(MandatoryCoursesDto mandatoryCoursesDto) {
        Scanner scanner = new Scanner(mandatoryCoursesDto.getAdditionalMandatoryCourses());
        TransitionalProvision transitionalProvision = new TransitionalProvision();
        Set<ExamModule> examModules = new HashSet<>();
        ExamModule examModule = null;
        Set<Course> additionalMandatoryCourses = null;
        boolean isStartAdditionalMandatoryCourses = false;
        boolean isAdded = false;


        while (scanner.hasNextLine()) {
            //TODO: refactor
            String line = scanner.nextLine().trim();

            if (isStartOfExamModule(line)) {
                examModule = new ExamModule();
                additionalMandatoryCourses = null;
                isAdded = false;
            }

            if(line.equals(ADDITIONAL_MANDATORY_COURSE_TITLE)) {
                isStartAdditionalMandatoryCourses = true;
                additionalMandatoryCourses = new HashSet<>();
            }

            if (isStartAdditionalMandatoryCourses && CourseLineUtil.isLineValidCourseInformation(line)) {
                Course course = CourseLineUtil.getCourseFromLine(line);
                additionalMandatoryCourses.add(course);
            } else if(additionalMandatoryCourses != null && !additionalMandatoryCourses.isEmpty()) {
                examModule.setAdditionalMandatoryCourses(additionalMandatoryCourses);

                examModules.add(examModule);
                isAdded = true;
                examModule = null;
                additionalMandatoryCourses = null;
                isStartAdditionalMandatoryCourses = false;
            }
        }

        //if exam module is not added because it is the last, it is added too
        if (!isAdded) {
            examModule.setAdditionalMandatoryCourses(additionalMandatoryCourses);
            examModules.add(examModule);
        }

        transitionalProvision.setExamModules(examModules);
        return transitionalProvision;
    }

    private boolean isStartOfExamModule(String line) {
        Pattern pattern = Pattern.compile(EXAM_MODULE_TITLE_FORMAT);
        Matcher matcher = pattern.matcher(line);

        return matcher.matches();
    }
}
