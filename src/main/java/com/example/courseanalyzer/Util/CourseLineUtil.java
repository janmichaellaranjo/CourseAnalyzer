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
 * Extracts the course information from the line depending of the format of the
 * line thus {@link #getCourseFromLine(String)} should be called on lines without
 * any weekly hours stated in and {@link #getCourseFromLineWithWeeklyHours(String)}
 * that contains the weekly hours informations in the line.
 *
 * <p>To ensure that no error occures, {@link #isLineValidCourseWithoutWeeklyHoursInformation(String)}
 *    should be called on lines that should not contain weekly hours and
 *    {@link #isLineValidCourseWithWeeklyHoursInformation(String)} should be called
 *    on lines that contain the weekly hours.</p>
 */
public final class CourseLineUtil {
    private static final String COURSE_INFORMATION_FORMAT = "[\\*|•]?[ ]?[0-9]+[,|.][0-9] [\\w|äöüÄÖÜß]+[\\w|äöüÄÖÜß| |+]*";
    private static final String COURSE_WITH_WEEKLY_HRS_FORMAT = "\\w+,\\w+\\/\\w+,\\w+ .*[VO|UE|VU|PR|SE] [\\w|äöüÄÖÜß| |*-|*&]+";
    private static final String COMMA = ",";
    private static final String DECIMAL_POINT = ".";

    /**
     *
     * Returns true, if the line has the format of a course without the weekly
     * hours information.
     *
     * <p>The format of the line is {@value #COURSE_INFORMATION_FORMAT}.</p>
     *
     * @param line the examined line.
     * @return true, if the line has the format of a course without the weekly
     *         hours information.
     */
    public static boolean isLineValidCourseWithoutWeeklyHoursInformation(String line) {
        Pattern pattern = Pattern.compile(COURSE_INFORMATION_FORMAT);
        Matcher matcher = pattern.matcher(line);

        return matcher.matches();
    }

    /**
     *
     * Returns true, if the line has the format of a course without the weekly
     * hours information.
     *
     * <p>The format of the line is {@value #COURSE_INFORMATION_FORMAT}.</p>
     *
     * @param line the examined line.
     * @return true, if the line has the format of a course without the weekly
     *         hours information.
     */
    public static boolean isLineValidCourseWithWeeklyHoursInformation(String line) {
        Pattern pattern = Pattern.compile(COURSE_WITH_WEEKLY_HRS_FORMAT);
        Matcher matcher = pattern.matcher(line);

        return matcher.matches();
    }

    /**
     *
     * Returns the course by extracting the informations from the line
     * {@code line}.
     *
     * <p>It is assumed that {@link #isLineValidCourseWithoutWeeklyHoursInformation(String)} is
     *    called before to ensure that the informations are extracted correctly.
     * </p>
     * <p>This method should be called on lines that doesn't contain the weekly
     *    hours.
     * </p>
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

    /**
     *
     * Returns the course type depending on the line {@code line}.
     *
     * <p>The method returns {@code null}, if the line does not contain any
     *    valid course type.</p>
     *
     * @param line the examined line
     * @return the course type depending on the line {@code line}.
     */
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

    /**
     *
     * Returns the course by extracting the informations from the line
     * {@code line}.
     *
     * <p>It is assumed that {@link #isLineValidCourseWithoutWeeklyHoursInformation(String)} is
     *    called before to ensure that the informations are extracted correctly.
     * </p>
     * <p>This method should be called on lines that does contain the weekly
     *    hours.
     * </p>
     *
     * @param line the examined line which is used to extract the course
     * @return the Course by extracting the informations from the line
     * {@code line}.
     */
    public static Course getCourseFromLineWithWeeklyHours(String line) {
        Course course = new Course();
        String processedLine = line.replaceAll(",", ".");
        course.setEcts(Float.parseFloat(processedLine.substring(0, line.indexOf("/"))));
        course.setCourseType(getCourseTypeFromLine(processedLine));
        course.setCourseName(getCourseNameFromLine(processedLine));
        return course;
    }

}
