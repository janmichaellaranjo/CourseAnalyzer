package com.example.courseanalyzer.analyzer.certificateanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.certificateanalyzer
 * @Class: CertificateAnalyzer
 * @Author: Jan
 * @Date: 30.01.2019
 */

import com.example.courseanalyzer.analyzer.model.Course;

import javax.servlet.ServletRequest;
import java.util.Set;

/**
 *
 * Extracts the informations from the downloaded file which contains the list of
 * certificates. These certificates are completed courses which also contains
 * the grade of the course.
 *
 * <p>This file can be downloaded on
 *    <a href="https://tiss.tuwien.ac.at/student/self_service">TISS-Student Self Service</a></p>.
 *
 * <p>These interface is used to conveniently change the implementation because
 *    the format of the file may change and a new implementation is
 *    necessary.</p>
 *
 */
public interface CertificateAnalyzer {

    /**
     *
     * Extracts the certificate informations from the download request.
     *
     * <p>These list of certificates can be empty, if the file of the request
     *    does not meet the format of the file to extract the information.</p>
     *
     * @param request the download request
     * @return
     */
    Set<Course> analyzeCertificateList(ServletRequest request);
}
