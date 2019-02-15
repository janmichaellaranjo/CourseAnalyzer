package com.example.courseanalyzer.analyzer.model;
/**
 * @Package: com.example.courseanalyzer.analyzer.model
 * @Class: TransitionalProvision
 * @Author: Jan
 * @Date: 02.02.2019
 */

import java.util.Set;

/**
 * Represents the transitional provision for an upcoming semester. This consists
 * of the mandatory courses and additional mandatory courses. These lists all
 * courses that are no longer available and also all actual courses.
 */
public class TransitionalProvision {


    private Set<CourseGroup> mandatoryCourseGroups;

    private Set<CourseGroup> additionalMandatoryCourseGroups;

    public Set<CourseGroup> getMandatoryCourseGroups() {
        return mandatoryCourseGroups;
    }

    public void setMandatoryCourseGroups(Set<CourseGroup> mandatoryCourseGroups) {
        this.mandatoryCourseGroups = mandatoryCourseGroups;
    }

    public Set<CourseGroup> getAdditionalMandatoryCourseGroups() {
        return additionalMandatoryCourseGroups;
    }

    public void setAdditionalMandatoryCourseGroups(Set<CourseGroup> additionalMandatoryCourseGroups) {
        this.additionalMandatoryCourseGroups = additionalMandatoryCourseGroups;
    }

    /**
     *
     * Returns the mandatory  course group of corresponding Course
     * {@code examinedCourse}.
     *
     * <p>Returns {@code null}, if no corresponding group exists.</p>
     *
     * @param examinedCourse the course that is examined
     * @return the mandatory course group of corresponding Course
     *         {@code examinedCourse}.
     */
    public CourseGroup getMandatoryCourseGroupOfCourse(Course examinedCourse) {
        for (CourseGroup mandatoryCourseGroup : mandatoryCourseGroups) {
            if (mandatoryCourseGroup.isCourseInGroup(examinedCourse)) {
                return mandatoryCourseGroup;
            }
        }
        return null;
    }

    /**
     *
     * Returns the additional mandatory  course group of corresponding Course
     * {@code examinedCourse}.
     *
     * <p>Returns {@code null}, if no corresponding group exists.</p>
     *
     * @param examinedCourse the course that is examined
     * @return the additional mandatory course group of corresponding Course
     *         {@code examinedCourse}.
     */
    public CourseGroup getAdditionalMandatoryCourseGroupOfCourse(Course examinedCourse) {
        for (CourseGroup additionalMandatoryCourseGroup : additionalMandatoryCourseGroups) {
            if (additionalMandatoryCourseGroup.isCourseInGroup(examinedCourse)) {
                return additionalMandatoryCourseGroup;
            }
        }
        return null;
    }

    /**
     *
     * Returns {@code true}, if the mandatory course groups contains the course
     * {@code examinedCourse}.
     *
     * @param examinedCourse the course which is examined
     * @return {@code true}, if the mandatory course groups contains the course
     *         {@code examinedCourse}.
     */
    public boolean containsMandatoryCourse(Course examinedCourse) {
        for (CourseGroup mandatoryCourseGroup : mandatoryCourseGroups) {
            if (mandatoryCourseGroup.isCourseInGroup(examinedCourse)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * Returns {@code true}, if the additional mandatory course groups contains
     * the course @code examinedCourse}.
     *
     * @param examinedCourse the course which is examined
     * @return {@code true}, if the additional mandatory course groups contains
     *         the course {@code examinedCourse}.
     */
    public boolean containsAdditionalMandatoryCourse(Course examinedCourse) {
        for (CourseGroup additionalMandatoryCourseGroup : additionalMandatoryCourseGroups) {
            if (additionalMandatoryCourseGroup.isCourseInGroup(examinedCourse)) {
                return true;
            }
        }
        return false;
    }
}
