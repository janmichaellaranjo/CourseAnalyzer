package com.example.courseanalyzer.analyzer.studyplananalyzer.model;
/**
 * @Package: com.example.courseanalyzer.analyzer.model
 * @Class: Chapter
 * @Author: Jan
 * @Date: 04.02.2019
 */

/**
 * Represents a chapter in the study plan. This consists of the title and the
 * start page pageStart and the end page pageStart which can be determined afterwards.
 *
 * <p>The last chapter page end is default {@code -1}.</p>
 */
public class Chapter {
    private String title;
    private int pageStart;
    private int pageEnd;

    public Chapter() {
        this.pageEnd = -1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public int getPageEnd() {
        return pageEnd;
    }

    public void setPageEnd(int pageEnd) {
        this.pageEnd = pageEnd;
    }

    @Override
    public String toString() {
        return "[title:" +
                title +
                ", pageStart: " +
                pageStart +
                ", pageEnd: " +
                pageEnd +
                "]";
    }
}
