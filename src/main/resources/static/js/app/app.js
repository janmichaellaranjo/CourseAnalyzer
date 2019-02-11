'use strict'
var demoApp = angular.module('CourseAnalyzer', ['CourseAnalyzer.controllers','CourseAnalyzer.services']);
demoApp.constant('CONSTANTS', {
    readStudyPlan: '/courseanalyzer/readStudyPlan',
    readTransitionalProvision: '/courseanalyzer/readTransitionalProvision',
    readCertificateList: '/courseanalyzer/readCertificateList',
    compareCourses: '/courseanalyzer/compareCourses',
    closeApplication: '/courseanalyzer/closeApplication'
});