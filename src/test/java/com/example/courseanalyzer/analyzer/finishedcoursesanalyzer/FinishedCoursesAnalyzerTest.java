package com.example.courseanalyzer.analyzer.finishedcoursesanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.finishedcoursesanalyzer
 * @Class: FinishedCoursesAnalyzerTest
 * @Author: Jan
 * @Date: 15.02.2019
 */

import com.example.courseanalyzer.CourseAnalyzerApplication;
import com.example.courseanalyzer.analyzer.exception.NoModelsExtractedException;
import com.example.courseanalyzer.analyzer.exception.WrongFormatException;
import com.example.courseanalyzer.analyzer.model.Course;
import com.example.courseanalyzer.analyzer.model.CourseType;
import org.apache.commons.collections4.SetUtils;
import org.apache.poi.util.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CourseAnalyzerApplication.class)
public class FinishedCoursesAnalyzerTest {

    @Autowired
    private FinishedCoursesAnalyzer finishedCoursesAnalyzer;

    private MockMultipartHttpServletRequest mockRequest;

    @Before
    public void setUp() {
        mockRequest = new MockMultipartHttpServletRequest();
    }

    @Test
    public void analyzeCertificateListShouldReturnCertificates() throws IOException {
        MultipartFile mockedFile = createMultiPartFile("correctCertificate.xlsx");
        Set<Course> expectedCourses = new HashSet<>(Arrays.asList(
                new Course(CourseType.VU, "Course 1", 1),
                new Course(CourseType.SE, "Course 2", 2),
                new Course(CourseType.UE, "Course 3", 3),
                new Course(CourseType.VU, "Course 4", 4),
                new Course(CourseType.PR, "Course 5", 5),
                new Course(CourseType.VO, "Course 6", 6)
        ));

        mockRequest.addFile(mockedFile);

        finishedCoursesAnalyzer.analyzeFinishedCourses(mockRequest);

        Set<Course> resultedCourses = finishedCoursesAnalyzer.getFinishedCourses();

        assertThat(resultedCourses.size(), is(6));
        assertThat(SetUtils.isEqualSet(expectedCourses, resultedCourses), is(true));
    }

    private MultipartFile createMultiPartFile(String filePath) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filePath).getFile());
        byte[] content = IOUtils.toByteArray(new FileInputStream(file));
        return new MockMultipartFile(file.getName(), content);
    }

    @Test(expected = NoModelsExtractedException.class)
    public void analyzeEmptyCertificateListShouldThrowException() throws IOException {
        MultipartFile mockedFile = createMultiPartFile("empty.xlsx");

        mockRequest.addFile(mockedFile);

        finishedCoursesAnalyzer.analyzeFinishedCourses(mockRequest);
    }

    @Test(expected = WrongFormatException.class)
    public void analyzeWrongFormatCertificateListShouldThrowException() throws IOException {
        MultipartFile mockedFile = createMultiPartFile("wrongFormat.xlsx");

        mockRequest.addFile(mockedFile);

        finishedCoursesAnalyzer.analyzeFinishedCourses(mockRequest);
    }
}
