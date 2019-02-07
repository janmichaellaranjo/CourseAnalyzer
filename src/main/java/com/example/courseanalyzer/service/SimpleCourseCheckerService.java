package com.example.courseanalyzer.service;
/**
 * @Package: com.example.courseanalyzer.service
 * @Class: SimpleCourseCheckerService
 * @Author: Jan
 * @Date: 25.01.2019
 */

import com.example.courseanalyzer.analyzer.CourseAnalyzer;
import com.example.courseanalyzer.analyzer.ReadFileException;
import com.example.courseanalyzer.analyzer.WrongFormatException;
import com.example.courseanalyzer.analyzer.model.CourseReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;


/**
 *
 */
@Service
public class SimpleCourseCheckerService implements CourseAnalyzerService {
    private static final Logger logger = LogManager.getLogger(SimpleCourseCheckerService.class);

    private CourseAnalyzer courseAnalyzer;

    public SimpleCourseCheckerService() {
        this.courseAnalyzer = new CourseAnalyzer();
    }

    @Override
    public void readStudyPlan(ServletRequest request) {
        if (request == null) {
            logger.error("The request on the service layer is null");
            throw new IllegalArgumentException("request must not be null");
        }
        courseAnalyzer.analyzeStudyPlan(request);
    }

    @Override
    public void readTransitionalProvision(ServletRequest request) {
        if (request == null) {
            logger.error("The request on the service layer is null");
            throw new IllegalArgumentException("request must not be null");
        }
        courseAnalyzer.readTransitionalProvision(request);
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
