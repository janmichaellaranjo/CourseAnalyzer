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

    @Override
    public Set<Course> analyzeTransferableSkills(String transferableSkillsText) {

        throwExceptionIfTextIsEmpty(transferableSkillsText);

        Set<Course> transferableSkills = new HashSet<>();
        Scanner scanner = new Scanner(transferableSkillsText);

        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (CourseLineUtil.isLineValidCourseWithWeeklyHoursInformation(line)) {
                Course transferableSkill = CourseLineUtil.getCourseFromLineWithWeeklyHours(line);
                transferableSkills.add(transferableSkill);
            } else if (!transferableSkills.isEmpty()) {
                String errorMsg ="The transferable skill could not be extracted from %i.line." +
                        "The line contains invalid or incomplete informations";
                throw new WrongFormatException(errorMsg);
            }
            i++;
        }

        if (transferableSkills.isEmpty()) {
            String errorMsg = "The passed transferable skills text creates a list of transferable skills that is empty";

            logger.error(errorMsg);

            throw new NoModelsExtractedException(errorMsg);
        }

        return transferableSkills;
    }

    private void throwExceptionIfTextIsEmpty(String text) {
        //TODO: extract into validation util because similar method exist
        if (text == null) {
            throw new IllegalArgumentException("The passed transferable skills text is null");
        } else if (text.isEmpty()) {
            throw new WrongFormatException("The passed transferable skills text is empty");
        }
    }
}
