package com.example.courseanalyzer.controller;
/**
 * @Package: com.example.courseanalyzer.controller
 * @Class: CourseAnalyzerController
 * @Author: Jan
 * @Date: 25.01.2019
 */

import com.example.courseanalyzer.CourseAnalyzerApplication;
import com.example.courseanalyzer.analyzer.model.CourseReport;
import com.example.courseanalyzer.service.CourseAnalyzerService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * Is the endpoint for the front end requests to trigger the methods.
 */
@CrossOrigin
@RequestMapping("/courseanalyzer")
@RestController
public class CourseAnalyzerController {

    @Autowired
    CourseAnalyzerService courseCheckerService;

    private static final Logger logger = LogManager.getLogger(CourseAnalyzerController.class.getName());

    @RequestMapping(value = "/readStudyPlan",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void readStudyPlan(@RequestParam("studyPlan") MultipartFile multipartFile) {
        logger.info("A request for reading a study plan has been initiated");

        courseCheckerService.readStudyPlan(multipartFile);
    }

    @RequestMapping(value = "/readTransitionalProvision",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void readTransitionalProvision(@RequestParam("transitionalProvision") MultipartFile multipartFile) {
        logger.info("A request for reading a transitional provision has been initiated");
        courseCheckerService.readTransitionalProvision(multipartFile);
    }

    @RequestMapping(value = "/readFinishedCourseList",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void readFinishedCourseList(@RequestParam("finishedCourses") MultipartFile multipartFile) {
        logger.info("A request for reading a course list has been initiated");
        courseCheckerService.readFinishedCourseList(multipartFile);
    }

    @RequestMapping(value = "/compareCourses",
                    method = RequestMethod.GET)
    public @ResponseBody CourseReport compareCourses() {
        logger.info("A request for comparing the courses has been initiated");
        CourseReport courseReport = courseCheckerService.compareCourses();
        return courseReport;
    }

    @RequestMapping(value = "/closeApplication",
            method = RequestMethod.GET)
    public @ResponseBody void closeApplication() {
        logger.info("A request for closing the application has been initiated");
        CourseAnalyzerApplication.closeApplication();
    }
}
