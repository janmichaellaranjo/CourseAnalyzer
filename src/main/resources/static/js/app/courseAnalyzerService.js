'use strict'
var module = angular.module('CourseAnalyzer.services', []);
module.factory('courseAnalyzerService', ['$http', 'CONSTANTS', function($http, CONSTANTS) {
    var service = {};
    service.analyzeMandatoryCourses = function(mandatoryCoursesDto) {
        return $http.post(CONSTANTS.analyzeMandatoryCourses, mandatoryCoursesDto);
    };
    service.analyzeAdditionalMandatoryCourses = function(mandatoryCoursesDto) {
            return $http.post(CONSTANTS.analyzeAdditionalMandatoryCourses, mandatoryCoursesDto);
    };
    service.compareCourses = function() {
        return $http.get(CONSTANTS.compareCourses);
    };

    return service;
}]);