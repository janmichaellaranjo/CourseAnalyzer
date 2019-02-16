package com.example.courseanalyzer.analyzer.exception;
/**
 * @Package: com.example.courseanalyzer.analyzer.exception
 * @Class: NoModelsExtractedException
 * @Author: Jan
 * @Date: 16.02.2019
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is thrown, when no model could not be extracted from the
 * analysis.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoModelsExtractedException  extends RuntimeException {
    public NoModelsExtractedException() {
        super();
    }
    public NoModelsExtractedException(String message, Throwable cause) {
        super(message, cause);
    }
    public NoModelsExtractedException(String message) {
        super(message);
    }
    public NoModelsExtractedException(Throwable cause) {
        super(cause);
    }
}
