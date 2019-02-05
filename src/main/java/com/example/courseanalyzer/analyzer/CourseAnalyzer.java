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
import com.example.courseanalyzer.analyzer.model.Course;
import com.example.courseanalyzer.analyzer.model.CourseReport;
import com.example.courseanalyzer.analyzer.model.TransitionalProvision;
import com.example.courseanalyzer.analyzer.studyplananalyzer.SimpleStudyPlanAnalyzer;
import com.example.courseanalyzer.analyzer.studyplananalyzer.StudyPlanAnalyzer;
import com.example.courseanalyzer.analyzer.studyplananalyzer.model.Module;

import javax.servlet.ServletRequest;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 */
public class CourseAnalyzer {

    private AdditionalMandatoryCourseAnalyzer additionalMandatoryCourseAnalyzer;

    private StudyPlanAnalyzer studyPlanAnalyzer;

    private CertificateAnalyzer certificateAnalyzer;

    private Set<Course> mandatoryCourses;

    private TransitionalProvision transitionalProvision;

    private Set<Course> finishedCourses;

    private Set<Module> modules;

    private Set<Course> transferableSkills;

    public CourseAnalyzer() {
        this.additionalMandatoryCourseAnalyzer = new SimpleAdditionalMandatoryCourseAnalyzer();
        this.studyPlanAnalyzer = new SimpleStudyPlanAnalyzer();
        this.certificateAnalyzer = new SimpleCertificateAnalyzer();
    }

    /**
     *
     * Extracts the information from the request to retrieve the mandatory course,
     * modules and transferable skills.
     *
     * @param request contains the list of mandatory courses, modules and
     *                transferable skills.
     */
    public void analyzeStudyPlan(ServletRequest request) {
        studyPlanAnalyzer.analyzeStudyPlan(request);

    }

    /**
     *
     * Extracts the additional mandatory courses from the request.
     *
     * @param request contains the list of additional courses.
     */
    public void readTransitionalProvision(ServletRequest request) {
        additionalMandatoryCourseAnalyzer.analyzeTransitionalProvision(request);
    }

    /**
     *
     * Extracts the information from the request to retrieve the certificates.
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

        initCourseInformation();
        float sumAchievedMandatoryEcts = 0f;
        float sumAchievedAdditionalMandatoryEcts = 0f;
        float sumAchievedOptionalModuleEcts = 0f;
        float sumAchievedTransferableSkillsEcts = 0f;
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
            if (transitionalProvision.containsMandatoryCourse(remainingCourse)) {
                sumAchievedMandatoryEcts += remainingCourse.getEcts();
                coursesIter.remove();
            } else if (transitionalProvision.containsAdditionalMandatoryCourse(remainingCourse)) {
                sumAchievedAdditionalMandatoryEcts += remainingCourse.getEcts();
                coursesIter.remove();
            }
        }

        //find courses from optional modules
        coursesIter = remainingFinishedCourses.iterator();
        while (coursesIter.hasNext()) {
            Course remainingCourse = coursesIter.next();

            for (Module module : modules) {
                if (module.containsCourse(remainingCourse) && module.isOptional()) {
                    sumAchievedOptionalModuleEcts += remainingCourse.getEcts();
                    coursesIter.remove();
                    break;
                }
            }
        }

        //find courses from the transferable skills
        coursesIter = remainingFinishedCourses.iterator();
        while (coursesIter.hasNext()) {
            Course remainingCourse = coursesIter.next();

            if (transferableSkills.contains(remainingCourse)) {
                sumAchievedTransferableSkillsEcts += remainingCourse.getEcts();
                coursesIter.remove();
            }
        }

        courseReport.setMandatoryCoursesEcts(sumAchievedMandatoryEcts);
        courseReport.setAdditionalMandatoryCoursesEcts(sumAchievedAdditionalMandatoryEcts);
        courseReport.setOptionalModuleEcts(sumAchievedOptionalModuleEcts);
        courseReport.setTransferableSkillsEcts(sumAchievedTransferableSkillsEcts);

        return courseReport;
    }

    private void initCourseInformation() {

        this.mandatoryCourses = studyPlanAnalyzer.getMandatoryCourses();
        this.transitionalProvision = additionalMandatoryCourseAnalyzer.getTransitionalProvision();
        this.modules = studyPlanAnalyzer.getModules();
        this.transferableSkills = studyPlanAnalyzer.getTransferableSkills();
    }
}
