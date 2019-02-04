package com.example.courseanalyzer.analyzer.studyplananalyzer.moduleanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.studyplananalyzer.moduleanalyzer
 * @Class: SimpleModuleAnalyzer
 * @Author: Jan
 * @Date: 04.02.2019
 */

import com.example.courseanalyzer.Util.CourseLineUtil;
import com.example.courseanalyzer.analyzer.model.Course;
import com.example.courseanalyzer.analyzer.model.CourseType;
import com.example.courseanalyzer.analyzer.studyplananalyzer.model.Module;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Analyzes the modules by simply assuming that each module starts with the name
 * of the module and mark, if they are optional. These consists of the courses.
 *
 * <p>The order is <i><ects>/<weeklyHourse>_<courseType>_<courseName></i></p>
 * <p>Every other information is simply ignored</p>
 */
public class SimpleModuleAnalyzer implements ModuleAnalyzer {
    private static final String MODULE_FORMAT = "[*]?Modul ”[\\w|äöüÄÖÜß| |\\-|&]+“ \\(\\d+,\\d+ECTS\\)";
    private static final String COURSE_FORMAT = "\\w+,\\w+\\/\\w+,\\w+ .*[VO|UE|VU|PR|SE] [\\w|äöüÄÖÜß| |*-|*&]+";
    private static final String EXAM_MODULE_TITLE_FORMAT = "Prüfungsfach .*[„|”][\\w|äöüÄÖÜß| |(|)|:|-]+“?";

    @Override
    public Set<Module> analyzeModule(String modulesText) {
        List<String> processedLines = getProcessedLinesFromText(modulesText);
        boolean isProcessing = false;
        boolean isFirst = true;
        Set<Module> modules = new HashSet<>();
        Module module = null;
        Set<Course> courses = null;
        int i = 0;
        for (String line : processedLines) {
            if (isLinePattern(line, MODULE_FORMAT)) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    module.setCourses(courses);
                    modules.add(module);
                }

                module = new Module();
                courses = new HashSet<>();
                if (line.charAt(0) == '*') {
                    module.setMandatory(false);
                }
                isProcessing = true;

            } else if (isLinePattern(line, COURSE_FORMAT)) {
                courses.add(getCourseFromLine(line));
            } else if(isProcessing && !isLinePageNumber(line)){
                module.setCourses(courses);
                modules.add(module);
                isProcessing = false;
            }
            i++;
        }

        module.setCourses(courses);
        modules.add(module);

        return modules;
    }

    private List<String> getProcessedLinesFromText(String text) {
        List<String> processedLines = new ArrayList<>();
        Scanner scanner = new Scanner(text);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            int size = processedLines.size();
            if (isUsedInformationLine(line)) {
                processedLines.add(line);
            } else if (size > 1 &&
                        isUsedInformationLine(processedLines.get(size - 1)) &&
                        !isLinePageNumber(line)) {
                String correctLine = processedLines.get(size - 1) + " " + line;

                if (correctLine.contains("- ")) {
                    correctLine = correctLine.replaceAll("- ", "");
                }

                processedLines.set(size - 1, correctLine);
            } else {
                //processedLines.add(line);
            }
        }

        return processedLines;
    }

    private boolean isUsedInformationLine(String line) {
        return isLinePattern(line, MODULE_FORMAT) ||
                isLinePattern(line, COURSE_FORMAT) ||
                isLinePattern(line, EXAM_MODULE_TITLE_FORMAT);
    }

    private boolean isLinePattern(String line, String patternFormat) {
        Pattern pattern = Pattern.compile(patternFormat);
        Matcher matcher = pattern.matcher(line);

        return matcher.matches();
    }

    private boolean isLinePageNumber(String line) {
        return isLinePattern(line, "\\d{1,3}");
    }

    private Course getCourseFromLine(String line) {
        Course course = new Course();
        String processedLine = line.replaceAll(",", ".");
        course.setEcts(Float.parseFloat(processedLine.substring(0, line.indexOf("/"))));
        course.setCourseType(getCourseTypeFromLine(processedLine));
        course.setCourseName(getCourseNameFromLine(processedLine));
        return course;
    }

    private CourseType getCourseTypeFromLine(String line) {
        int startIndexOfCourseType = getIndexOfCourseType(line);
        String courseTypeAsString = line.substring(startIndexOfCourseType, startIndexOfCourseType + 2);

        return CourseLineUtil.getCourseTypeFromLine(courseTypeAsString);
    }

    private int getIndexOfCourseType(String line) {
        for (CourseType courseType : CourseType.values()) {
            //assume that CourseType does not occure in course name
            if (line.contains(courseType.getCourseType())) {
                return line.indexOf(courseType.getCourseType());
            }
        }
        return -1;
    }

    private String getCourseNameFromLine(String line) {
        int startIndexOfCourseType = getIndexOfCourseType(line);

        return line.substring(startIndexOfCourseType + 3, line.length())
                .trim();
    }
}
