package com.example.courseanalyzer.analyzer.transitionalprovision;
/**
 * @Package: com.example.courseanalyzer.analyzer.transitionalprovision
 * @Class: TransitionalProvisionAnalyzerTest
 * @Author: Jan
 * @Date: 17.02.2019
 */

import com.example.courseanalyzer.CourseAnalyzerApplication;
import com.example.courseanalyzer.Util.ModelUtil;
import com.example.courseanalyzer.Util.FileUtil;
import com.example.courseanalyzer.analyzer.exception.NoModelsExtractedException;
import com.example.courseanalyzer.analyzer.exception.WrongFormatException;
import com.example.courseanalyzer.analyzer.model.CourseGroup;
import com.example.courseanalyzer.analyzer.model.TransitionalProvision;
import com.example.courseanalyzer.analyzer.transitionalprovisionanalyzer.TransitionalProvisionAnalyzer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests the functionality of {@link TransitionalProvisionAnalyzer} such that the
 * expected behaviour is correctly implemented.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CourseAnalyzerApplication.class)
public class TransitionalProvisionAnalyzerTest {

    @Autowired
    private TransitionalProvisionAnalyzer transitionalProvisionAnalyzer;

    private MockMultipartHttpServletRequest mockRequest;

    @Before
    public void setUp() {
        mockRequest = new MockMultipartHttpServletRequest();
    }

    @Test
    public void analyzeCorrectMandatoryCoursesTextShouldReturnCorrectSet() throws IOException {
        MultipartFile mockedFile = FileUtil.createMultiPartFile(
                "correctTransitionalProvision.pdf");

        mockRequest.addFile(mockedFile);

        transitionalProvisionAnalyzer.analyzeTransitionalProvision(mockRequest);

        TransitionalProvision resultedTransitionalProvision = transitionalProvisionAnalyzer.getTransitionalProvision();
        TransitionalProvision expectedTransitionalProvision = ModelUtil.getExpectedTransitionalProvision();
        Set<CourseGroup> resultedMandatoryCourseGroup = resultedTransitionalProvision.getMandatoryCourseGroups();
        Set<CourseGroup> resultedAdditonalMandatoryCourseGroup = resultedTransitionalProvision.getAdditionalMandatoryCourseGroups();

        assertThat(resultedMandatoryCourseGroup.size(), is(4));
        assertThat(resultedAdditonalMandatoryCourseGroup.size(), is(4));
        assertThat(resultedTransitionalProvision.equals(expectedTransitionalProvision), is(true));
    }

    @Test(expected = NoModelsExtractedException.class)
    public void analyzeEmptyTransferableSkillsTextShouldThrowException() throws IOException {
        MultipartFile mockedFile = FileUtil.createMultiPartFile(
                "emptyTransitionalProvision.pdf");

        mockRequest.addFile(mockedFile);

        transitionalProvisionAnalyzer.analyzeTransitionalProvision(mockRequest);
    }

    @Test(expected = WrongFormatException.class)
    public void analyzeEmptyTextShouldThrowException() throws IOException {
        MultipartFile mockedFile = FileUtil.createMultiPartFile("empty.pdf");

        mockRequest.addFile(mockedFile);

        transitionalProvisionAnalyzer.analyzeTransitionalProvision(mockRequest);
    }
}
