package com.example.courseanalyzer.service;
/**
 * @Package: com.example.courseanalyzer.service
 * @Class: CourseAnalyzerServiceTest
 * @Author: Jan
 * @Date: 03.03.2019
 */

import com.example.courseanalyzer.analyzer.finishedcoursesanalyzer.FinishedCoursesAnalyzer;
import com.example.courseanalyzer.analyzer.model.*;
import com.example.courseanalyzer.analyzer.studyplananalyzer.model.Module;
import com.example.courseanalyzer.analyzer.studyplananalyzer.StudyPlanAnalyzer;
import com.example.courseanalyzer.analyzer.transitionalprovisionanalyzer.TransitionalProvisionAnalyzer;
import org.apache.commons.collections4.SetUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 *
 */
public class CourseAnalyzerServiceTest {

    @Spy
    private StudyPlanAnalyzer studyPlanAnalyzer;

    @Spy
    private FinishedCoursesAnalyzer finishedCoursesAnalyzer;

    @Spy
    private TransitionalProvisionAnalyzer transitionalProvisionAnalyzer;

    @InjectMocks
    private CourseAnalyzer courseAnalyzer;

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAnalyzeCoursesShouldCreateCorrectReport() {

        Mockito.when(studyPlanAnalyzer.getMandatoryCourses())
                .thenReturn(
                        new HashSet<>(Arrays.asList(
                                new Course(CourseType.VO, "Course 1", 1),
                                new Course(CourseType.VO, "Course 2", 2),
                                new Course(CourseType.VO, "Course 3", 3),
                                new Course(CourseType.VO, "Course 4", 4)
                        )));

        Mockito.when(studyPlanAnalyzer.getTransferableSkills())
                .thenReturn(
                        new HashSet<>(Arrays.asList(
                                new Course(CourseType.VO, "Course 5", 5),
                                new Course(CourseType.VO, "Course 6", 6),
                                new Course(CourseType.VO, "Course 7", 7),
                                new Course(CourseType.VO, "Course 8", 8)
                        )));

        Mockito.when(studyPlanAnalyzer.getModules())
                .thenReturn(getModulesForCorrectReport());

        Mockito.when(transitionalProvisionAnalyzer.getTransitionalProvision())
                .thenReturn(getTransitionalProvisionForCorrectReport());

        Mockito.when(finishedCoursesAnalyzer.getFinishedCourses()).thenReturn(
                new HashSet<>(Arrays.asList(
                        new Course(CourseType.VO, "Course 1", 1),
                        new Course(CourseType.VO, "Course 2", 2),
                        new Course(CourseType.VO, "Course 6", 6),
                        new Course(CourseType.VO, "Course 10", 10),
                        new Course(CourseType.VO, "Course 12", 12),
                        new Course(CourseType.VO, "Course 13", 13),
                        new Course(CourseType.VO, "Course 14", 14),
                        new Course(CourseType.VO, "Course 15", 15)
                )));

        Set<Course> expectedResultedRemainingMandatoryCourses =
                new HashSet<>(Arrays.asList(
                        new Course(CourseType.VO, "Course 3", 3),
                        new Course(CourseType.VO, "Course 4", 4)
                ));

        Set<Course> expectedResultedRemainingUnassginedCourses =
                new HashSet<>(Arrays.asList(
                        new Course(CourseType.VO, "Course 14", 14),
                        new Course(CourseType.VO, "Course 15", 15)
                ));

        courseAnalyzer.analyzeCourses();

        CourseReport resultedCourseReport = courseAnalyzer.getCourseReport();

        assertThat(SetUtils.isEqualSet(
                expectedResultedRemainingMandatoryCourses,
                resultedCourseReport.getRemainingMandatoryCourses()),
                is(true));
        assertThat(SetUtils.isEqualSet(
                expectedResultedRemainingUnassginedCourses,
                resultedCourseReport.getRemainingUnassignedFinishedCourses()),
                is(true));
        assertThat(resultedCourseReport.getMandatoryCoursesEcts(), is(15.0f));
        assertThat(resultedCourseReport.getAdditionalMandatoryCoursesEcts(), is(13.0f));
        assertThat(resultedCourseReport.getTransferableSkillsEcts(), is(6.0f));
        assertThat(resultedCourseReport.getOptionalModuleEcts(), is(10.0f));
    }

    private Set<Module> getModulesForCorrectReport() {
        Set<Module> modules = new HashSet<>();
        Module module1 = new Module();
        Module module2 = new Module();

        module1.setCourses(new HashSet<>(Arrays.asList(
                new Course(CourseType.VO, "Course 9", 9),
                new Course(CourseType.VO, "Course 10", 10)
        )));
        module2.setCourses(new HashSet<>(Arrays.asList(
                new Course(CourseType.VO, "Course 10", 10),
                new Course(CourseType.VO, "Course 11", 11)
        )));

        module2.setMandatory(false);

        modules.add(module1);
        modules.add(module2);

        return modules;
    }

    private TransitionalProvision getTransitionalProvisionForCorrectReport() {
        TransitionalProvision transitionalProvision = new TransitionalProvision();

        CourseGroup mandatoryCourseGroup = new CourseGroup();
        CourseGroup additionalMandatoryCourseGroup = new CourseGroup();

        mandatoryCourseGroup.setCourses(new HashSet(Arrays.asList(
                new Course(CourseType.VO, "Course 12", 12)
        )));
        additionalMandatoryCourseGroup.setCourses(new HashSet(Arrays.asList(
                new Course(CourseType.VO, "Course 13", 13)
        )));

        transitionalProvision.setMandatoryCourseGroups(new HashSet<>(Arrays.asList(
                mandatoryCourseGroup
        )));
        transitionalProvision.setAdditionalMandatoryCourseGroups(new HashSet(Arrays.asList(
                additionalMandatoryCourseGroup
        )));
        return transitionalProvision;
    }

    @Test
    public void testAnalyzeEmptyTransitionalProvisionShouldCreateCorrectReport() {
        Mockito.when(studyPlanAnalyzer.getMandatoryCourses())
                .thenReturn(
                        new HashSet<>(Arrays.asList(
                                new Course(CourseType.VO, "Course 1", 1),
                                new Course(CourseType.VO, "Course 2", 2),
                                new Course(CourseType.VO, "Course 3", 3),
                                new Course(CourseType.VO, "Course 4", 4)
                        )));

        Mockito.when(studyPlanAnalyzer.getTransferableSkills())
                .thenReturn(
                        new HashSet<>(Arrays.asList(
                                new Course(CourseType.VO, "Course 5", 5),
                                new Course(CourseType.VO, "Course 6", 6),
                                new Course(CourseType.VO, "Course 7", 7),
                                new Course(CourseType.VO, "Course 8", 8)
                        )));

        Mockito.when(studyPlanAnalyzer.getModules())
                .thenReturn(getModulesForCorrectReport());

        Mockito.when(finishedCoursesAnalyzer.getFinishedCourses()).thenReturn(
                new HashSet<>(Arrays.asList(
                        new Course(CourseType.VO, "Course 1", 1),
                        new Course(CourseType.VO, "Course 2", 2),
                        new Course(CourseType.VO, "Course 6", 6),
                        new Course(CourseType.VO, "Course 10", 10),
                        new Course(CourseType.VO, "Course 12", 12),
                        new Course(CourseType.VO, "Course 13", 13)
                )));

        Set<Course> expectedResultedRemainingMandatoryCourses =
                new HashSet<>(Arrays.asList(
                        new Course(CourseType.VO, "Course 3", 3),
                        new Course(CourseType.VO, "Course 4", 4)
                ));

        Set<Course> expectedResultedRemainingUnassginedCourses =
                new HashSet<>(Arrays.asList(
                        new Course(CourseType.VO, "Course 12", 12),
                        new Course(CourseType.VO, "Course 13", 13)
                ));

        courseAnalyzer.analyzeCourses();

        CourseReport resultedCourseReport = courseAnalyzer.getCourseReport();

        assertThat(SetUtils.isEqualSet(
                expectedResultedRemainingMandatoryCourses,
                resultedCourseReport.getRemainingMandatoryCourses()),
                is(true));
        assertThat(SetUtils.isEqualSet(
                expectedResultedRemainingUnassginedCourses,
                resultedCourseReport.getRemainingUnassignedFinishedCourses()),
                is(true));
        assertThat(resultedCourseReport.getMandatoryCoursesEcts(), is(3.0f));
        assertThat(resultedCourseReport.getAdditionalMandatoryCoursesEcts(), is(0.0f));
        assertThat(resultedCourseReport.getTransferableSkillsEcts(), is(6.0f));
        assertThat(resultedCourseReport.getOptionalModuleEcts(), is(10.0f));
    }

    @Test
    public void testAnalyzeEmptyCoursesShouldCreateCorrectEmptyReport() {
        Mockito.when(studyPlanAnalyzer.getMandatoryCourses())
                .thenReturn(new HashSet<>(Arrays.asList()));

        Mockito.when(studyPlanAnalyzer.getTransferableSkills())
                .thenReturn( new HashSet<>(Arrays.asList()));

        Mockito.when(studyPlanAnalyzer.getModules())
                .thenReturn(getModulesForCorrectReport());

        Mockito.when(transitionalProvisionAnalyzer.getTransitionalProvision())
                .thenReturn(getTransitionalProvisionForCorrectReport());

        Mockito.when(finishedCoursesAnalyzer.getFinishedCourses()).thenReturn(
                new HashSet<>(Arrays.asList()));

        courseAnalyzer.analyzeCourses();

        CourseReport resultedCourseReport = courseAnalyzer.getCourseReport();
        Set<Course> remainingMandatoryCourses = resultedCourseReport.getRemainingMandatoryCourses();
        Set<Course> remainingUnassignedCourses = resultedCourseReport.getRemainingUnassignedFinishedCourses();

        assertThat(remainingMandatoryCourses.isEmpty(), is(true));
        assertThat(remainingUnassignedCourses.isEmpty(), is(true));
        assertThat(resultedCourseReport.getMandatoryCoursesEcts(), is(0.0f));
        assertThat(resultedCourseReport.getAdditionalMandatoryCoursesEcts(), is(0.0f));
        assertThat(resultedCourseReport.getTransferableSkillsEcts(), is(0.0f));
        assertThat(resultedCourseReport.getOptionalModuleEcts(), is(0.0f));
    }


}
