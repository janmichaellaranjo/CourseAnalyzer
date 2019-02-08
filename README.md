# CourseAnalyzer

This web application helps students to give a better view of their received ECTs and remaining mandatory courses. The application calculates conviently the amount of ECTs of mandatory courses, courses from optional modules and transferable skills depending on the certificate list, study plan and transitional provision. Also the remaining mandatory courses will be listed.

**This version just contains the most basic functionality. It currently prints the calculated ECTS of mandatory courses, courses form optional modules and transferable skills.**

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

* Remove mistake of courses in groups in transitional provision
* ~~Return all remaining mandatory courses~~
* ~~Add validations on every layerand Exception Handling~~
* Better error messaging
* Better formatting of site
* More user interaction
* Create a tutorial part
* ~~More course informations~~
* Make application responsive and different formats for different medias
* Change to multiple languages
* Refactore code
* Create tests


## Used Frameworks

* AngularJS
* Spring
* Apache POI
* PDFBox
* Log4J
