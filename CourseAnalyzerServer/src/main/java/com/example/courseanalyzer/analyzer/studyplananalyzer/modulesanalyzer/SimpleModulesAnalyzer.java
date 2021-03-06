package com.example.courseanalyzer.analyzer.studyplananalyzer.modulesanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.studyplananalyzer.modulesanalyzer
 * @Class: SimpleModulesAnalyzer
 * @Author: Jan
 * @Date: 04.02.2019
 */

import com.example.courseanalyzer.analyzer.exception.NoModelsExtractedException;
import com.example.courseanalyzer.analyzer.exception.WrongFormatException;
import com.example.courseanalyzer.util.CourseLineUtil;
import com.example.courseanalyzer.analyzer.model.Course;
import com.example.courseanalyzer.analyzer.studyplananalyzer.model.Module;
import com.example.courseanalyzer.util.ValidationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Analyzes the modules by simply assuming that each module starts with the name
 * of the module and mark, if they are optional. This implementation is used for
 *  the study plan of the computer science curriculum of TU Wien thus an
 *  error can occure, if other study plans are used with different formatting.
 *
 * <p>The order is <i>[ects]/[weeklyHour]_[courseType]_[courseName]</i></p>
 * <p>Every other information is simply ignored</p>
 */
@Component("SimpleModulesAnalyzer")
public class SimpleModulesAnalyzer implements ModulesAnalyzer {

    private static final String MODULE_FORMAT = "[*]?Modul ”[\\w|äöüÄÖÜß| |\\-|&]+“ \\(.*[mindestens ]?\\d+,\\d+ECTS\\)";

    private static final String COURSE_FORMAT = "\\w+,\\w+\\/\\w+,\\w+ .*[VO|UE|VU|PR|SE] [\\w|äöüÄÖÜß| |*-|*&]+";

    private static final Logger logger = LogManager.getLogger(SimpleModulesAnalyzer.class);

    private Set<Module> modules;

    @Override
    public void analyzeModule(String modulesText) {

        throwExceptionIfTextIsEmpty(modulesText);

        List<String> processedLines = getProcessedLinesFromText(modulesText);
        boolean isProcessing = false;
        boolean isFirst = true;
        Module module = null;
        Set<Course> courses = null;

        this.modules = new HashSet<>();

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

                module.setName(getModuleName(line));

                if (line.charAt(0) == '*') {
                    module.setMandatory(false);
                }

                isProcessing = true;

            } else if (isLinePattern(line, COURSE_FORMAT)) {
                courses.add(CourseLineUtil.getCourseFromLineWithWeeklyHours(line));
            } else if(isProcessing && !isLinePageNumber(line)){
                module.setCourses(courses);
                modules.add(module);
                isProcessing = false;
            }
        }
        if (module != null) {
            module.setCourses(courses);
            modules.add(module);
        }

        ValidationUtil.validateNonEmptySet(modules, "module");
    }

    private void throwExceptionIfTextIsEmpty(String text) {
        //TODO: extract into validation util because similar method exist
        String errorMsg;
        if (text == null) {
            errorMsg = "The passed module text is null";

            logger.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        } else if (text.isEmpty()) {
            errorMsg = "The passed module text is empty";

            logger.error(errorMsg);
            throw new WrongFormatException(errorMsg);
        }
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
                        !isLinePageNumber(line) &&
                        !line.contains("Prüfungsfach")) {
                String correctLine = processedLines.get(size - 1) + " " + line;

                if (correctLine.contains("- ")) {
                    correctLine = correctLine.replaceAll("- ", "");
                }

                processedLines.set(size - 1, correctLine);
            }
        }

        return processedLines;
    }

    private String getModuleName(String line) {
        String modulePrefix = line.contains("*") ? "*Modul " : "Modul ";
        int indexOfQuotation = line.indexOf("“");
        return line.substring(modulePrefix.length() + 1, indexOfQuotation);
    }

    private boolean isUsedInformationLine(String line) {
        return isLinePattern(line, MODULE_FORMAT) ||
                isLinePattern(line, COURSE_FORMAT);
    }

    private boolean isLinePattern(String line, String patternFormat) {
        Pattern pattern = Pattern.compile(patternFormat);
        Matcher matcher = pattern.matcher(line);

        return matcher.matches();
    }

    private boolean isLinePageNumber(String line) {
        return isLinePattern(line, "\\d{1,3}");
    }

    @Override
    public Set<Module> getModules() {
        return modules;
    }
}
