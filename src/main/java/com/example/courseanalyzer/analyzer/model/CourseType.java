package com.example.courseanalyzer.analyzer.model;
/**
 * @Package: com.example.courseanalyzer.analyzer.model
 * @Class: CourseType
 * @Author: Jan
 * @Date: 29.01.2019
 */

/**
 *
 * Represents the type of a course which determine how
 *
 */
public enum CourseType {

    VO("VO"),
    UE("UE"),
    VU("VU"),
    PR("PR"),
    SE("SE");

    private String courseType;
    CourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getCourseType() {
        return courseType;
    }
}
