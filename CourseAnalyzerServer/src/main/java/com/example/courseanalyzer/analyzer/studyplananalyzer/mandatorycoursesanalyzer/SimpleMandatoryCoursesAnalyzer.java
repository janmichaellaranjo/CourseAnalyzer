package com.example.courseanalyzer.analyzer.studyplananalyzer.mandatorycoursesanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.studyplananalyzer.mandatorycoursesanalyzer
 * @Class: SimpleMandatoryCoursesAnalyzer
 * @Author: Jan
 * @Date: 29.01.2019
 */

import com.example.courseanalyzer.analyzer.exception.WrongFormatException;
import com.example.courseanalyzer.util.CourseLineUtil;
import com.example.courseanalyzer.analyzer.model.Course;
import com.example.courseanalyzer.util.ValidationUtil;
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
@Component("SimpleMandatoryCoursesAnalyzer")
public class SimpleMandatoryCoursesAnalyzer implements MandatoryCoursesAnalyzer {

    private static final Logger logger = LogManager.getLogger(SimpleMandatoryCoursesAnalyzer.class);

    private Set<Course> mandatoryCourses;

    @Override
    public void analyzeMandatoryCourses(String mandatoryCoursesText) {

        if (mandatoryCoursesText == null) {
            throw new IllegalArgumentException("The passed mandatory courses text is null");
        } else if (mandatoryCoursesText.isEmpty()) {
            throw new WrongFormatException("The passed mandatory courses text is empty");
        }

        Scanner scanner = new Scanner(mandatoryCoursesText);
        this.mandatoryCourses = new HashSet<>();

        String line = "";
        try {
            while (scanner.hasNextLine()) {
                line = scanner.nextLine().trim();

                if (CourseLineUtil.isLineValidCourseWithoutWeeklyHoursInformation(line)) {

                    Course courseFromLine = CourseLineUtil.getCourseFromLine(line);

                    if (courseFromLine.isInformationComplete()) {
                        mandatoryCourses.add(courseFromLine);
                    } else {
                        String erroMsg = "The mandatory course could not be extracted from the line %s. " +
                                "The line contains invalid or incomplete informations";
                        throw new WrongFormatException(erroMsg);
                    }
                }
            }
        } catch (NumberFormatException e) {
            logger.error(e.getLocalizedMessage(), e);

            String errorMsg = String.format(
                    "The mandatory course could not be extracted from the line %s. The ECTs is not a number.",
                    line);
            throw new WrongFormatException(errorMsg);
        } finally {
            scanner.close();
        }

        ValidationUtil.validateNonEmptySet(mandatoryCourses, "mandatory courses");
    }

    @Override
    public Set<Course> getMandatoryCourses() {
        return mandatoryCourses;
    }
}
