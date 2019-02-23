package com.example.courseanalyzer.analyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer
 * @Class: CourseAnalyzer
 * @Author: Jan
 * @Date: 29.01.2019
 */

import com.example.courseanalyzer.analyzer.exception.ReadFileException;
import com.example.courseanalyzer.analyzer.exception.WrongFormatException;
import com.example.courseanalyzer.analyzer.model.CourseGroup;
import com.example.courseanalyzer.analyzer.transitionalprovisionanalyzer.TransitionalProvisionAnalyzer;
import com.example.courseanalyzer.analyzer.finishedcoursesanalyzer.FinishedCoursesAnalyzer;
import com.example.courseanalyzer.analyzer.model.Course;
import com.example.courseanalyzer.analyzer.model.CourseReport;
import com.example.courseanalyzer.analyzer.model.TransitionalProvision;
import com.example.courseanalyzer.analyzer.studyplananalyzer.StudyPlanAnalyzer;
import com.example.courseanalyzer.analyzer.studyplananalyzer.model.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Is responsible to pass the information from the front end to the
 * corresponding analyzers. The retrieved information is used for the analysis
 * to create a {@link CourseReport} with {@link #compareCourses()}.
 */
@Component
public class CourseAnalyzer {

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

    /**
     * @see StudyPlanAnalyzer#analyzeStudyPlan(MultipartFile)
     *
     * @param multiPartFile contains the list of mandatory courses, modules and
     *                transferable skills.
     * @throws IllegalArgumentException is thrown, when one necessary parameters
     *                                  is {@code null}.
     * @throws WrongFormatException is thrown, when the format of table of
     *                              content, text of mandatory courses, text of
     *                              modules or text of the transferable skills
     *                              is wrong.
     */
    public void analyzeStudyPlan(MultipartFile multiPartFile) {
        studyPlanAnalyzer.analyzeStudyPlan(multiPartFile);
    }

    /**
     * @see TransitionalProvisionAnalyzer#analyzeTransitionalProvision(MultipartFile)
     *
     * @param multipartFile contains the study plan.
     * @throws ReadFileException    is thrown, when an IO problem occurs while
     *                              reading the passed file.
     * @throws WrongFormatException is thrown, when the text on a page is empty
     *                              or the format creates an empty
     *                              {@link TransitionalProvision}
     */
    public void readTransitionalProvision(MultipartFile multipartFile) {
        transitionalProvisionAnalyzer.analyzeTransitionalProvision(multipartFile);
    }

    /**
     * @see FinishedCoursesAnalyzer#analyzeFinishedCourses(MultipartFile)
     *
     * @param multipartFile contains the finished courses
     */
    public void analyzeFinishedCourses(MultipartFile multipartFile) {
        certificateAnalyzer.analyzeFinishedCourses(multipartFile);
    }

    /**
     * Returns a report of the finished mandatory courses and the missing
     * mandatory courses.
     *
     * @return a report of the finished mandatory courses.
     */
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
