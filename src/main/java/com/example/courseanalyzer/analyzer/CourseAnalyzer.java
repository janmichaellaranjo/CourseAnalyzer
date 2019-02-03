package com.example.courseanalyzer.analyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer
 * @Class: CourseAnalyzer
 * @Author: Jan
 * @Date: 29.01.2019
 */

import com.example.courseanalyzer.analyzer.addtionalmandatorycourseanalyzer.AdditionalMandatoryCourseAnalyzer;
import com.example.courseanalyzer.analyzer.addtionalmandatorycourseanalyzer.SimpleAdditionalMandatoryCourseAnalyzer;
import com.example.courseanalyzer.analyzer.certificateanalyzer.CertificateAnalyzer;
import com.example.courseanalyzer.analyzer.certificateanalyzer.SimpleCertificateAnalyzer;
import com.example.courseanalyzer.analyzer.mandatorycourseanalyzer.MandatoryCourseAnalyzer;
import com.example.courseanalyzer.analyzer.mandatorycourseanalyzer.SimpleMandatoryCourseAnalyzer;
import com.example.courseanalyzer.analyzer.model.Course;
import com.example.courseanalyzer.analyzer.model.CourseReport;
import com.example.courseanalyzer.analyzer.model.TransitionalProvision;
import com.example.courseanalyzer.dto.MandatoryCoursesDto;

import javax.servlet.ServletRequest;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 */
public class CourseAnalyzer {

    private CertificateAnalyzer certificateAnalyzer;

    private AdditionalMandatoryCourseAnalyzer additionalMandatoryCourseAnalyzer;

    private MandatoryCourseAnalyzer mandatoryCourseAnalyzer;

    private Set<Course> mandatoryCourses;

    private TransitionalProvision transitionalProvision;

    private Set<Course> finishedCourses;

    public CourseAnalyzer() {
        this.mandatoryCourseAnalyzer = new SimpleMandatoryCourseAnalyzer();
        this.additionalMandatoryCourseAnalyzer = new SimpleAdditionalMandatoryCourseAnalyzer();
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
     * Extracts every addtional mandatory course from the DTO.
     *
     * @param mandatoryCoursesDto contains the mandatory courses.
     */
    public void analyzeAdditionalMandatoryCourses(MandatoryCoursesDto mandatoryCoursesDto) {
        this.transitionalProvision = additionalMandatoryCourseAnalyzer.analyzeAdditionalMandatoryCourses(mandatoryCoursesDto);
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
     *
     * Returns a report of the finished mandatory courses and the missing
     * mandatory courses.
     *
     * @return a report of the finished mandatory courses.
     */
    public CourseReport compareCourses() {
        float sumAchievedMandatoryEcts = 0f;
        float sumAchievedAdditionalMandatoryEcts = 0f;
        Set<Course> remainingFinishedCourses = new HashSet<>(finishedCourses);
        Set<Course> remainingMandatoryCourses = new HashSet<>(mandatoryCourses);
        CourseReport courseReport = new CourseReport();

        //find mandatory courses(Pflicht-LVAs in German)
        for (Course finishedCourse : finishedCourses) {
            if (mandatoryCourses.contains(finishedCourse)) {
                sumAchievedMandatoryEcts += finishedCourse.getEcts();
                remainingFinishedCourses.remove(finishedCourse);
                remainingMandatoryCourses.remove(finishedCourse);
            }
        }

        //find additional courses
        Iterator<Course> coursesIter = remainingFinishedCourses.iterator();
        while (coursesIter.hasNext()) {
            Course remainingCourse = coursesIter.next();

            if (transitionalProvision.containsCourse(remainingCourse)) {
                sumAchievedAdditionalMandatoryEcts += remainingCourse.getEcts();
                remainingMandatoryCourses.remove(remainingCourse);
            }
        }

        courseReport.setMandatoryCoursesEcts(sumAchievedMandatoryEcts);
        courseReport.setAdditionalMandatoryCoursesEcts(sumAchievedAdditionalMandatoryEcts);

        return courseReport;
    }
}
