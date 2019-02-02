package com.example.courseanalyzer.service;
/**
 * @Package: com.example.courseanalyzer.service
 * @Class: CourseCheckerService
 * @Author: Jan
 * @Date: 25.01.2019
 */

import com.example.courseanalyzer.analyzer.model.CourseReport;
import com.example.courseanalyzer.dto.MandatoryCoursesDto;

import javax.servlet.ServletRequest;

/**
 *
 */
public interface CourseCheckerService {

    /**
     *
     * Checks the mandatory courses {@code mandatoryCoursesDto} and extracts the
     * mandatory courses information from the input text.
     *
     * @param mandatoryCoursesDto contains the mandatory courses
     */
    void analyzeMandatoryCourses(MandatoryCoursesDto mandatoryCoursesDto);

    /**
     *
     * Checks the additonal mandatory courses {@code mandatoryCoursesDto} and
     * extracts the additional mandatory courses information from the input text.
     *
     * @param mandatoryCoursesDto contains the additonal mandatory courses
     */
    void analyzeAdditionalMandatoryCourses(MandatoryCoursesDto mandatoryCoursesDto);

    /**
     *
     * Reads and extracts the certificates from the file
     * {@code certificateListFile}.
     *
     * @param request contains the certificates
     */
    void readCertificateList(ServletRequest request);

    /**
     *
     * Returns a report containing the sum of achieved ects and the list of missing
     * mandatory courses.
     *
     */
    CourseReport compareCourses();
}
