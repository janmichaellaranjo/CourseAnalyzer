package com.example.courseanalyzer.analyzer.model;
/**
 * @Package: com.example.courseanalyzer.analyzer.model
 * @Class: CourseReport
 * @Author: Jan
 * @Date: 01.02.2019
 */

import java.util.Set;

/**
 * Contains the result of the comparison between the finished courses and the
 * mandatory courses, transferable skills and courses in optional modules.
 * Following informations are included:
 * <ul>
 *     <li>The ects of mandatory courses</li>
 *     <li>The ects of additional mandatory courses</li>
 *     <li>The ects of courses from optional modules</li>
 *     <li>The ects of transferable skills</li>
 *     <li>The list of remaining mandatory courses</li>
 *     <li>The list of unnassigned finished courses</li>
 * </ul>
 */
public class CourseReport {

    private float mandatoryCoursesEcts;

    private float additionalMandatoryCoursesEcts;

    private float optionalModuleEcts;

    private float transferableSkillsEcts;

    private Set<Course> remainingMandatoryCourses;

    private Set<Course> remainingUnassignedFinishedCourses;

    public float getMandatoryCoursesEcts() {
        return mandatoryCoursesEcts;
    }

    public void setMandatoryCoursesEcts(float mandatoryCoursesEcts) {
        this.mandatoryCoursesEcts = mandatoryCoursesEcts;
    }

    public float getAdditionalMandatoryCoursesEcts() {
        return additionalMandatoryCoursesEcts;
    }

    public void setAdditionalMandatoryCoursesEcts(float additionalMandatoryCoursesEcts) {
        this.additionalMandatoryCoursesEcts = additionalMandatoryCoursesEcts;
    }

    public float getOptionalModuleEcts() {
        return optionalModuleEcts;
    }

    public void setOptionalModuleEcts(float optionalModuleEcts) {
        this.optionalModuleEcts = optionalModuleEcts;
    }

    public float getTransferableSkillsEcts() {
        return transferableSkillsEcts;
    }

    public void setTransferableSkillsEcts(float transferableSkillsEcts) {
        this.transferableSkillsEcts = transferableSkillsEcts;
    }

    public Set<Course> getRemainingMandatoryCourses() {
        return remainingMandatoryCourses;
    }

    public void setRemainingMandatoryCourses(Set<Course> remainingMandatoryCourses) {
        this.remainingMandatoryCourses = remainingMandatoryCourses;
    }

    public Set<Course> getRemainingUnassignedFinishedCourses() {
        return remainingUnassignedFinishedCourses;
    }

    public void setRemainingUnassignedFinishedCourses(Set<Course> remainingUnassignedFinishedCourses) {
        this.remainingUnassignedFinishedCourses = remainingUnassignedFinishedCourses;
    }

    @Override
    public String toString() {
        return "[" +
                "mandatoryCoursesEcts: " +
                mandatoryCoursesEcts +
                ", remainingMandatoryCourses: " +
                remainingMandatoryCourses +
                ", optionalModuleEcts: " +
                optionalModuleEcts +
                ", transferableSkillsEcts: " +
                transferableSkillsEcts +
                "]";
    }
}
