
'use strict'
var demoApp = angular.module('CourseAnalyzer', ['CourseAnalyzer.controllers','CourseAnalyzer.services']);
demoApp.constant('CONSTANTS', {
    analyzeMandatoryCourses: '/courseanalyzer/analyzeMandatoryCourses',
    readCertificateList: '/courseanalyzer/readCertificateList',
    compareCourses: '/courseanalyzer/compareCourses'
});