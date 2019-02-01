package com.example.courseanalyzer.service;
/**
 * @Package: com.example.courseanalyzer.service
 * @Class: SimpleCourseCheckerService
 * @Author: Jan
 * @Date: 25.01.2019
 */

import com.example.courseanalyzer.analyzer.CourseAnalyzer;
import com.example.courseanalyzer.analyzer.model.CourseReport;
import com.example.courseanalyzer.dto.MandatoryCoursesDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;


/**
 *
 */
@Service
public class SimpleCourseCheckerService implements CourseCheckerService {
    private static final Logger logger = LogManager.getLogger(SimpleCourseCheckerService.class);

    private CourseAnalyzer courseAnalyzer;

    public SimpleCourseCheckerService() {
        this.courseAnalyzer = new CourseAnalyzer();
    }

    @Override
    public void analyzeMandatoryCourses(MandatoryCoursesDto mandatoryCoursesDto) {

        if (mandatoryCoursesDto == null) {
            logger.error("The mandatory courses dto on the service layer is null");
            throw new IllegalArgumentException("mandatoryCoursesDto must not be null");
        }

        courseAnalyzer.analyzeMandatoryCourses(mandatoryCoursesDto);
    }

    @Override
    public void readCertificateList(ServletRequest request) {

        if (request == null) {
            logger.error("The request on the service layer is null");
            throw new IllegalArgumentException("request must not be null");
        }

        courseAnalyzer.analyzeCourseList(request);
    }

    @Override
    public CourseReport compareCourses() {
        return courseAnalyzer.compareCourses();
    }
}
