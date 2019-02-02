package com.example.courseanalyzer.analyzer.mandatorycourseanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.mandatorycourseanalyzer
 * @Class: SimpleMandatoryCourseAnalyzer
 * @Author: Jan
 * @Date: 29.01.2019
 */

import com.example.courseanalyzer.Util.CourseLineUtil;
import com.example.courseanalyzer.analyzer.model.Course;
import com.example.courseanalyzer.analyzer.model.CourseType;
import com.example.courseanalyzer.dto.MandatoryCoursesDto;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Analyzes the mandatory courses by simply assuming that each line contains the
 * course informations of the study plan of Computer Science of TU Wien
 * in a consistent order.
 *
 * <p>The order is <i><ects>_<courseType>_<course name></i></p>
 * <p>Every other information is simply ignored</p>
 */
public class SimpleMandatoryCourseAnalyzer implements MandatoryCourseAnalyzer {
    private static final String COURSE_INFORMATION_FORMAT = "\\*?[ ]?[0-9]+,[0,9] [\\w|äöüÄÖÜß]+[\\w|äöüÄÖÜß| ]*";
    private static final String COMMA = ",";
    private static final String DECIMAL_POINT = ".";

    @Override
    public Set<Course> analyzeMandatoryCourses(MandatoryCoursesDto mandatoryCoursesDto) {
        Set<Course> mandatoryCourses = new HashSet<>();
        Scanner scanner =
                new Scanner(mandatoryCoursesDto.getMandatoryCourses());

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (CourseLineUtil.isLineValidCourseInformation(line)) {

                Course courseFromLine = CourseLineUtil.getCourseFromLine(line);

                if (courseFromLine.isInformationComplete()) {
                    mandatoryCourses.add(courseFromLine);
                }
            }
        }
        scanner.close();

        return mandatoryCourses;
    }
}
