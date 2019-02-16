package com.example.courseanalyzer.analyzer.studyplananalyzer.mandatorycourseanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.studyplananalyzer.mandatorycourseanalyzer
 * @Class: SimpleMandatoryCourseAnalyzer
 * @Author: Jan
 * @Date: 29.01.2019
 */

import com.example.courseanalyzer.analyzer.exception.NoModelsExtractedException;
import com.example.courseanalyzer.analyzer.exception.WrongFormatException;
import com.example.courseanalyzer.util.CourseLineUtil;
import com.example.courseanalyzer.analyzer.model.Course;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Analyzes the mandatory courses. This implementation is used for the study
 * plan of the computer science curriculum of TU Wien thus an error can occure,
 * if other study plans are used with different formatting.
 *
 * <p>The order is <i>[ects]_[courseType]_[course name]</i></p>
 * <p>Every other information is simply ignored</p>
 */
@Component("SimpleMandatoryCourseAnalyzer")
public class SimpleMandatoryCourseAnalyzer implements MandatoryCourseAnalyzer {

    private static final Logger logger = LogManager.getLogger(SimpleMandatoryCourseAnalyzer.class);

    @Override
    public Set<Course> analyzeMandatoryCourses(String mandatoryCoursesText) {

        if (mandatoryCoursesText == null) {
            throw new IllegalArgumentException("The passed mandatory courses text is null");
        }

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

        if (mandatoryCourses.isEmpty()) {
            String errorMsg = String.format(
                    "The mandatory course text has the wrong format." +
                    "No Mandatory courses could be extracted");
            logger.error(errorMsg);

            throw new NoModelsExtractedException(errorMsg);
        }

        return mandatoryCourses;
    }
}
