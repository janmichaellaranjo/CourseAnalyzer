package com.example.courseanalyzer.analyzer.studyplananalyzer.mandatorycourseanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.studyplananalyzer.mandatorycourseanalyzer
 * @Class: MandatoryCourseAnalyzer
 * @Author: Jan
 * @Date: 29.01.2019
 */

import com.example.courseanalyzer.analyzer.model.Course;

import java.util.Set;


/**
 * Extracts the mandatory course informations.
 *
 * <p>This informations are found in the corresponding study plan under the
 *    chapter for recommended courses for each semester.
 * </p>
 *
 * <p>These interface is used to conveniently change the implementation because
 *    the plan may change and a new implementation is necessary.</p>
 */
public interface MandatoryCourseAnalyzer {

    /**
     *
     * Returns the set of the mandatory courses from the text
     * {@code mandatoryCourses}.
     *
     * <p>This list can be empty, if the text does not meet the format
     *    criteria.</p>
     *
     * @param mandatoryCourses contains the mandatory courses
     * @return the set of the mandatory courses from the text
     *         {@code mandatoryCourses}.
     */
    Set<Course> analyzeMandatoryCourses(String mandatoryCourses);
}
