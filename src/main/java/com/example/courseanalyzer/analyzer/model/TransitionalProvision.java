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
     * Returns true, if this transitional provision contains the examined module
     * {@code examinedModule}.
     *
     * @param examinedModule the examined module.
     * @return true, if this transitional provision contains the examined module
     *         {@code examinedModule}.
     */
    public boolean containsCourse(Course examinedModule) {
        for (ExamModule examModule : examModules) {
            if (examModule.containsAdditionalMandatoryCourse(examinedModule)) {
                return true;
            }
        }
        return false;
    }
}
