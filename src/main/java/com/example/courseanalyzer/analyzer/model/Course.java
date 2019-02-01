package com.example.courseanalyzer.analyzer.model;
/**
 * @Package: com.example.courseanalyzer.analyzer.model
 * @Class: Course
 * @Author: Jan
 * @Date: 29.01.2019
 */

/**
 *
 * Represents a Course of the university.
 * <p>Following attributes are needed for a certificate:
 * <ul>
 *     <li>course type</li>
 *     <li>course name</li>
 *     <li>ects-a positive value</li>
 * </ul>
 * </p>
 *
 */
public class Course {
    private CourseType courseType;
    private String courseName;
    private float ects;

    public Course() {}

    public Course(CourseType courseType, String courseName, float ects) {
        this.courseType = courseType;
        this.courseName = courseName;
        this.ects = ects;
    }

    /**
     *
     * Returns true, if every information of the course is filled.
     *
     * @return true, if every information of the course is filled.
     */
    public boolean isInformationComplete() {
        if (courseType == null) {
            return false;
        } else if (courseName == null || courseName.isEmpty()) {
            return false;
        } else if (ects == 0) {
            return false;
        }
        return true;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public float getEcts() {
        return ects;
    }

    public void setEcts(float ects) {
        this.ects = ects;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        } else if (!(other instanceof Course)) {
            return false;
        }
        Course otherCourse = (Course)other;
        return courseType.equals(otherCourse.courseType) && courseName.equals(otherCourse.courseName);
    }

    @Override
    public int hashCode() {
        int hashCode = 37 * courseName.hashCode();
        hashCode += 37 * courseType.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        String courseTypeAsString = courseType != null ? courseType.getCourseType() : "__";
        return "[" + ects + " " + courseTypeAsString + " " + courseName + "]";
    }
}
