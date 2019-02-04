package com.example.courseanalyzer.Util;
/**
 * @Package: com.example.courseanalyzer.Util
 * @Class: CourseLineUtil
 * @Author: Jan
 * @Date: 02.02.2019
 */

import com.example.courseanalyzer.analyzer.model.Course;
import com.example.courseanalyzer.analyzer.model.CourseType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extracts the course information from the line. To ensue that the extraction is
 * done correctly, {@link #isLineValidCourseInformation(String)} should be called
 * before {@link #getCourseFromLine(String)}.
 */
public final class CourseLineUtil {
    private static final String COURSE_INFORMATION_FORMAT = "[\\*|•]?[ ]?[0-9]+[,|.][0-9] [\\w|äöüÄÖÜß]+[\\w|äöüÄÖÜß| |+]*";
    private static final String COMMA = ",";
    private static final String DECIMAL_POINT = ".";

    public static boolean isLineValidCourseInformation(String line) {
        Pattern pattern = Pattern.compile(COURSE_INFORMATION_FORMAT);
        Matcher matcher = pattern.matcher(line);

        return matcher.matches();
    }

    /**
     *
     * Returns the Course by extracting the informations from the line
     * {@code line}.
     *
     * <p>It is assumed that {@link #isLineValidCourseInformation(String)} is
     *    called before to ensure that the informations are extracted correctly</p>
     *
     * @param line the examined line which is used to extract the course
     * @return the Course by extracting the informations from the line
     * {@code line}.
     */
    public static Course getCourseFromLine(String line) {
        Course course = new Course();
        String currentLine = line.replaceAll("[\\*|•]", "");
        course.setEcts(getEctsFromLine(currentLine));
        course.setCourseType(getCourseTypeFromLine(currentLine));
        course.setCourseName(getCourseNameFromLine(currentLine));
        return course;
    }

    private static float getEctsFromLine(String line) {
        int indexOfCourseType = getIndexOfCourseType(line);
        // needs to be shorten because the course name could contain a number
        String shortenedLine = line.substring(0, indexOfCourseType)
                .replaceAll(COMMA, DECIMAL_POINT);
        return Float.parseFloat(shortenedLine);
    }

    private static int getIndexOfCourseType(String line) {
        String courseTypeAsString = "";
        for (CourseType courseType : CourseType.values()) {
            if (line.contains(courseType.getCourseType())) {
                courseTypeAsString = courseType.getCourseType();
            }
        }

        return line.indexOf(courseTypeAsString);
    }

    public static CourseType getCourseTypeFromLine(String line) {
        for (CourseType courseType : CourseType.values()) {
            if (line.contains(courseType.getCourseType())) {
                return courseType;
            }
        }
        return null;
    }

    private static String getCourseNameFromLine(String line) {
        final int offset = 3;
        int indexOfCourseType = getIndexOfCourseType(line) + offset;
        return line.substring(indexOfCourseType, line.length());
    }
}
