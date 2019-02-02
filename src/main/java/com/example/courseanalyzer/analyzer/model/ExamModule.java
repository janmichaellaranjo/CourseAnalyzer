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

    private Set<Course> additionalMandatoryCourses;

    public Set<Course> getAdditionalMandatoryCourses() {
        return additionalMandatoryCourses;
    }

    public void setAdditionalMandatoryCourses(Set<Course> additionalMandatoryCourses) {
        this.additionalMandatoryCourses = additionalMandatoryCourses;
    }

    @Override
    public String toString() {
        if (additionalMandatoryCourses == null || additionalMandatoryCourses.isEmpty()) {
            return "[empty]";
        }
        return additionalMandatoryCourses.toString();
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
