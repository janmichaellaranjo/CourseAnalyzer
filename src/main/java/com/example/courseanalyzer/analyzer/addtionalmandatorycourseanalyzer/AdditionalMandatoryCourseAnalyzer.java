package com.example.courseanalyzer.analyzer.addtionalmandatorycourseanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.addtionalmandatorycourseanalyzer
 * @Class: AdditionalMandatoryCourseAnalyzer
 * @Author: Jan
 * @Date: 02.02.2019
 */

import com.example.courseanalyzer.analyzer.model.TransitionalProvision;
import com.example.courseanalyzer.dto.MandatoryCoursesDto;

import javax.servlet.ServletRequest;

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
     * Analysis the request {@code request} and assign the result to the sets of
     * mandatory and additional mandatory courses.
     *
     * @param request contains the study plan.
     */
    void analyzeTransitionalProvision(ServletRequest request);

    /**
     *
     * Returns the transitional provision containing the mandatory and additional
     * mandatory courses.
     *
     * @return the transitional provision containing the mandatory and
     * additional mandatory courses.
     */
    TransitionalProvision getTransitionalProvision();
}
