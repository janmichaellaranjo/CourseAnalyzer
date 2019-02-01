package com.example.courseanalyzer.analyzer.model;
/**
 * @Package: com.example.courseanalyzer.analyzer.model
 * @Class: CourseReport
 * @Author: Jan
 * @Date: 01.02.2019
 */

import java.util.Set;

/**
 *
 */
public class CourseReport {

    private float mandatoryCoursesEcts;

    private Set<Course> remainingMandatoryCourses;

    public float getMandatoryCoursesEcts() {
        return mandatoryCoursesEcts;
    }

    public void setMandatoryCoursesEcts(float mandatoryCoursesEcts) {
        this.mandatoryCoursesEcts = mandatoryCoursesEcts;
    }

    public Set<Course> getRemainingMandatoryCourses() {
        return remainingMandatoryCourses;
    }

    public void setRemainingMandatoryCourses(Set<Course> remainingMandatoryCourses) {
        this.remainingMandatoryCourses = remainingMandatoryCourses;
    }

    @Override
    public String toString() {
        return "[" +
                "mandatoryCoursesEcts: " + mandatoryCoursesEcts +
                ", remainingMandatoryCourses: " +
                remainingMandatoryCourses;
    }
}
