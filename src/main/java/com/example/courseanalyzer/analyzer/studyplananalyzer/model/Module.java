package com.example.courseanalyzer.analyzer.studyplananalyzer.model;
/**
 * @Package: com.example.courseanalyzer.analyzer.studyplananalyzer.model
 * @Class: Module
 * @Author: Jan
 * @Date: 04.02.2019
 */

import com.example.courseanalyzer.analyzer.model.Course;

import java.util.Set;

/**
 *
 * Represents the module of the curriculum which consists of courses. Module can
 * be mandatory or optional.
 * <p>Due to the curriculum a student has to achieve a minimum of 6 ECTs of
 *    courses of an optional module and has to collect a minimum 12 ECTs of
 *    optional modules.
 * </p>
 * <p>A module is default {@code true}</p>
 */
public class Module {
    private boolean isMandatory;

    private Set<Course> courses;

    public Module() {
        this.isMandatory = true;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    /**
     *
     * Returns {@code true}, if the module contains the course {@code course}.
     * This automatically returns {@code false}, when the module does not
     * contain any courses.
     *
     * @param course the examined course
     * @return {@code true}, if the module contains the course {@code course}.
     */
    public boolean containsCourse(Course course) {
        if (courses == null) {
            return false;
        }

        return courses.contains(course);
    }

    @Override
    public String toString() {
        if (courses == null || courses.isEmpty()) {
            return "[]";
        }

        String output = "";
        for (Course course : courses) {
            output += course + "\n";
        }

        return output;
    }
}
