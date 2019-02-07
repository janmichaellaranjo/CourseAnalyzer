package com.example.courseanalyzer.analyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer
 * @Class: ReadFileException
 * @Author: Jan
 * @Date: 07.02.2019
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

/**
 * This exception is thrown, when a IO problem occures during the process of 
 * reading the file to extract informations from. For instance the passed file is
 * damaged.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ReadFileException extends RuntimeException {
    public ReadFileException() {
        super();
    }
    public ReadFileException(String message, Throwable cause) {
        super(message, cause);
    }
    public ReadFileException(String message) {
        super(message);
    }
    public ReadFileException(Throwable cause) {
        super(cause);
    }
}
