package com.example.courseanalyzer.analyzer.studyplananalyzer.transferableskillsanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.studyplananalyzer.transferableskillsanalyzer
 * @Class: SimpleTransferableSkills
 * @Author: Jan
 * @Date: 04.02.2019
 */

import com.example.courseanalyzer.Util.CourseLineUtil;
import com.example.courseanalyzer.analyzer.model.Course;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Analyzes the list of the transferable skills. This implementation is used for
 * the study plan of the computer science curriculum of TU Wien thus an error
 * can occure, if other study plans are used with different formatting.
 *
 * <p>Each step of the analysis can be exchanged with a new implementation, if
 *    necessary.</p>
 *
 */
public class SimpleTransferableSkills implements TransferableSkillsAnalyzer {

    @Override
    public Set<Course> analyzeTransferableSkills(String transferableSkillsText) {
        Set<Course> transferableSkills = new HashSet<>();
        Scanner scanner = new Scanner(transferableSkillsText);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (CourseLineUtil.isLineValidCourseWithWeeklyHoursInformation(line)) {
                Course transferableSkill = CourseLineUtil.getCourseFromLineWithWeeklyHours(line);
                transferableSkills.add(transferableSkill);
            }
        }

        return transferableSkills;
    }

}
