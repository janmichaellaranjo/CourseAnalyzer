package com.example.courseanalyzer.service;
/**
 * @Package: com.example.courseanalyzer.service
 * @Class: CourseAnalyzer
 * @Author: Jan
 * @Date: 02.03.2019
 */

import com.example.courseanalyzer.analyzer.finishedcoursesanalyzer.FinishedCoursesAnalyzer;
import com.example.courseanalyzer.analyzer.model.Course;
import com.example.courseanalyzer.analyzer.model.CourseGroup;
import com.example.courseanalyzer.analyzer.model.CourseReport;
import com.example.courseanalyzer.analyzer.model.TransitionalProvision;
import com.example.courseanalyzer.analyzer.studyplananalyzer.StudyPlanAnalyzer;
import com.example.courseanalyzer.analyzer.studyplananalyzer.model.Module;
import com.example.courseanalyzer.analyzer.transitionalprovisionanalyzer.TransitionalProvisionAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Compares the mandatory courses and transferable skills from the study plan
 * and optional module from the transitional provision with the finished courses.
 * </p>
 * <p>The detailed result of the analysis will be encapsulated in
 * {@link com.example.courseanalyzer.analyzer.model.CourseReport} which can be
 * </p>
 *
 */
public class CourseAnalyzer {

    @Autowired
    @Qualifier("SimpleStudyPlanAnalyzer")
    private StudyPlanAnalyzer studyPlanAnalyzer;

    @Autowired
    @Qualifier("SimpleTransitionalProvisionAnalyzer")
    private TransitionalProvisionAnalyzer transitionalProvisionAnalyzer;

    @Autowired
    @Qualifier("SimpleFinishedCoursesAnalyzer")
    private FinishedCoursesAnalyzer finishedCoursesAnalyzer;

    private CourseReport courseReport;

    private Set<Course> mandatoryCourses;

    private TransitionalProvision transitionalProvision;

    private Set<Course> finishedCourses;

    private Set<Module> modules;

    private Set<Course> transferableSkills;

    private float sumAchievedMandatoryEcts;

    private float sumAchievedAdditionalMandatoryEcts;

    private float sumAchievedOptionalModuleEcts;

    private float sumAchievedTransferableSkillsEcts;

    /**
     * Analysis the mandatory courses, transferable skills, optional modules
     * with the finished courses. The result of the analysis will be encapsulated
     * in {@link #courseReport}.
     */
    public void analyzeCourses() {
        initCourseInformation();
        determineCourseLists();
        calculateEcts();
    }

    private void initCourseInformation() {
        this.sumAchievedMandatoryEcts = 0f;
        this.sumAchievedAdditionalMandatoryEcts = 0f;
        this.sumAchievedOptionalModuleEcts = 0f;
        this.sumAchievedTransferableSkillsEcts = 0f;
        this.finishedCourses = finishedCoursesAnalyzer.getFinishedCourses();
        this.mandatoryCourses = studyPlanAnalyzer.getMandatoryCourses();
        this.transitionalProvision = transitionalProvisionAnalyzer.getTransitionalProvision();
        this.modules = studyPlanAnalyzer.getModules();
        this.transferableSkills = studyPlanAnalyzer.getTransferableSkills();
        this.courseReport = new CourseReport();
    }

    private void calculateEcts() {
        for (Course finishedCourse : finishedCourses) {
            if (mandatoryCourses.contains(finishedCourse)) {
                sumAchievedMandatoryEcts += finishedCourse.getEcts();
            } else if (transitionalProvision != null && transitionalProvision.containsMandatoryCourse(finishedCourse)) {
                sumAchievedMandatoryEcts += finishedCourse.getEcts();
            } else if (transitionalProvision != null && transitionalProvision.containsAdditionalMandatoryCourse(finishedCourse)) {
                sumAchievedAdditionalMandatoryEcts += finishedCourse.getEcts();
            } else if (isOptionalModulesContainsCourse(finishedCourse)) {
                sumAchievedOptionalModuleEcts += finishedCourse.getEcts();
            } else if (transferableSkills.contains(finishedCourse)) {
                sumAchievedTransferableSkillsEcts += finishedCourse.getEcts();
            }
        }

        courseReport.setMandatoryCoursesEcts(sumAchievedMandatoryEcts);
        courseReport.setAdditionalMandatoryCoursesEcts(sumAchievedAdditionalMandatoryEcts);
        courseReport.setOptionalModuleEcts(sumAchievedOptionalModuleEcts);
        courseReport.setTransferableSkillsEcts(sumAchievedTransferableSkillsEcts);
    }

    private boolean isOptionalModulesContainsCourse(Course examinedCourse) {
        for (Module module : modules) {
            if (module.containsCourse(examinedCourse) && module.isOptional()) {
                return true;
            }
        }
        return false;
    }

    private void determineCourseLists() {
        Set<Course> remainingMandatoryCourses = determineRemainingMandatoryCourses();

        Set<Course> unassignedCourses = finishedCourses
                .stream()
                .filter(finishedCourse -> !mandatoryCourses.contains(finishedCourse))
                .filter(finishedCourse -> transitionalProvision != null ? !transitionalProvision.containsMandatoryCourse(finishedCourse) : true)
                .filter(finishedCourse -> transitionalProvision != null ? !transitionalProvision.containsAdditionalMandatoryCourse(finishedCourse) : true)
                .filter(finishedCourse -> !isOptionalModulesContainsCourse(finishedCourse))
                .filter(finishedCourse -> !transferableSkills.contains(finishedCourse))
                .collect(Collectors.toSet());

        courseReport.setRemainingMandatoryCourses(remainingMandatoryCourses);
        courseReport.setRemainingUnassignedFinishedCourses(unassignedCourses);
    }

    private Set<Course> determineRemainingMandatoryCourses() {

        Set<Course> remainingMandatoryCourses = mandatoryCourses
                .stream()
                .filter(mandatoryCourse -> !finishedCourses.contains(mandatoryCourse))
                .collect(Collectors.toSet());

        if (transitionalProvision == null) {
            return remainingMandatoryCourses;
        }

        for (Course finishedCourse : finishedCourses) {
            CourseGroup additionalMandatoryCourseGroup = transitionalProvision.getAdditionalMandatoryCourseGroupOfCourse(finishedCourse);
            CourseGroup mandatoryCourseGroup = transitionalProvision.getMandatoryCourseGroupOfCourse(finishedCourse);
            if (mandatoryCourseGroup != null) {
                remainingMandatoryCourses = remainingMandatoryCourses
                        .stream()
                        .filter(remainingCourse -> !mandatoryCourseGroup.containsCourse(remainingCourse))
                        .collect(Collectors.toSet());
            } else if(additionalMandatoryCourseGroup != null) {
                remainingMandatoryCourses = remainingMandatoryCourses
                        .stream()
                        .filter(remainingCourse -> !additionalMandatoryCourseGroup.containsCourse(remainingCourse))
                        .collect(Collectors.toSet());
            }
        }

        return remainingMandatoryCourses;
    }

    /**
     * Returns the detailed result of the courses.
     *
     * <p>This method should only be used after {@link #analyzeCourses()} has
     * been called to ensure a correct report.</p>
     * @return the detailed result of the courses.
     */
    public CourseReport getCourseReport() {
        return courseReport;
    }
}
