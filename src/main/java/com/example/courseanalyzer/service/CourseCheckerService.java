package com.example.courseanalyzer.service;
/**
 * @Package: com.example.courseanalyzer.service
 * @Class: CourseCheckerService
 * @Author: Jan
 * @Date: 25.01.2019
 */

import com.example.courseanalyzer.dto.MandatoryCoursesDto;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;

/**
 *
 */
@Service
public interface CourseCheckerService {

    /**
     *
     * Checks the mandatory courses {@code mandatoryCoursesDto} and extracts the
     * mandatory courses from the input text.
     *
     * @param mandatoryCoursesDto contains the mandatory courses
     */
    void analyzeMandatoryCourses(MandatoryCoursesDto mandatoryCoursesDto);

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
     * Compares the mandatory courses with the finished courses. The result of
     * this process is sent back to the front end.
     *
     */
    void compareCourses();
}
