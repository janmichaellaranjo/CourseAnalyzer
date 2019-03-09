package com.example.courseanalyzer.analyzer.finishedcoursesanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.finishedcoursesanalyzer
 * @Class: FinishedCoursesAnalyzerTest
 * @Author: Jan
 * @Date: 15.02.2019
 */

import com.example.courseanalyzer.CourseAnalyzerApplication;
import com.example.courseanalyzer.util.ModelUtil;
import com.example.courseanalyzer.util.FileUtil;
import com.example.courseanalyzer.analyzer.exception.NoModelsExtractedException;
import com.example.courseanalyzer.analyzer.exception.WrongFormatException;
import com.example.courseanalyzer.analyzer.model.Course;
import org.apache.commons.collections4.SetUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests the functionality of {@link FinishedCoursesAnalyzer} such that the
 * expected behaviour is correctly implemented.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CourseAnalyzerApplication.class)
public class FinishedCoursesAnalyzerTest {

    @Autowired
    private FinishedCoursesAnalyzer finishedCoursesAnalyzer;

    @Test
    public void analyzeCertificateListShouldReturnCertificates() throws IOException {
        final int sizeOfCourses = 10;
        MultipartFile mockedFile = FileUtil.createMultiPartFile("correctCertificate.xlsx");
        Set<Course> expectedCourses = new HashSet<>(Arrays.asList(
                IntStream.range(1, sizeOfCourses + 1)
                .mapToObj(ModelUtil::createCourseFromInteger)
                .toArray(Course[]::new)));

        finishedCoursesAnalyzer.analyzeFinishedCourses(mockedFile);

        Set<Course> resultedCourses = finishedCoursesAnalyzer.getFinishedCourses();

        assertThat(resultedCourses.size(), is(sizeOfCourses));
        assertThat(SetUtils.isEqualSet(expectedCourses, resultedCourses), is(true));
    }

    @Test(expected = NoModelsExtractedException.class)
    public void analyzeEmptyCertificateListShouldThrowException() throws IOException {
        MultipartFile mockedFile = FileUtil.createMultiPartFile("empty.xlsx");

        finishedCoursesAnalyzer.analyzeFinishedCourses(mockedFile);
    }

    @Test(expected = WrongFormatException.class)
    public void analyzeWrongFormatCertificateListShouldThrowException() throws IOException {
        MultipartFile mockedFile = FileUtil.createMultiPartFile("wrongFormat.xlsx");

        finishedCoursesAnalyzer.analyzeFinishedCourses(mockedFile);
    }
}
