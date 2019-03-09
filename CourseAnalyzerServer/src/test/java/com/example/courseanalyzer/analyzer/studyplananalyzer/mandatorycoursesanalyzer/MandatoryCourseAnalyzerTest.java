package com.example.courseanalyzer.analyzer.studyplananalyzer.mandatorycoursesanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.studyplananalyzer.mandatorycoursesanalyzer
 * @Class: MandatoryCourseAnalyzerTest
 * @Author: Jan
 * @Date: 16.02.2019
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

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests the functionality of {@link MandatoryCoursesAnalyzer} such that the
 * expected behaviour is correctly implemented.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CourseAnalyzerApplication.class)
public class MandatoryCourseAnalyzerTest {

    @Autowired
    private MandatoryCoursesAnalyzer mandatoryCoursesAnalyzer;

    @Test
    public void analyzeCorrectMandatoryCoursesTextShouldReturnCorrectSet() throws IOException {
        final int sizeOfCourses = 36;
        String mandatoryCoursesText = FileUtil.getWholeStringFromFile("correctMandatoryCourses.txt");

        mandatoryCoursesAnalyzer.analyzeMandatoryCourses(mandatoryCoursesText);

        Set<Course> resultedCourses = mandatoryCoursesAnalyzer.getMandatoryCourses();
        Set<Course> expectedCourses = new HashSet<>(Arrays.asList(
                IntStream.range(1, sizeOfCourses + 1)
                .mapToObj(ModelUtil::createCourseFromInteger)
                .toArray(Course[]::new)));

        assertThat(resultedCourses.size(), is(sizeOfCourses));
        assertThat(SetUtils.isEqualSet(expectedCourses, resultedCourses), is(true));
    }

    @Test(expected = NoModelsExtractedException.class)
    public void analyzeEmptyMandatoryCoursesTextShouldThrowException() throws IOException {
        String mandatoryCoursesText = FileUtil.getWholeStringFromFile("emptyMandatoryCourses.txt");

        mandatoryCoursesAnalyzer.analyzeMandatoryCourses(mandatoryCoursesText);
    }

    @Test(expected = WrongFormatException.class)
    public void analyzeWrongTypeMandatoryCoursesTextShouldThrowException() throws IOException {
        String mandatoryCoursesText = FileUtil.getWholeStringFromFile("wrongTypeMandatoryCourses.txt");

        mandatoryCoursesAnalyzer.analyzeMandatoryCourses(mandatoryCoursesText);
    }

    @Test(expected = WrongFormatException.class)
    public void analyzeEmptyTextShouldThrowException() {
        mandatoryCoursesAnalyzer.analyzeMandatoryCourses("");
    }
}
