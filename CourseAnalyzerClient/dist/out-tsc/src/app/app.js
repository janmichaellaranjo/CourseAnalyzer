'use strict';
angular.module('CourseAnalyzer', ['CourseAnalyzer.controllers', 'CourseAnalyzer.services'])
    .constant('CONSTANTS', {
    readStudyPlan: '/courseanalyzer/readStudyPlan',
    readTransitionalProvision: '/courseanalyzer/readTransitionalProvision',
    readFinishedCourseList: '/courseanalyzer/readFinishedCourseList',
    compareCourses: '/courseanalyzer/compareCourses',
    closeApplication: '/courseanalyzer/closeApplication'
});
//# sourceMappingURL=app.js.map