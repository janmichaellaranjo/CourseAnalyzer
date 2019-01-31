package com.example.courseanalyzer.analyzer.mandatorycourseanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.mandatorycourseanalyzer
 * @Class: MandatoryCourseAnalyzer
 * @Author: Jan
 * @Date: 29.01.2019
 */

import com.example.courseanalyzer.analyzer.model.Course;
import com.example.courseanalyzer.dto.MandatoryCoursesDto;

import java.util.Set;


/**
 * Extracts the mandatory course informations.
 *
 * <p>This informations are found in the corresponding study plan under the
 * chapter for recommended courses for each semester. This plan must be copied
 * and pasted into the textarea.</p>
 *
 * <p>These interface is used to conveniently change the implementation because
 * the plan may change and a new implementation is necessary.</p>
 */
public interface MandatoryCourseAnalyzer {

    /**
     *
     * Returns the list of the mandatory courses from the dto.
     *
     * <p>This list can be empty, if the text does not meet the format
     *    criteria.</p>
     *
     * @param mandatoryCoursesDto contains the mandatory courses
     * @return the list of the mandatory courses from the dto.
     */
    Set<Course> analyzeMandatoryCourses(MandatoryCoursesDto mandatoryCoursesDto);
}
