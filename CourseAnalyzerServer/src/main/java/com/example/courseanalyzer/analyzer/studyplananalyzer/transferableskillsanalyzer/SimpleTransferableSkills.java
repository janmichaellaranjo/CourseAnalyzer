package com.example.courseanalyzer.analyzer.studyplananalyzer.transferableskillsanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.studyplananalyzer.transferableskillsanalyzer
 * @Class: SimpleTransferableSkills
 * @Author: Jan
 * @Date: 04.02.2019
 */

import com.example.courseanalyzer.analyzer.exception.NoModelsExtractedException;
import com.example.courseanalyzer.analyzer.exception.WrongFormatException;
import com.example.courseanalyzer.util.CourseLineUtil;
import com.example.courseanalyzer.analyzer.model.Course;
import com.example.courseanalyzer.util.ValidationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Analyzes the list of the transferable skills. This implementation is used for
 * the study plan of the computer science curriculum of TU Wien thus an error
 * can occure, if other study plans are used with different formatting.
 *
 * <p>Each step of the analysis can be exchanged with a new implementation, if
 *    necessary.</p>
 */
@Component("SimpleTransferableSkills")
public class SimpleTransferableSkills implements TransferableSkillsAnalyzer {

    private static final Logger logger = LogManager.getLogger(SimpleTransferableSkills.class);

    private Set<Course> transferableSkills;

    @Override
    public void analyzeTransferableSkills(String transferableSkillsText) {

        throwExceptionIfTextIsEmpty(transferableSkillsText);

        this.transferableSkills = new HashSet<>();
        Scanner scanner = new Scanner(transferableSkillsText);

        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (CourseLineUtil.isLineValidCourseWithWeeklyHoursInformation(line)) {
                Course transferableSkill = CourseLineUtil.getCourseFromLineWithWeeklyHours(line);
                if (transferableSkill.isInformationComplete()) {
                    transferableSkills.add(transferableSkill);
                } else {
                    String errorMsg = String.format(
                            "The transferable skill could not be extracted from %d.line." +
                                    "The line contains invalid or incomplete informations",
                            i);
                    throw new WrongFormatException(errorMsg);
                }
            }
            i++;
        }

        ValidationUtil.validateNonEmptySet(transferableSkills, "transferable skills");
    }

    private void throwExceptionIfTextIsEmpty(String text) {
        //TODO: extract into validation util because similar method exist
        if (text == null) {
            throw new IllegalArgumentException("The passed transferable skills text is null");
        } else if (text.isEmpty()) {
            throw new WrongFormatException("The passed transferable skills text is empty");
        }
    }

    @Override
    public Set<Course> getTransferableSkills() {
        return transferableSkills;
    }
}
