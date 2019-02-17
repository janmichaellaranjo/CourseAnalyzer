package com.example.courseanalyzer.Util;
/**
 * @Package: com.example.courseanalyzer.Util
 * @Class: ModelUtil
 * @Author: Jan
 * @Date: 16.02.2019
 */

import com.example.courseanalyzer.analyzer.model.Course;
import com.example.courseanalyzer.analyzer.model.CourseGroup;
import com.example.courseanalyzer.analyzer.model.CourseType;
import com.example.courseanalyzer.analyzer.model.TransitionalProvision;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Provides methods that uses {@link Course}
 */
public final class ModelUtil {

    /**
     * Returns a Course with the {@code i} as the ects and is contained the name
     * of the course. Also the course types alternate between
     * {@link CourseType#VU} and {@link CourseType#VO}, if {@code i} is even.
     * @param i the integer
     * @return a Course with the {@code i} as the ects and is contained the name
     *         of the course.
     */
    public static Course createCourseFromInteger(int i) {
        CourseType courseType = i % 2 == 0 ? CourseType.VU : CourseType.VO;
        Course course = new Course();
        course.setCourseType(courseType);
        course.setCourseName("Course " + i);
        course.setEcts(i);
        return course;
    }

    /**
     * Returns the transitional provision containing course groups with 4
     * courses.
     * @return the transitional provision containing course groups with 4
     *         courses.
     */
    public static TransitionalProvision getExpectedTransitionalProvision() {
        final int sizeOfCourseGroups = 4;
        TransitionalProvision expectedTransitionalProvision = new TransitionalProvision();
        Set<CourseGroup> mandatoryCourseGroups = new HashSet(Arrays.asList(
                IntStream
                        .range(1, sizeOfCourseGroups + 1)
                        .mapToObj(ModelUtil::createCourseGroupFromInteger)
                        .toArray()
        ));

        Set<CourseGroup> additionalMandatoryCourseGroups = new HashSet(Arrays.asList(
                IntStream
                        .range(sizeOfCourseGroups + 1, sizeOfCourseGroups * 2 + 1)
                        .mapToObj(ModelUtil::createCourseGroupFromInteger)
                        .toArray()
        ));

        expectedTransitionalProvision.setMandatoryCourseGroups(mandatoryCourseGroups);
        expectedTransitionalProvision.setAdditionalMandatoryCourseGroups(additionalMandatoryCourseGroups);
        return expectedTransitionalProvision;
    }

    /**
     * Returns a course group containing 2 courses with.
     * @param num the integer
     * @return a course group containing 2 courses with.
     */
    public static CourseGroup createCourseGroupFromInteger(int num) {
        CourseGroup courseGroup = new CourseGroup();
        int num2 = 2 * num;
        int num1 = num2 - 1;
        Set<Course> courses = new HashSet<>(Arrays.asList(
                ModelUtil.createCourseFromInteger(num1),
                ModelUtil.createCourseFromInteger(num2)
        ));
        courseGroup.setCourses(courses);
        return courseGroup;
    }
}
