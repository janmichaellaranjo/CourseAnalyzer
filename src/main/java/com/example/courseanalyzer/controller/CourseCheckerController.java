package com.example.courseanalyzer.controller;
/**
 * @Package: com.example.courseanalyzer.controller
 * @Class: CourseCheckerController
 * @Author: Jan
 * @Date: 25.01.2019
 */

import com.example.courseanalyzer.analyzer.model.CourseReport;
import com.example.courseanalyzer.dto.MandatoryCoursesDto;
import com.example.courseanalyzer.service.CourseCheckerService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 */

@RequestMapping("/courseanalyzer")
@RestController
public class CourseCheckerController {

    @Autowired
    CourseCheckerService courseCheckerService;

    private static final Logger logger = LogManager.getLogger(CourseCheckerController.class.getName());

    @RequestMapping(value = "/analyzeAdditionalMandatoryCourses",
            method = RequestMethod.POST)
    public void analyzeAdditionalMandatoryCourses(@RequestBody MandatoryCoursesDto mandatoryCoursesDto) {
        courseCheckerService.analyzeAdditionalMandatoryCourses(mandatoryCoursesDto);
    }

    @RequestMapping(value = "/readStudyPlan",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void readStudyPlan(HttpServletRequest request, HttpServletResponse response) {
        courseCheckerService.readStudyPlan(request);
    }

    @RequestMapping(value = "/readCertificateList",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public void readCourseList(HttpServletRequest request, HttpServletResponse response) {
        courseCheckerService.readCertificateList(request);
    }

    @RequestMapping(value = "/compareCourses",
                    method = RequestMethod.GET)
    public @ResponseBody CourseReport compareCourses() {
        CourseReport courseReport = courseCheckerService.compareCourses();
        return courseReport;
    }

}
