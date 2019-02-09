package com.example.courseanalyzer.analyzer.studyplananalyzer.transferableskillsanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.studyplananalyzer.transferableskillsanalyzer
 * @Class: TransferableSkillsAnalyzer
 * @Author: Jan
 * @Date: 04.02.2019
 */

import com.example.courseanalyzer.analyzer.WrongFormatException;
import com.example.courseanalyzer.analyzer.model.Course;

import java.util.Set;

/**
 * Extracts the transferable skills informations.
 *
 * <p>This informations are found in the corresponding study plan under the
 *    chapter that lists some possible transferable skills.
 * </p>
 *
 * <p>These interface is used to conveniently change the implementation because
 *    the list may change and a new implementation is due to a new format
 *    necessary.</p>
 */
public interface TransferableSkillsAnalyzer {

    /**
     * Returns the set of the transferable skills from the  text
     * {@code mandatoryCourses}
     *
     * <p>This list can be empty, if the text does not meet the format
     *    criteria.</p>
     *
     * @param transferableSkillsText contains the mandatory courses
     * @throws IllegalArgumentException is thrown, when {@code modulesText} is
     *                                  {@code null}.
     * @throws WrongFormatException is thrown, when the format of
     *                              {@code transferableSkillsText} is empty or
     *                              the format of the text is wrong such that
     *                              a empty list of transferable skills is
     *                              created.
     * @return the set of the transferable skills from the  text
     *        {@code mandatoryCourses}
     */
    Set<Course> analyzeTransferableSkills(String transferableSkillsText);
}
