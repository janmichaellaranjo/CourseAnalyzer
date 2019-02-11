# CourseAnalyzer

This web application helps students to give a better view of their received ECTs and remaining mandatory courses. The application calculates conviently the amount of ECTs of mandatory courses, courses from optional modules and transferable skills depending on the certificate list, study plan and transitional provision. Also the remaining mandatory courses will be listed to help you plan your future semesters.

**This version just contains the most basic functionality. It currently prints the calculated ECTS of mandatory courses, courses form optional modules, transferable skills, the remaining mandatory courses and unassignable courses.**\
**The current implementation works for the *TU Wien* study plan and TISS certificate**\
**There is also a small problem occurs while comparing finished mandatory courses with the mandatory courses in the transitional provision.**

## Getting Started

These instructions will help you to run the web application and how to use the application properly.

### Prerequisites

```
JDK 8
Maven
Web browser(Firefox, Chrome,...)
```

### Required Files

* Study plan of your curriculum
* Transitional provision (optional)
* Finished courses file e.g. certificate list

### Running The Web Application

1) First download or clone this repository. 
2) Execute the following command for Windows
```
mvnw install
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;for Linux
```
.\mvn install
```

3) The previous step should create a jar file *courseanalyzer-${project.version}.jar*. This file has to be executed.
4) Finally start your web browser and type the following URL to run the web application:
```
localhost:8080
```


## Development

Due to the fact that the extraction of information depends on the format of the files the current implementation cannot be applied to every study plan, transitional provision or file that contains the finished courses.\
To cope with this problem, there exists interfaces for extracting the informations to easily change implementations for different formats. Basically there are 3 main interfaces for extracting the informations of the study plans, transitional provisions and finished courses. Furthermore the study plan can be divided into the extraction of the mandatory courses, modules and soft skills.\
For further implementation details how the interface should be implemented refer to the JavaDoc.

### Format

This application heavily relies on the format of the documents because the documents are scanned and the necessary informations are extracted. To extract the information properly the application expects a consistent order and certain phrases.

### Implementation

The current implementation works for the *TU Wien*'s current study plans in Computer Science ([study plan 2018](http://www.informatik.tuwien.ac.at/studium/angebot/studienplaene/informatik-archiv/informatik-studienplan-2018)) and the automatically generated certificate list file which can be retrieved on *TISS* page. The transitional provision ([transitional provision 2018](http://www.informatik.tuwien.ac.at/studium/angebot/studienplaene/informatik-archiv/informatik-uebergang-2018)) also relies on the format of the corresponding file.

## Used Frameworks

* AngularJS
* Spring
* Apache POI
* PDFBox
* Log4J

## TODO

* Remove mistake of courses in groups in transitional provision
* ~~Return all remaining mandatory courses~~
* ~~Add validations on every layerand Exception Handling~~
* Better error messaging
* Better formatting of site
* More user interaction
* Create a tutorial part in web application
* ~~More course informations~~
* Make application responsive and different formats for different medias
* ~~Change to multiple languages~~
* Refactore code
* Create tests
* Properly log every class
* Create a how to use in README