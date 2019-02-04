package com.example.courseanalyzer.analyzer.model;
/**
 * @Package: com.example.courseanalyzer.analyzer.model
 * @Class: TransitionalProvision
 * @Author: Jan
 * @Date: 02.02.2019
 */

import java.util.Set;

/**
 *
 * Represents the transitional provision for an upcoming semester. This consists
 * of the mandatory courses and additional mandatory courses. These lists all
 * courses that are no longer available and also all actual courses.
 *
 */
public class TransitionalProvision {

    private Set<ExamModule> examModules;

    public Set<ExamModule> getExamModules() {
        return examModules;
    }

    public void setExamModules(Set<ExamModule> examModules) {
        this.examModules = examModules;
    }

    /**
     *
     * Returns true, if the mandatory courses of transitional provision
     * contains the examined module{@code examinedModule}.
     *
     * @param examinedModule the examined module.
     * @return true, if the mandatory courses of transitional provision
     *         contains the examined module{@code examinedModule}.
     */
    public boolean containsMandatoryCourse(Course examinedModule) {
        for (ExamModule examModule : examModules) {
            if (examModule.containsMandatoryCourse(examinedModule)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * Returns true, if the additional mandatory courses of transitional
     * provision contains the examined module{@code examinedModule}.
     *
     * @param examinedModule the examined module.
     * @return if the additional mandatory courses of transitional provision
     *         contains the examined module{@code examinedModule}.
     */
    public boolean containsAdditionalMandatoryCourse(Course examinedModule) {
        for (ExamModule examModule : examModules) {
            if (examModule.containsAdditionalMandatoryCourse(examinedModule)) {
                return true;
            }
        }
        return false;
    }
}
