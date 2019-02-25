package com.example.courseanalyzer.service;
/**
 * @Package: com.example.courseanalyzer.service
 * @Class: SimpleCourseCheckerService
 * @Author: Jan
 * @Date: 25.01.2019
 */

import com.example.courseanalyzer.analyzer.finishedcoursesanalyzer.FinishedCoursesAnalyzer;
import com.example.courseanalyzer.analyzer.model.Course;
import com.example.courseanalyzer.analyzer.model.CourseGroup;
import com.example.courseanalyzer.analyzer.model.CourseReport;
import com.example.courseanalyzer.analyzer.model.TransitionalProvision;
import com.example.courseanalyzer.analyzer.studyplananalyzer.StudyPlanAnalyzer;
import com.example.courseanalyzer.analyzer.studyplananalyzer.model.Module;
import com.example.courseanalyzer.analyzer.transitionalprovisionanalyzer.TransitionalProvisionAnalyzer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.stream.Collectors;


/**
 *
 */
@Service
public class SimpleCourseCheckerService implements CourseAnalyzerService {
    private static final Logger logger = LogManager.getLogger(SimpleCourseCheckerService.class);

    @Autowired
    @Qualifier("SimpleStudyPlanAnalyzer")
    private StudyPlanAnalyzer studyPlanAnalyzer;

    @Autowired
    @Qualifier("SimpleFinishedCoursesAnalyzer")
    private FinishedCoursesAnalyzer certificateAnalyzer;

    @Autowired
    @Qualifier("SimpleTransitionalProvisionAnalyzer")
    private TransitionalProvisionAnalyzer transitionalProvisionAnalyzer;

    private CourseReport courseReport;

    private Set<Course> mandatoryCourses;

    private TransitionalProvision transitionalProvision;

    private Set<Course> finishedCourses;

    private Set<Module> modules;

    private Set<Course> transferableSkills;

    private float sumAchievedMandatoryEcts = 0f;

    private float sumAchievedAdditionalMandatoryEcts = 0f;

    private float sumAchievedOptionalModuleEcts = 0f;

    private float sumAchievedTransferableSkillsEcts = 0f;

    @Override
    public void readStudyPlan(MultipartFile multiPartFile) {
        if (multiPartFile == null) {
            String errorMsg = "The multipartFile for the study plan on the service layer is null";
            logger.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        studyPlanAnalyzer.analyzeStudyPlan(multiPartFile);
    }

    @Override
    public void readTransitionalProvision(MultipartFile multipartFile) {
        if (multipartFile == null) {
            String errorMsg = "The multipartFile for the transitional provision on the service layer is null";
            logger.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        transitionalProvisionAnalyzer.analyzeTransitionalProvision(multipartFile);
    }

    @Override
    public void readFinishedCourseList(MultipartFile multipartFile) {
        if (multipartFile == null) {
            String errorMsg = "The multipartFile for the finished course list on the service layer is null";
            logger.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        certificateAnalyzer.analyzeFinishedCourses(multipartFile);
    }

    @Override
    public void deleteSelectedFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            String errorMsg = "The file name for deleting the selected file must not be null or empty";
            logger.error(errorMsg);

            throw new IllegalArgumentException(errorMsg);
        }

    }

    @Override
    public CourseReport compareCourses() {
        initCourseInformation();
        determineCourseLists();
        calculateEcts();

        return courseReport;
    }

    private void initCourseInformation() {
        this.sumAchievedMandatoryEcts = 0f;
        this.sumAchievedAdditionalMandatoryEcts = 0f;
        this.sumAchievedOptionalModuleEcts = 0f;
        this.sumAchievedTransferableSkillsEcts = 0f;
        this.finishedCourses = certificateAnalyzer.getFinishedCourses();
        this.mandatoryCourses = studyPlanAnalyzer.getMandatoryCourses();
        this.transitionalProvision = transitionalProvisionAnalyzer.getTransitionalProvision();
        this.modules = studyPlanAnalyzer.getModules();
        this.transferableSkills = studyPlanAnalyzer.getTransferableSkills();
        this.courseReport = new CourseReport();
    }

    private void calculateEcts() {
        for (Course finishedCourse : finishedCourses) {
            if (mandatoryCourses.contains(finishedCourse)) {
                sumAchievedMandatoryEcts += finishedCourse.getEcts();
            } else if (transitionalProvision != null && transitionalProvision.containsMandatoryCourse(finishedCourse)) {
                sumAchievedMandatoryEcts += finishedCourse.getEcts();
            } else if (transitionalProvision != null && transitionalProvision.containsAdditionalMandatoryCourse(finishedCourse)) {
                sumAchievedAdditionalMandatoryEcts += finishedCourse.getEcts();
            } else if (isOptionalModulesContainsCourse(finishedCourse)) {
                sumAchievedOptionalModuleEcts += finishedCourse.getEcts();
            } else if (transferableSkills.contains(finishedCourse)) {
                sumAchievedTransferableSkillsEcts += finishedCourse.getEcts();
            }
        }

        courseReport.setMandatoryCoursesEcts(sumAchievedMandatoryEcts);
        courseReport.setAdditionalMandatoryCoursesEcts(sumAchievedAdditionalMandatoryEcts);
        courseReport.setOptionalModuleEcts(sumAchievedOptionalModuleEcts);
        courseReport.setTransferableSkillsEcts(sumAchievedTransferableSkillsEcts);
    }

    private boolean isOptionalModulesContainsCourse(Course examinedCourse) {
        for (Module module : modules) {
            if (module.containsCourse(examinedCourse) && module.isOptional()) {
                return true;
            }
        }
        return false;
    }

    private void determineCourseLists() {
        Set<Course> remainingMandatoryCourses = determineRemainingMandatoryCourses();

        Set<Course> unassignedCourses = finishedCourses
                .stream()
                .filter(finishedCourse -> !mandatoryCourses.contains(finishedCourse))
                .filter(finishedCourse -> transitionalProvision != null ? !transitionalProvision.containsMandatoryCourse(finishedCourse) : true)
                .filter(finishedCourse -> transitionalProvision != null ? !transitionalProvision.containsAdditionalMandatoryCourse(finishedCourse) : true)
                .filter(finishedCourse -> !isOptionalModulesContainsCourse(finishedCourse))
                .filter(finishedCourse -> !transferableSkills.contains(finishedCourse))
                .collect(Collectors.toSet());

        courseReport.setRemainingMandatoryCourses(remainingMandatoryCourses);
        courseReport.setRemainingUnassignedFinishedCourses(unassignedCourses);
    }

    private Set<Course> determineRemainingMandatoryCourses() {

        if (transitionalProvision == null) {
            return mandatoryCourses;
        }

        Set<Course> remainingMandatoryCourses = mandatoryCourses
                .stream()
                .filter(mandatoryCourse -> !finishedCourses.contains(mandatoryCourse))
                .collect(Collectors.toSet());

        for (Course finishedCourse : finishedCourses) {
            CourseGroup additionalMandatoryCourseGroup = transitionalProvision.getAdditionalMandatoryCourseGroupOfCourse(finishedCourse);
            CourseGroup mandatoryCourseGroup = transitionalProvision.getMandatoryCourseGroupOfCourse(finishedCourse);
            if (mandatoryCourseGroup != null) {
                remainingMandatoryCourses = remainingMandatoryCourses
                        .stream()
                        .filter(remainingCourse -> !mandatoryCourseGroup.containsCourse(remainingCourse))
                        .collect(Collectors.toSet());
            } else if(additionalMandatoryCourseGroup != null) {
                remainingMandatoryCourses = remainingMandatoryCourses
                        .stream()
                        .filter(remainingCourse -> !additionalMandatoryCourseGroup.containsCourse(remainingCourse))
                        .collect(Collectors.toSet());
            }
        }

        return remainingMandatoryCourses;
    }
}
