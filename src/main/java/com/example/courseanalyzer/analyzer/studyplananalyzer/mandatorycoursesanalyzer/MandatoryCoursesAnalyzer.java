package com.example.courseanalyzer.analyzer.studyplananalyzer.mandatorycoursesanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.studyplananalyzer.mandatorycoursesanalyzer
 * @Class: MandatoryCoursesAnalyzer
 * @Author: Jan
 * @Date: 29.01.2019
 */

import com.example.courseanalyzer.analyzer.exception.NoModelsExtractedException;
import com.example.courseanalyzer.analyzer.exception.WrongFormatException;
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
public interface MandatoryCoursesAnalyzer {

    /**
     * Returns the set of the mandatory courses from the text
     * {@code mandatoryCourses}.
     *
     * <p>When a line does not meet all criteria for a course, it won't be added
     * to the list and will be treated as a different line.</p>
     *
     * @param mandatoryCourses contains the mandatory courses
     * @throws IllegalArgumentException is thrown, when {@code modulesText} is
     *                                   {@code null}.
     * @throws NoModelsExtractedException is thrown, when no course could be
     *                                    extracted from {@code mandatoryCourses}
     *                                    because the format of the text is
     *                                    wrong.
     * @throws WrongFormatException is thrown, when the format of
     *                              {@code mandatoryCourses} is empty or a
     *                              course information is incomplete
     * @return the set of the mandatory courses from the text
     *         {@code mandatoryCourses}.
     */
    Set<Course> analyzeMandatoryCourses(String mandatoryCourses);
}
