
'use strict'
var demoApp = angular.module('CourseAnalyzer', ['CourseAnalyzer.controllers','CourseAnalyzer.services']);
demoApp.constant('CONSTANTS', {
    analyzeAdditionalMandatoryCourses: '/courseanalyzer/analyzeAdditionalMandatoryCourses',
    readStudyPlan: '/courseanalyzer/readStudyPlan',
    readCertificateList: '/courseanalyzer/readCertificateList',
    compareCourses: '/courseanalyzer/compareCourses'
});