package com.example.courseanalyzer.analyzer.model;
/**
 * @Package: com.example.courseanalyzer.analyzer.model
 * @Class: ExamModule
 * @Author: Jan
 * @Date: 02.02.2019
 */

import java.util.Set;

/**
 * Represents a exam module of the transitional provisions. This is divided into
 * a set of the actual mandatory courses and the previous additional mandatory
 * courses that are no longer offered.
 */
public class ExamModule {

    private Set<Course> mandatoryCourses;

    private Set<Course> additionalMandatoryCourses;

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

    public Set<Course> getMandatoryCourses() {
        return mandatoryCourses;
    }

    public void setMandatoryCourses(Set<Course> mandatoryCourses) {
        this.mandatoryCourses = mandatoryCourses;
    }

    public Set<Course> getAdditionalMandatoryCourses() {
        return additionalMandatoryCourses;
    }

    public void setAdditionalMandatoryCourses(Set<Course> additionalMandatoryCourses) {
        this.additionalMandatoryCourses = additionalMandatoryCourses;
    }

    @Override
    public String toString() {
        String output = "";
        if (mandatoryCourses == null || mandatoryCourses.isEmpty()) {
            output += "mandatoryCourses: empty, ";
        } else {
            output += "mandatoryCourses:" + mandatoryCourses + ", ";
        }
        if (additionalMandatoryCourses == null || additionalMandatoryCourses.isEmpty()) {
            output += "additionalMandatoryCourses: empty";
        } else {
            output += "additionalMandatoryCourses:" + additionalMandatoryCourses;
        }
        return "[" + output + "]";
    }

    /**
     *
     * Returns true, if the examined course is in the set of mandatory courses
     * of the this module.
     *
     * @param examinedCourse the checked course.
     * @return true, if the examined course is in the set of mandatory courses
     *         of the this module.
     */
    public boolean containsMandatoryCourse(Course examinedCourse) {
        return mandatoryCourses.contains(examinedCourse);
    }

    /**
     *
     * Returns true, if the examined course is in the set of additional mandatory
     * courses of the this module.
     *
     * @param examinedCourse the checked course.
     * @return true, if the examined course is in the set of additional
     *         mandatory courses of the this module.
     */
    public boolean containsAdditionalMandatoryCourse(Course examinedCourse) {
        return additionalMandatoryCourses.contains(examinedCourse);
    }
}
