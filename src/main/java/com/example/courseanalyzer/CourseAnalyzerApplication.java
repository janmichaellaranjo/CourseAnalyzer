package com.example.courseanalyzer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class CourseAnalyzerApplication {

	private static final Logger logger = LogManager.getLogger(CourseAnalyzerApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(CourseAnalyzerApplication.class, args);
	}

}

