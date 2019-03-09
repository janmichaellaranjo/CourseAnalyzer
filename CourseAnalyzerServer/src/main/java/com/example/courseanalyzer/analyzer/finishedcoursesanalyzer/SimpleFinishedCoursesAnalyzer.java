package com.example.courseanalyzer.analyzer.finishedcoursesanalyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.finishedcoursesanalyzer
 * @Class: SimpleFinishedCoursesAnalyzer
 * @Author: Jan
 * @Date: 30.01.2019
 */

import com.example.courseanalyzer.analyzer.exception.ReadFileException;
import com.example.courseanalyzer.analyzer.exception.WrongFormatException;
import com.example.courseanalyzer.analyzer.model.Course;
import com.example.courseanalyzer.analyzer.model.CourseType;
import com.example.courseanalyzer.util.ValidationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * Analyzes the passed file in the request. This assumes it is an excel-file and
 * contains the list of finished courses. Each row contains the information of
 * the finished course.
 *
 * <p>This analyzer is tailored to the format of generated certificate list
 * file of TISS.</p>
 * <p>
 *     The format is <i>[course title][course typ][course id][ects][grade]....</i>.
 * </p>
 *
 * <p>Every other information is simply ignored.</p>
 */
@Component("SimpleFinishedCoursesAnalyzer")
public class SimpleFinishedCoursesAnalyzer implements FinishedCoursesAnalyzer {

    private static final Logger logger = LogManager.getLogger(SimpleFinishedCoursesAnalyzer.class);

    private static final int SHEET_NUMBER = 0;

    private static final int START_ROW = 3;

    private static final int COURSE_NAME_INDEX = 0;

    private static final int COURSE_TYPE_INDEX = 1;

    private static final int COURSE_ECTS_INDEX = 3;

    private Set<Course> finishedCourses;

    private String fileName;

    @Override
    public void analyzeFinishedCourses(MultipartFile multipartFile) {
        this.finishedCourses = new HashSet<>();
        this.fileName = multipartFile.getName();

        int i = START_ROW;

        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(multipartFile.getInputStream());
            Sheet sheet = workbook.getSheetAt(SHEET_NUMBER);

            Row row = sheet.getRow(i);
            while (!isRowEmpty(row)) {
                Course finishedCourse = getFinishedCourseFromCells(row);

                if (finishedCourse.isInformationComplete()) {
                    finishedCourses.add(finishedCourse);
                } else {
                    String errorMsg = String.format(
                            "The %i.row does not have valid information to extract information.", i);
                    logger.error(errorMsg);
                    throw new WrongFormatException(errorMsg);
                }
                row = sheet.getRow(i++);
            }

        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
            String errorMsg = String.format(
                    "An error occured while reading finished courses list file %s",
                    getFileName());

            throw new ReadFileException(errorMsg);
        } catch (IllegalStateException e) {
            logger.error(e.getLocalizedMessage(), e);

            String errorMsg = String.format(
                    "The format of the %i.row does not contain a string value",
                    i);

            throw new WrongFormatException(errorMsg);
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    logger.error(e.getLocalizedMessage(), e);
                    String errorMsg = String.format(
                            "An error occured while closing the workbook %s",
                            getFileName());

                    throw new ReadFileException(errorMsg);
                }
            }
        }

        ValidationUtil.validateNonEmptySet(finishedCourses, "finished courses");
    }

    @Override
    public void deleteFinishedCoursesFile() {
        this.fileName = null;
        this.finishedCourses = null;
    }

    private Course getFinishedCourseFromCells(Row row) {
        Course finishedcourse = new Course();

        try {
            finishedcourse.setCourseName(row.getCell(COURSE_NAME_INDEX).getStringCellValue());
            finishedcourse.setCourseType(getCourseTypeFromCell(row.getCell(COURSE_TYPE_INDEX)));
            finishedcourse.setEcts(getEctsFromCell(row.getCell(COURSE_ECTS_INDEX)));
        } catch (Exception e) {
            String errorMsg = String.format(
                    "An error occured while reading the row %d from the file %s",
                    row.getRowNum(),
                    getFileName());
            throw new WrongFormatException(errorMsg);
        }

        return finishedcourse;
    }

    /**
     * Returns true, if the row is empty. It is assumed that the first cell of
     * the row is not empty due to the generated file.
     *
     * @param row the file
     * @return true, if the row is empty
     */
    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        } else if (row.getCell(0) == null) {
            return true;
        }
        Cell cell = row.getCell(0);

        if (cell == null) {
            return true;
        }

        return cell.getCellType() == CellType.BLANK ||
                cell.getStringCellValue().isEmpty();
    }

    private CourseType getCourseTypeFromCell(Cell cell) {
        String cellValue = cell.getStringCellValue();

        for (CourseType courseType : CourseType.values()) {
            if (courseType.getCourseType().equals(cellValue)) {
                return courseType;
            }
        }

        return null;
    }

    private float getEctsFromCell(Cell cell) {
        return (float)cell.getNumericCellValue();
    }

    private String getFileName() {
        return fileName != null ? fileName : "";
    }

    @Override
    public Set<Course> getFinishedCourses() {
        return finishedCourses;
    }
}
