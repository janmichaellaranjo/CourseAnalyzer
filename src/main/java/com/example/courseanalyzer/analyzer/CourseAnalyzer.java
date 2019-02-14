package com.example.courseanalyzer.analyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer
 * @Class: CourseAnalyzer
 * @Author: Jan
 * @Date: 29.01.2019
 */

import com.example.courseanalyzer.analyzer.transitionalprovisionanalyzer.TransitionalProvisionAnalyzer;
import com.example.courseanalyzer.analyzer.transitionalprovisionanalyzer.SimpleTransitionalProvisionAnalyzer;
import com.example.courseanalyzer.analyzer.certificateanalyzer.CertificateAnalyzer;
import com.example.courseanalyzer.analyzer.certificateanalyzer.SimpleCertificateAnalyzer;
import com.example.courseanalyzer.analyzer.model.Course;
import com.example.courseanalyzer.analyzer.model.CourseReport;
import com.example.courseanalyzer.analyzer.model.TransitionalProvision;
import com.example.courseanalyzer.analyzer.studyplananalyzer.SimpleStudyPlanAnalyzer;
import com.example.courseanalyzer.analyzer.studyplananalyzer.StudyPlanAnalyzer;
import com.example.courseanalyzer.analyzer.studyplananalyzer.model.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Is responsible to pass the information from the front end to the
 * corresponding analyzers. The retrieved information is used for the analysis
 * to create a {@link CourseReport} with {@link #compareCourses()}.
 */
@Component
public class CourseAnalyzer {

    @Autowired
    @Qualifier("SimpleStudyPlanAnalyzer")
    private StudyPlanAnalyzer studyPlanAnalyzer;

    @Autowired
    @Qualifier("SimpleCertificateAnalyzer")
    private CertificateAnalyzer certificateAnalyzer;

    @Autowired
    @Qualifier("SimpleTransitionalProvisionAnalyzer")
    private TransitionalProvisionAnalyzer transitionalProvisionAnalyzer;

    private Set<Course> mandatoryCourses;

    private TransitionalProvision transitionalProvision;

    private Set<Course> finishedCourses;

    private Set<Module> modules;

    private Set<Course> transferableSkills;

    private float sumAchievedMandatoryEcts = 0f;

    private float sumAchievedAdditionalMandatoryEcts = 0f;

    private float sumAchievedOptionalModuleEcts = 0f;

    private float sumAchievedTransferableSkillsEcts = 0f;

    /**
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
     * @see CertificateAnalyzer#analyzeCertificateList(ServletRequest)
     *
     * @param request the download request
     */
    public void analyzeCourseList(ServletRequest request) {
        certificateAnalyzer.analyzeCertificateList(request);
    }

    /**
     * Returns a report of the finished mandatory courses and the missing
     * mandatory courses.
     *
     * @return a report of the finished mandatory courses.
     */
    public CourseReport compareCourses() {

        initCourseInformation();

        CourseReport courseReport = new CourseReport();

        Set<Course> remainingFinishedCourses = mandatoryCourses
                .stream()
                .filter(mandatoryCourse -> !finishedCourses.contains(mandatoryCourse))
                .collect(Collectors.toSet());

        Set<Course> remainingMandatoryCourses = finishedCourses
                .stream()
                .filter(finishedCourse -> !mandatoryCourses.contains(finishedCourse))
                .filter(finishedCourse -> !transitionalProvision.containsMandatoryCourse(finishedCourse))
                .filter(finishedCourse -> !transitionalProvision.containsAdditionalMandatoryCourse(finishedCourse))
                .filter(finishedCourse -> !isOptionalModulesContainsCourse(finishedCourse))
                .filter(finishedCourse -> !transferableSkills.contains(finishedCourse))
                .collect(Collectors.toSet());

        calculateEcts();

        courseReport.setMandatoryCoursesEcts(sumAchievedMandatoryEcts);
        courseReport.setAdditionalMandatoryCoursesEcts(sumAchievedAdditionalMandatoryEcts);
        courseReport.setOptionalModuleEcts(sumAchievedOptionalModuleEcts);
        courseReport.setTransferableSkillsEcts(sumAchievedTransferableSkillsEcts);

        courseReport.setRemainingMandatoryCourses(remainingFinishedCourses);
        courseReport.setRemainingUnassignedFinishedCourses(remainingMandatoryCourses);

        return courseReport;
    }

    private void initCourseInformation() {
        this.finishedCourses = certificateAnalyzer.getCertificates();
        this.mandatoryCourses = studyPlanAnalyzer.getMandatoryCourses();
        this.transitionalProvision = transitionalProvisionAnalyzer.getTransitionalProvision();
        this.modules = studyPlanAnalyzer.getModules();
        this.transferableSkills = studyPlanAnalyzer.getTransferableSkills();
    }

    private void calculateEcts() {
        for (Course finishedCourse : finishedCourses) {
            if (mandatoryCourses.contains(finishedCourse)) {
                sumAchievedMandatoryEcts += finishedCourse.getEcts();
            } else if (transitionalProvision.containsMandatoryCourse(finishedCourse)) {
                sumAchievedMandatoryEcts += finishedCourse.getEcts();
            } else if (transitionalProvision.containsAdditionalMandatoryCourse(finishedCourse)) {
                sumAchievedAdditionalMandatoryEcts += finishedCourse.getEcts();
            } else if (isOptionalModulesContainsCourse(finishedCourse)) {
                sumAchievedOptionalModuleEcts += finishedCourse.getEcts();
            } else if (transferableSkills.contains(finishedCourse)) {
                sumAchievedTransferableSkillsEcts += finishedCourse.getEcts();
            }
        }
    }

    private boolean isOptionalModulesContainsCourse(Course examinedCourse) {
        for (Module module : modules) {
            if (module.containsCourse(examinedCourse) && module.isOptional()) {
                return true;
            }
        }
        return false;
    }
}
