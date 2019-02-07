package com.example.courseanalyzer.analyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer
 * @Class: CourseAnalyzer
 * @Author: Jan
 * @Date: 29.01.2019
 */

import com.example.courseanalyzer.analyzer.addtionalmandatorycourseanalyzer.TransitionalProvisionAnalyzer;
import com.example.courseanalyzer.analyzer.addtionalmandatorycourseanalyzer.SimpleTransitionalProvisionAnalyzer;
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

    private TransitionalProvisionAnalyzer transitionalProvisionAnalyzer;

    private StudyPlanAnalyzer studyPlanAnalyzer;

    private CertificateAnalyzer certificateAnalyzer;

    private Set<Course> mandatoryCourses;

    private TransitionalProvision transitionalProvision;

    private Set<Course> finishedCourses;

    private Set<Module> modules;

    private Set<Course> transferableSkills;

    public CourseAnalyzer() {
        this.transitionalProvisionAnalyzer = new SimpleTransitionalProvisionAnalyzer();
        this.studyPlanAnalyzer = new SimpleStudyPlanAnalyzer();
        this.certificateAnalyzer = new SimpleCertificateAnalyzer();
    }

    /**
     *
     * @see StudyPlanAnalyzer#analyzeStudyPlan(ServletRequest)
     *
     * @param request contains the list of mandatory courses, modules and
     *                transferable skills.
     * @throws IllegalArgumentException is thrown, when one necessary parameters
     *                                  is {@code null}.
     * @throws WrongFormatException is thrown, when the format of table of
     *                              content, text of mandatory courses, text of
     *                              modules or text of the transferable skills
     *                              is wrong.
     */
    public void analyzeStudyPlan(ServletRequest request) {
        studyPlanAnalyzer.analyzeStudyPlan(request);
    }

    /**
     *
     *
     * @see TransitionalProvisionAnalyzer#analyzeTransitionalProvision(ServletRequest)
     *
     * @param request contains the study plan.
     * @throws ReadFileException    is thrown, when an IO problem occurs while
     *                              reading the passed file.
     * @throws WrongFormatException is thrown, when the text on a page is empty
     *                              or the format creates an empty
     *                              {@link TransitionalProvision}
     */
    public void readTransitionalProvision(ServletRequest request) {
        transitionalProvisionAnalyzer.analyzeTransitionalProvision(request);
    }

    /**
     *
     * @see CertificateAnalyzer#analyzeCertificateList(ServletRequest)
     *
     * @param request the download request
     */
    public void analyzeCourseList(ServletRequest request) {
        certificateAnalyzer.analyzeCertificateList(request);
    }

    /**
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
        this.finishedCourses = certificateAnalyzer.getCertificates();
        this.mandatoryCourses = studyPlanAnalyzer.getMandatoryCourses();
        this.transitionalProvision = transitionalProvisionAnalyzer.getTransitionalProvision();
        this.modules = studyPlanAnalyzer.getModules();
        this.transferableSkills = studyPlanAnalyzer.getTransferableSkills();
    }
}
