package com.example.courseanalyzer.analyzer;
/**
 * @Package: com.example.courseanalyzer.analyzer.addtionalmandatorycourseanalyzer
 * @Class: WrongFormatException
 * @Author: Jan
 * @Date: 07.02.2019
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * This exception is thrown, when the passed file does not have to required
 * format to extract the necessary information e.g. the page is empty, the order
 * of the line is wrong, the course type does not exist,....
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WrongFormatException extends RuntimeException {
    public WrongFormatException() {
        super();
    }
    public WrongFormatException(String message, Throwable cause) {
        super(message, cause);
    }
    public WrongFormatException(String message) {
        super(message);
    }
    public WrongFormatException(Throwable cause) {
        super(cause);
    }
}
