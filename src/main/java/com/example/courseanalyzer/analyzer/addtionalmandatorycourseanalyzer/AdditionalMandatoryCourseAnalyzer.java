package com.example.courseanalyzer.analyzer.addtionalmandatorycourseanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.addtionalmandatorycourseanalyzer
 * @Class: AdditionalMandatoryCourseAnalyzer
 * @Author: Jan
 * @Date: 02.02.2019
 */

import com.example.courseanalyzer.analyzer.model.TransitionalProvision;
import com.example.courseanalyzer.dto.MandatoryCoursesDto;

/**
 * Extracts the additional mandatory course informations.
 *
 * <p>This informations are found in the actual transitional provisions under the
 * chapter that contains the informations of previous courses that are no longer
 * offered. This informations must be copied and pasted into the textarea.</p>
 *
 * <p>These interface is used to conveniently change the implementation because
 * the format may change and a new implementation is necessary.</p>
 */
public interface AdditionalMandatoryCourseAnalyzer {

    /**
     *
     *
     * Returns the transitional provisions by extracting every addtional
     * mandatory course from the DTO.
     *
     * @param mandatoryCoursesDto contains the mandatory courses.
     * @return the transitional provisions by extracting every addtional
     *         mandatory course from the DTO.
     */
    TransitionalProvision analyzeAdditionalMandatoryCourses(MandatoryCoursesDto mandatoryCoursesDto);
}
