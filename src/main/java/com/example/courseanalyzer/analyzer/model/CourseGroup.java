package com.example.courseanalyzer.analyzer.model;
/**
 * @Package: com.example.courseanalyzer.analyzer.model
 * @Class: CourseGroup
 * @Author: Jan
 * @Date: 15.02.2019
 */

import java.util.HashSet;
import java.util.Set;

/**
 * Contains courses in the transitional provision that can be attended instead
 * of another courses in that group.
 */
public class CourseGroup {

    private Set<Course> courses;

    public CourseGroup() {
        this.courses = new HashSet<>();
    }

    /**
     *
     * Returns {@code true}, if the group contains {@code examinedCourse}.
     *
     * @param examinedCourse the examined course
     * @return {@code true}, if the group contains {@code examinedCourse}.
     */
    public boolean isCourseInGroup(Course examinedCourse) {
        return courses.contains(examinedCourse);
    }

    /**
     *
     * Adds a course to the group.
     *
     * @param course the course which will be added
     */
    public void addCourse(Course course) {
        courses.add(course);
    }

    /**
     *
     * Returns {@code true}, if the group is empty.
     *
     * @return {@code true}, if the group is empty.
     */
    public boolean isCourseGroupEmpty() {
        return courses.isEmpty();
    }

    /**
     *
     * Returns {@true}, if the group contains the examined course
     * {@code examinedCourse}.
     *
     * @param examinedCourse the examined course
     * @return {@true}, if the group contains the examined course
     *         {@code examinedCourse}.
     */
    public boolean containsCourse(Course examinedCourse) {
        return courses.contains(examinedCourse);
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CourseGroup)) {
            return false;
        }

        CourseGroup otherCourseGroup = (CourseGroup) o;

        return this.courses.equals(otherCourseGroup.courses);
    }

    @Override
    public int hashCode() {
        int hashCode = 0;

        for (Course course : courses) {
            hashCode += 37 * course.hashCode();
        }

        return hashCode;
    }

    @Override
    public String toString() {
        return courses.toString();
    }
}
