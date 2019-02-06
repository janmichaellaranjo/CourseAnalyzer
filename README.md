# CourseAnalyzer

This web application helps students to give a better view of their received ECTs and remaining mandatory courses. The application calculates conviently the amount of ECTs of mandatory courses, courses from optional modules and transferable skills depending on the certificate list, study plan and transitional provision. Also the remaining mandatory courses will be listed.

<span style="color:red">This version just contains the most basic functionality. It prints only the calculated ECTS.</span>

## Getting Started

These instructions will help you to run the web application and how to use the application properly.

### Prerequisites

```
JDK 8
Maven
Any web browser
```

### Running The Web Application

1) First download or clone this repository. 
2) Execute the following command for Windows
```
mvnw clean install
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;for Linux
```
.\mvn clean install
```

3) The previous step should create a jar file *courseanalyzer-${project.version}.jar*. This file has to be executed.
4) Finally start your web browser and type the following URL to run the web application:
```
localhost:8080
```

## TODO

* Return all remaining mandatory courses
* Add Validations and Exception Handling
* Better formatting of site
* More user interaction on the web application
* More course informations
* Make application repsonsive and different formats for different medias
* Refactore code


## Used Frameworks

* AngularJS
* Spring
* H2 Database
* Apache POI
* PDFBox
* Log4J