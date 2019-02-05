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
     * Reads the request and extracts the mandatory courses, modules and
     * transferable skills from the request which contains the uploaded file.
     *
     * @param request contains the courses
     */
    void readStudyPlan(ServletRequest request);

    /**
     *
     * Reads the request and extracts the additional mandatory courses from the
     * request which contains the uploaded file.
     *
     * @param request contains the courses
     */
    void readTransitionalProvision(ServletRequest request);

    /**
     *
     * Reads the request and extracts the certificates from the request which
     * contains the uploaded file.
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
