package com.example.courseanalyzer.analyzer.studyplananalyzer.mandatorycourseanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.studyplananalyzer.mandatorycourseanalyzer
 * @Class: SimpleMandatoryCourseAnalyzer
 * @Author: Jan
 * @Date: 29.01.2019
 */

import com.example.courseanalyzer.Util.CourseLineUtil;
import com.example.courseanalyzer.analyzer.model.Course;

import java.util.*;

/**
 * Analyzes the mandatory courses. This implementation is used for the study
 * plan of the computer science curriculum of TU Wien thus an error can occure,
 * if other study plans are used with different formatting.
 *
 * <p>The order is <i><ects>_<courseType>_<course name></i></p>
 * <p>Every other information is simply ignored</p>
 */
public class SimpleMandatoryCourseAnalyzer implements MandatoryCourseAnalyzer {

    @Override
    public Set<Course> analyzeMandatoryCourses(String mandatoryCoursesText) {
        Set<Course> mandatoryCourses = new HashSet<>();
        Scanner scanner = new Scanner(mandatoryCoursesText);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (CourseLineUtil.isLineValidCourseWithoutWeeklyHoursInformation(line)) {

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
