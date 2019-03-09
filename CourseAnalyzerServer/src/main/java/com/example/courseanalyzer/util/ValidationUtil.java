package com.example.courseanalyzer.util;
/**
 * @Package: com.example.courseanalyzer.util
 * @Class: ValidationUtil
 * @Author: Jan
 * @Date: 09.03.2019
 */

import com.example.courseanalyzer.analyzer.exception.NoModelsExtractedException;
import com.example.courseanalyzer.analyzer.exception.WrongFormatException;
import com.example.courseanalyzer.analyzer.model.Course;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

/**
 * Is used to validate different methods and enhance consistent error handling.
 */
public final class ValidationUtil {
    private static final Logger logger = LogManager.getLogger(ValidationUtil.class);

    /**
     * Validates the multipartFile.
     * @param multiPartFile the examined file
     * @param fileName the type of file which is wrongly passed which is used for
     *                 exception message.
     * @throws IllegalArgumentException is thrown, when the passed multipart file
     *                                  {@code multiPartFile} is {@code null}.
     */
    public static void validateMultiPartFileOnServiceLayer(MultipartFile multiPartFile, String fileName) {
        if (multiPartFile == null) {
            String errorMsg = String.format(
                    "The multipartFile for the %s on the service layer is null",
                    fileName);
            logger.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
    }

    /**
     * Validates the file name {@code fileName}.
     * @param fileName the examined file name
     * @param purpose the purpose of the file name which is used for the error
     *                message.
     * @throws IllegalArgumentException is thrown, when the file name is empty
     *                                  or {@code null}.
     */
    public static void validateFilledFileName(String fileName, String purpose) {
        if (fileName == null || fileName.isEmpty()) {
            String errorMsg = String.format(
                    "The file name for %s must not be null or empty",
                    purpose);
            logger.error(errorMsg);

            throw new IllegalArgumentException(errorMsg);
        }
    }

    /**
     * Validates the parsed text from the ressource.
     * @param parsedText the examined text
     * @param pageNr the page number of the parsed text
     * @param textType the type of the text which is used for showing the error
     *                 message.
     * @throws WrongFormatException is thrown, when the parsed text is {@code null}
     *                              or empty.
     */
    public static void validateNonEmptyParsedText(String parsedText, int pageNr, String textType) {
        if (parsedText.isEmpty()) {
            String errorMsg = String.format(
                    "The %s text on page %d is empty",
                    textType,
                    pageNr);
            throw new WrongFormatException(errorMsg);
        }
    }

    /**
     * Validates the set which was extracted from the ressources.
     * <p>This method should be called after the extraction of the set.</p>
     * @param examinedSet the examined set.
     * @param setType the type of set which is used for the error message to
     *                indicate which set could not be extracted.
     * @throws NoModelsExtractedException is thrown, when the set is empty.
     */
    public static void validateNonEmptySet(Set<?> examinedSet, String setType) {
        if(examinedSet == null || examinedSet.isEmpty()) {
            String errorMsg = String.format(
                    "The file has a wrong format to extract information from."
                            + "No %s could be extracted",
                    setType);
            throw new NoModelsExtractedException(errorMsg);
        }
    }
}
