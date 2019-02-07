package com.example.courseanalyzer.service;
/**
 * @Package: com.example.courseanalyzer.service
 * @Class: CourseAnalyzerService
 * @Author: Jan
 * @Date: 25.01.2019
 */

import com.example.courseanalyzer.analyzer.ReadFileException;
import com.example.courseanalyzer.analyzer.WrongFormatException;
import com.example.courseanalyzer.analyzer.model.CourseReport;

import javax.servlet.ServletRequest;

/**
 *
 */
public interface CourseAnalyzerService {

    /**
     *
     * Reads the request and extracts the mandatory courses, modules and
     * transferable skills from the request which contains the uploaded file.
     *
     * @param request contains the courses
     * @throws IllegalArgumentException is thrown, when one necessary parameters
     *                                  is {@code null}.
     * @throws WrongFormatException is thrown, when the format of table of
     *                              content, text of mandatory courses, text of
     *                              modules or text of the transferable skills
     *                              is wrong.
     */
    void readStudyPlan(ServletRequest request);

    /**
     *
     * Reads the request and extracts the additional mandatory courses from the
     * request which contains the uploaded file.
     *
     * @param request contains the courses
     * @throws ReadFileException    is thrown, when an IO problem occurs while
     *                              reading the passed file.
     * @throws WrongFormatException is thrown, when the text on a page
     *                              is empty or the format creates an empty
     *                              {@link com.example.courseanalyzer.analyzer.model.TransitionalProvision}
     */
    void readTransitionalProvision(ServletRequest request);

    /**
     *
     * Reads the request and extracts the certificates from the request which
     * contains the uploaded file.
     *
     * @param request contains the certificates
     * @throws ReadFileException    is thrown, when an IO problem occurs while
     *                              reading the passed file.
     * @throws WrongFormatException is thrown, when the format is empty or the
     *                               text creates an empty list of certificates.
     */
    void readCertificateList(ServletRequest request);

    /**
     *
     * @see com.example.courseanalyzer.analyzer.CourseAnalyzer#compareCourses
     *
     * @return a report of the finished mandatory courses.
     */
    CourseReport compareCourses();
}
