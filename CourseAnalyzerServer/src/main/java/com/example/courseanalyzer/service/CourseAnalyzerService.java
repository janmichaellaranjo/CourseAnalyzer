package com.example.courseanalyzer.service;
/**
 * @Package: com.example.courseanalyzer.service
 * @Class: CourseAnalyzerService
 * @Author: Jan
 * @Date: 25.01.2019
 */

import com.example.courseanalyzer.analyzer.exception.ReadFileException;
import com.example.courseanalyzer.analyzer.exception.WrongFormatException;
import com.example.courseanalyzer.analyzer.model.CourseReport;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;

/**
 *
 */
public interface CourseAnalyzerService {

    /**
     * Reads the file and extracts the mandatory courses, modules and
     * transferable skills from the request which contains the uploaded file.
     *
     * @param multipartFile contains the courses
     * @throws IllegalArgumentException is thrown, when one necessary parameters
     *                                  is {@code null}.
     * @throws WrongFormatException is thrown, when the format of table of
     *                              content, text of mandatory courses, text of
     *                              modules or text of the transferable skills
     *                              is wrong.
     */
    void readStudyPlan(MultipartFile multipartFile);

    /**
     * Reads the file and extracts the additional mandatory courses from the
     * request which contains the uploaded file.
     *
     * @param multipartFile contains the courses
     * @throws ReadFileException    is thrown, when an IO problem occurs while
     *                              reading the passed file.
     * @throws WrongFormatException is thrown, when the text on a page
     *                              is empty or the format creates an empty
     *                              {@link com.example.courseanalyzer.analyzer.model.TransitionalProvision}
     */
    void readTransitionalProvision(MultipartFile multipartFile);

    /**
     * Reads the file and extracts the certificates from the request which
     * contains the uploaded file.
     *
     * @param multipartFile contains the certificates
     * @throws ReadFileException    is thrown, when an IO problem occurs while
     *                              reading the passed file.
     * @throws WrongFormatException is thrown, when the format is empty or the
     *                               text creates an empty list of certificates.
     */
    void readFinishedCourseList(MultipartFile multipartFile);

    /**
     * Deletes the selected file with the file name {@code fileName} and also
     * the corresponding determined informations such as mandatory courses, etc.
     *
     * @param fileName the name of the file which should be deleted.
     */
    void deleteSelectedFile(String fileName);

    /**
     * Returns a detailed report about all finished courses by comparing the
     * resulted finished courses with the mandatory courses and optionally with
     * the transitional provision.
     *
     * @return a detailed report about all finished courses.
     */
    CourseReport compareCourses();
}
