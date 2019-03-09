package com.example.courseanalyzer.util;
/**
 * @Package: com.example.courseanalyzer.util
 * @Class: FileUtil
 * @Author: Jan
 * @Date: 16.02.2019
 */

import org.apache.commons.io.FileUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Provides utility methods that uses IO operations.
 */
public final class FileUtil {

    /**
     * Returns the multipart file that contains the file with the corresponding
     * path {@code filePath}.
     * @param filePath the path of the file
     * @return the multipart file that contains the file with the corresponding
     *         path {@code filePath}.
     * @throws IOException is thrown, when a problem occurs while reading the
     *                     file or retrieving the content of the file.
     */
    public static MultipartFile createMultiPartFile(String filePath) throws IOException {
        File file = getFileFromResource(filePath);
        byte[] content = IOUtils.toByteArray(new FileInputStream(file));
        return new MockMultipartFile(file.getName(), content);
    }

    /**
     * Returns the file with the path {@code filePath}.
     * <p>An abstract file is returned, when the file does not exist.</p>
     *
     * @param filePath the path of the file.
     * @return the file with the path {@code filePath}.
     */
    public static File getFileFromResource(String filePath) {
        ClassLoader classLoader = FileUtil.class.getClassLoader();
        return new File(classLoader.getResource(filePath).getFile());
    }

    /**
     * Returns the whole content of the file as a string.
     * @param filePath the path of the file
     * @return the whole content of the file as a string.
     * @throws IOException is thrown, when a problem occures while reading the
     *                     file.
     */
    public static String getWholeStringFromFile(String filePath) throws IOException {
        File file = FileUtil.getFileFromResource(filePath);
        return FileUtils.readFileToString(file, "utf-8");
    }
}
