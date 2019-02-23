package com.example.courseanalyzer.analyzer.studyplananalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.studyplananalyzer
 * @Class: StudyPlanAnalyzer
 * @Author: Jan
 * @Date: 03.02.2019
 */

import com.example.courseanalyzer.analyzer.exception.WrongFormatException;
import com.example.courseanalyzer.analyzer.studyplananalyzer.model.Module;
import com.example.courseanalyzer.analyzer.model.Course;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import java.util.Set;

/**
 * Extracts the mandatory and addtional mandatory course informations from the
 * requests.
 *
 * <p>This interface aim is to extract these informations from different types
 *    of study plans. The analysis heavily relies on the format and assumes that
 *    the format is consistent. When the format varies between different study
 *    plans, a new implementation of this interface is advised.</p>
 *
 * <p>The process should consist of the following steps:</p>
 * <ol>
 *     <li>Extraction of the table of content</li>
 *     <li>Extraction of all mandatory courses</li>
 *     <li>Extraction of all modules</li>
 *     <li>Extraction of all transferable skills.</li>
 * </ol>
 *
 * <p>This informations should be found in the study plan. They contain the list
 *    of courses in seperate chapters.</p>
 */
public interface StudyPlanAnalyzer {

    /**
     * Analysis the file {@code multiPartFile} and assign the result to the sets
     * of mandatory courses, modules and transferable skills.
     *
     * @param multiPartFile contains the study plan.
     * @throws IllegalArgumentException is thrown, when one necessary parameters
     *                                  is {@code null}.
     * @throws WrongFormatException is thrown, when the format of table of
     *                              content, text of mandatory courses, text of
     *                              modules or text of the transferable skills
     *                              is wrong.
     */
    void analyzeStudyPlan(MultipartFile multiPartFile);

    /**
     * Returns the set of mandatory courses.
     *
     * <p>This method should be called after
     * {@link #analyzeStudyPlan(MultipartFile)}.</p>
     *
     * @return the set of mandatory courses.
     */
    Set<Course> getMandatoryCourses();

    /**
     * Returns the set of additional mandatory courses.
     *
     * <p>This method should be called after
     * {@link #analyzeStudyPlan(MultipartFile)}.</p>
     *
     * @return the set of additional mandatory courses.
     */
    Set<Module> getModules();

    /**
     * Returns the set of transferable skills.
     *
     * <p>This method should be called after
     *    {@link #analyzeStudyPlan(MultipartFile)}.</p>
     *
     * @return the set of transferable skills.
     */
    Set<Course> getTransferableSkills();
}
