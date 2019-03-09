package com.example.courseanalyzer.analyzer.studyplananalyzer.transferableskillsanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.studyplananalyzer.transferableskillsanalyzer
 * @Class: TransferableSkillsAnalyzerTest
 * @Author: Jan
 * @Date: 17.02.2019
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
 * Tests the functionality of {@link TransferableSkillsAnalyzerTest} such that the expected
 * behaviour is correctly implemented.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CourseAnalyzerApplication.class)
public class TransferableSkillsAnalyzerTest {

    @Autowired
    public TransferableSkillsAnalyzer transferableSkillsAnalyzer;

    @Test
    public void analyzeCorrectTransferableSkillsTextShouldReturnCorrectSet() throws IOException {
        final int sizeOfCourses = 17;
        String transferableSkillsText = FileUtil.getWholeStringFromFile("correctTransferableSkills.txt");

        transferableSkillsAnalyzer.analyzeTransferableSkills(transferableSkillsText);

        Set<Course> resultedCourses = transferableSkillsAnalyzer.getTransferableSkills();
        Set<Course> expectedCourses = new HashSet<>(Arrays.asList(
                IntStream.range(1, sizeOfCourses + 1)
                        .mapToObj(ModelUtil::createCourseFromInteger)
                        .toArray(Course[]::new)));

        assertThat(resultedCourses.size(), is(sizeOfCourses));
        assertThat(SetUtils.isEqualSet(expectedCourses, resultedCourses), is(true));
    }

    @Test
    public void analyzeWrongTransferableSkillsTextShouldThrowException() throws IOException {
        String transferableSkillsText = FileUtil.getWholeStringFromFile("wrongTransferableSkills.txt");

        transferableSkillsAnalyzer.analyzeTransferableSkills(transferableSkillsText);
    }

    @Test(expected = NoModelsExtractedException.class)
    public void analyzeEmptyTransferableSkillsTextShouldThrowException() throws IOException {
        String transferableSkillsText = FileUtil.getWholeStringFromFile("emptyTransferableSkills.txt");

        transferableSkillsAnalyzer.analyzeTransferableSkills(transferableSkillsText);
    }

    @Test(expected = WrongFormatException.class)
    public void analyzeEmptyTextShouldThrowException() {
        transferableSkillsAnalyzer.analyzeTransferableSkills("");
    }
}
