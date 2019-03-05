package com.example.courseanalyzer.analyzer.transitionalprovisionanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.transitionalprovisionanalyzer
 * @Class: TransitionalProvisionAnalyzer
 * @Author: Jan
 * @Date: 02.02.2019
 */

import com.example.courseanalyzer.analyzer.exception.ReadFileException;
import com.example.courseanalyzer.analyzer.exception.WrongFormatException;
import com.example.courseanalyzer.analyzer.model.TransitionalProvision;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;

/**
 * Extracts the additional mandatory course informations.
 *
 * <p>This informations are found in the actual transitional provisions under the
 * chapter that contains the informations of previous courses that are no longer
 * offered. This informations must be copied and pasted into the textarea.</p>
 *
 * <p>These interface is used to conveniently change the implementation because
 * the format may change and a new implementation is necessary.</p>
 */
public interface TransitionalProvisionAnalyzer {

    /**
     * Analysis the request {@code request} and creates {@link TransitionalProvision}
     *
     * @param multipartFile contains the study plan.
     * @throws ReadFileException    is thrown, when an IO problem occurs while
     *                              reading the passed file.
     * @throws WrongFormatException is thrown, when the text on a page is empty
     *                              or the format creates an empty
     *                              {@link TransitionalProvision}
     */
    void analyzeTransitionalProvision(MultipartFile multipartFile);

    /**
     * Deletes the transitional provision and all determined informations such
     * as mandatory course groups, etc.
     */
    void deleteTransitionalProvisionFile();

    /**
     * Returns the transitional provision containing the mandatory and additional
     * mandatory courses.
     *
     * @return the transitional provision containing the mandatory and
     * additional mandatory courses.
     */
    TransitionalProvision getTransitionalProvision();
}
