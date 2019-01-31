package com.example.courseanalyzer.dto;
/**
 * @Package: com.example.courseanalyzer.dto
 * @Class: MandatoryCoursesDto
 * @Author: Jan
 * @Date: 25.01.2019
 */


/**
 *
 */
public class MandatoryCoursesDto {

    private String mandatoryCourses;


    public MandatoryCoursesDto() {}

    public MandatoryCoursesDto(String mandatoryCourses) {
        this.mandatoryCourses = mandatoryCourses;
    }

    public void setMandatoryCourses(String mandatoryCourses) {
        this.mandatoryCourses = mandatoryCourses;
    }

    public String getMandatoryCourses() {
        return mandatoryCourses;
    }


}
