package com.example.courseanalyzer.analyzer.studyplananalyzer.moduleanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.studyplananalyzer.moduleanalyzer
 * @Class: ModuleAnalyzer
 * @Author: Jan
 * @Date: 04.02.2019
 */

import com.example.courseanalyzer.analyzer.WrongFormatException;
import com.example.courseanalyzer.analyzer.studyplananalyzer.model.Module;

import java.util.Set;

/**
 * Extracts the modules informations.
 *
 * <p>This informations are found in the corresponding study plan under the
 *    chapter of all courses assigned to their corresponding modules.
 * </p>
 *
 * <p>These interface is used to conveniently change the implementation because
 *    the plan may change and a new implementation is necessary.</p>
 */
public interface ModuleAnalyzer {

    /**
     * Returns the set of modules which is extracted from the text {@code modulesText}.
     *
     * @param modulesText contains the modules.
     * @throws IllegalArgumentException is thrown, when {@code modulesText} is
     *                                  {@code null}.
     * @throws WrongFormatException is thrown, when {@code modulesText} is empty
     *                              or the passed creates an empty list of
     *                              modules.
     * @return the set of modules which is extracted from the text {@code modulesText}.
     */
    Set<Module> analyzeModule(String modulesText);
}
