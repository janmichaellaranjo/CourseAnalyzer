'use strict'
var module = angular.module('CourseAnalyzer.services', []);
module.factory('courseAnalyzerService', ['$http', 'CONSTANTS', function($http, CONSTANTS) {
    var service = {};
    service.compareCourses = function() {
        return $http.get(CONSTANTS.compareCourses);
    };

    return service;
}]);