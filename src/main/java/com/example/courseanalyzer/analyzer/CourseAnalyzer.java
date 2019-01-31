package com.example.courseanalyzer.analyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer
 * @Class: CourseAnalyzer
 * @Author: Jan
 * @Date: 29.01.2019
 */

import com.example.courseanalyzer.analyzer.certificateanalyzer.CertificateAnalyzer;
import com.example.courseanalyzer.analyzer.certificateanalyzer.SimpleCertificateAnalyzer;
import com.example.courseanalyzer.analyzer.mandatorycourseanalyzer.MandatoryCourseAnalyzer;
import com.example.courseanalyzer.analyzer.mandatorycourseanalyzer.SimpleMandatoryCourseAnalyzer;
import com.example.courseanalyzer.analyzer.model.Course;
import com.example.courseanalyzer.dto.MandatoryCoursesDto;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import java.util.Set;

/**
 *
 */
public class CourseAnalyzer {

    private CertificateAnalyzer certificateAnalyzer;

    private MandatoryCourseAnalyzer mandatoryCourseAnalyzer;

    private Set<Course> mandatoryCourses;

    private Set<Course> finishedCourses;

    public CourseAnalyzer() {
        this.mandatoryCourseAnalyzer = new SimpleMandatoryCourseAnalyzer();
        this.certificateAnalyzer = new SimpleCertificateAnalyzer();
    }

    /**
     *
     * Extracts every mandatory course from the DTO.
     *
     * @param mandatoryCoursesDto contains the mandatory courses.
     */
    public void analyzeMandatoryCourses(MandatoryCoursesDto mandatoryCoursesDto) {
        this.mandatoryCourses = mandatoryCourseAnalyzer.analyzeMandatoryCourses(mandatoryCoursesDto);
    }

    /**
     *
     * Extracts the information from the request to retriev the certificates.
     *
     * @param request contains the list of certificates.
     */
    public void analyzeCourseList(ServletRequest request) {
        this.finishedCourses = certificateAnalyzer.analyzeCertificateList(request);
    }

    /**
     *
     * Compares the mandatory courses and the finished courses.
     *
     */
    public void compareCourses() {
        System.out.println(mandatoryCourses);
        System.out.println(finishedCourses);
    }
}
