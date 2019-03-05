package com.example.courseanalyzer.analyzer.studyplananalyzer.model;
/**
 * @Package: com.example.courseanalyzer.analyzer.model
 * @Class: TableOfContent
 * @Author: Jan
 * @Date: 04.02.2019
 */

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the table of content of the study plan.
 */
public class TableOfContent {
    private static final String CHAPTER_FORMAT = "[\\w]+\\. .*”?[\\w|äöüÄÖÜß| |\\-|(|)]+“? \\d+";
    private List<Chapter> chapters;

    public TableOfContent() {
        this.chapters = new ArrayList<>();
    }

    /**
     * Extracts the chapter information from the line and adds this chapter to
     * to list of chapters.
     *
     * @param line contains the chapter line
     */
    public void addChapterFromLine(String line) {
        if (isValidChapterLine(line)) {
            Chapter chapter = createChapterFromLine(line);
            chapters.add(chapter);
        }
    }

    private boolean isValidChapterLine(String line) {
        Pattern pattern = Pattern.compile(CHAPTER_FORMAT);
        Matcher matcher = pattern.matcher(line);

        return matcher.matches();
    }

    private Chapter createChapterFromLine(String line) {
        Chapter chapter = new Chapter();
        String processedLine = line.substring(line.indexOf('.') + 1, line.length())
                                    .trim();
        String chapterTitle = processedLine.replaceAll("\\d", "")
                                            .trim();
        int number = Integer.parseInt(processedLine.replaceAll("\\D", ""));

        chapter.setTitle(chapterTitle);
        chapter.setPageStart(number);

        return chapter;
    }

    /**
     * Determines every last page of each chapter of the table of content.
     *
     * <p>The last chapter's page end is default {@code -1}</p>
     */
    public void determinePageEndsOfAllChapters() {
        for (int i = 0; i < chapters.size() - 1; i++) {
            Chapter currentChapter = chapters.get(i);
            Chapter nextChapter = chapters.get(i + 1);
            int pageDiff = nextChapter.getPageStart() - currentChapter.getPageStart() - 1;
            int correctPageDiff = Math.max(0, pageDiff);
            int pageEnd = currentChapter.getPageStart() + correctPageDiff;
            currentChapter.setPageEnd(pageEnd);
        }
    }

    /**
     *
     * Returns true, if the table of content contains any chapter.
     *
     * @return true, if the table of content contains any chapter.
     */
    public boolean containsChapter() {
        return !chapters.isEmpty();
    }

    /**
     * Returns the chapter with the corresponding title {@code title}.
     *
     * @param chapterTitle the title of the chapter which is searched for.
     * @return the chapter with the corresponding title {@code title}.
     */
    public Chapter getChapter(String chapterTitle) {
        return chapters
                .stream()
                .filter(chapter -> chapter.getTitle().equals(chapterTitle))
                .findAny()
                .orElse(null);
    }

    @Override
    public String toString() {
        String output = "Table of content: \n";
        if (chapters == null) {
            return output + "empty";
        }

        for(Chapter chapter : chapters) {
            output += chapter + "\n";
        }

        return output;
    }
}
