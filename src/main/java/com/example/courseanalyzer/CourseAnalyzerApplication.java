package com.example.courseanalyzer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Starts the whole web application.
 */
@SpringBootApplication
public class CourseAnalyzerApplication {

	private static final Logger logger = LogManager.getLogger(CourseAnalyzerApplication.class.getName());

	private static ConfigurableApplicationContext applicationContext;

	/**
	 *
	 * Closes the web application.
	 *
	 */
	public static void closeApplication() {
		applicationContext.close();
	}

	public static void main(String[] args) {
		logger.info("The web application is started");
		applicationContext = SpringApplication.run(CourseAnalyzerApplication.class, args);
	}
}

