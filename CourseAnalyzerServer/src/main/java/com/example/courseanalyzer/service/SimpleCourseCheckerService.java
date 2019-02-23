package com.example.courseanalyzer.service;
/**
 * @Package: com.example.courseanalyzer.service
 * @Class: SimpleCourseCheckerService
 * @Author: Jan
 * @Date: 25.01.2019
 */

import com.example.courseanalyzer.analyzer.CourseAnalyzer;
import com.example.courseanalyzer.analyzer.model.CourseReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;


/**
 *
 */
@Service
public class SimpleCourseCheckerService implements CourseAnalyzerService {
    private static final Logger logger = LogManager.getLogger(SimpleCourseCheckerService.class);

    @Autowired
    private CourseAnalyzer courseAnalyzer;

    @Override
    public void readStudyPlan(MultipartFile multiPartFile) {
        if (multiPartFile == null) {
            logger.error("The multipartFile on the service layer is null");
            throw new IllegalArgumentException("request must not be null");
        }
        courseAnalyzer.analyzeStudyPlan(multiPartFile);
    }

    @Override
    public void readTransitionalProvision(MultipartFile multipartFile) {
        if (multipartFile == null) {
            logger.error("The multipartFile on the service layer is null");
            throw new IllegalArgumentException("request must not be null");
        }
        courseAnalyzer.readTransitionalProvision(multipartFile);
    }

    @Override
    public void readFinishedCourseList(MultipartFile multipartFile) {
        if (multipartFile == null) {
            logger.error("The multipartFile on the service layer is null");
            throw new IllegalArgumentException("request must not be null");
        }

        courseAnalyzer.analyzeFinishedCourses(multipartFile);
    }

    @Override
    public CourseReport compareCourses() {
        return courseAnalyzer.compareCourses();
    }
}
