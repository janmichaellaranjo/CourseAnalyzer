package com.example.courseanalyzer.analyzer.finishedcoursesanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.finishedcoursesanalyzer
 * @Class: FinishedCoursesAnalyzer
 * @Author: Jan
 * @Date: 30.01.2019
 */

import com.example.courseanalyzer.analyzer.exception.NoModelsExtractedException;
import com.example.courseanalyzer.analyzer.exception.ReadFileException;
import com.example.courseanalyzer.analyzer.exception.WrongFormatException;
import com.example.courseanalyzer.analyzer.model.Course;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import java.util.Set;

/**
 * Extracts the informations from the downloaded file which contains the list of
 * finished courses. These finished courses are completed courses which also contains
 * the grade of the course.
 *
 * <p>This file can be downloaded on
 *    <a href="https://tiss.tuwien.ac.at/student/self_service">TISS-Student Self
 *    Service</a></p>.
 *
 * <p>These interface is used to conveniently change the implementation because
 *    the format of the file may change and a new implementation is
 *    necessary.</p>
 */
@Service
public interface FinishedCoursesAnalyzer {

    /**
     * Creates the set of finished courses which is extracted from the download
     * request.
     *
     * <p>These list of finished courses can be empty, if the file of the request
     *    does not meet the format of the file to extract the information.</p>
     *
     * @param request the download request
     * @throws ReadFileException    is thrown, when an IO problem occurs while
     *                              reading the passed file.
     * @throws WrongFormatException is thrown, when the format is wrong such
     *                              that no information could properly be
     *                              extracted e.g. a number is expected but the
     *                              information is a string.
     * @throws NoModelsExtractedException is thrown, when no model is extracted
     *                                    from the request. The list of finished
     *                                    courses is empty.
     */
   void analyzeFinishedCourses(ServletRequest request);

    /**
     * Returns the list of finished courses.
     *
     * @return the list of finished courses.
     */
    Set<Course> getFinishedCourses();
}
