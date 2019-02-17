package com.example.courseanalyzer.analyzer.studyplananalyzer.modulesanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.studyplananalyzer.modulesanalyzer
 * @Class: ModulesAnalyzerTest
 * @Author: Jan
 * @Date: 16.02.2019
 */

import com.example.courseanalyzer.CourseAnalyzerApplication;
import com.example.courseanalyzer.Util.FileUtil;
import com.example.courseanalyzer.analyzer.exception.NoModelsExtractedException;
import com.example.courseanalyzer.analyzer.exception.WrongFormatException;
import com.example.courseanalyzer.analyzer.model.Course;
import com.example.courseanalyzer.analyzer.model.CourseType;
import com.example.courseanalyzer.analyzer.studyplananalyzer.model.Module;
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
 * Tests the functionality of {@link ModulesAnalyzer} such that the expected
 * behaviour is correctly implemented.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CourseAnalyzerApplication.class)
public class ModulesAnalyzerTest {

    @Autowired
    private ModulesAnalyzer modulesAnalyzer;

    @Test
    public void analyzeCorrectMandatoryCoursesTextShouldReturnCorrectSet() throws IOException {
        final int sizeOfModules = 7;
        String moduleText = FileUtil.getWholeStringFromFile("correctModule.txt");

        Set<Module> resultedModules = modulesAnalyzer.analyzeModule(moduleText);
        Set<Module> expectedModules = new HashSet(Arrays.asList(
                IntStream
                .range(1, 8)
                .mapToObj(ModulesAnalyzerTest::createModulesFromInteger)
                .toArray()
        ));

        assertThat(resultedModules.size(), is(7));
        assertThat(SetUtils.isEqualSet(resultedModules, expectedModules), is(true));
    }

    private static Module createModulesFromInteger(int i) {
        Module module = new Module();
        Course course1 = new Course();
        Course course2 = new Course();
        int num2 = 2 * i;
        int num1 = num2 - 1;
        boolean isMandatory = i % 2 != 0;
        course1.setCourseType(CourseType.VO);
        course1.setCourseName("Course " + num1);
        course1.setEcts(num1);

        course2.setCourseType(CourseType.VO);
        course2.setCourseName("Course " + num2);
        course2.setEcts(num2);

        Set<Course> courses = new HashSet<>(Arrays.asList(course1, course2));

        module.setName("Module " + i);
        module.setCourses(courses);
        module.setMandatory(isMandatory);

        return module;
    }

    @Test(expected = NoModelsExtractedException.class)
    public void analyzeEmptyMandatoryCoursesTextShouldThrowException() throws IOException {
        String mandatoryCoursesText = FileUtil.getWholeStringFromFile("emptyModule.txt");

        modulesAnalyzer.analyzeModule(mandatoryCoursesText);
    }

    @Test(expected = WrongFormatException.class)
    public void analyzeEmptyTextShouldThrowException() {
        modulesAnalyzer.analyzeModule("");
    }
}
