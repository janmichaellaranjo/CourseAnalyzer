import * as angular from 'angular';
import 'angular-route';
var MODULE_NAME = 'CourseAnalyzer';
angular.module(MODULE_NAME, ['CourseAnalyzer.controllers', 'CourseAnalyzer.services'])
    .constant('CONSTANTS', {
    readStudyPlan: '/courseanalyzer/readStudyPlan',
    readTransitionalProvision: '/courseanalyzer/readTransitionalProvision',
    readFinishedCourseList: '/courseanalyzer/readFinishedCourseList',
    compareCourses: '/courseanalyzer/compareCourses',
    closeApplication: '/courseanalyzer/closeApplication'
});
export default MODULE_NAME;
//# sourceMappingURL=app.module.ajs.js.map