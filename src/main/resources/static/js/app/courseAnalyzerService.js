'use strict'
var module = angular.module('CourseAnalyzer.services', []);
module.factory('courseAnalyzerService', ['$q', '$http', 'CONSTANTS', function($q, $http, CONSTANTS) {
    var service = {};

    service.uploadFileToUrl = function(file, uploadUrl){
        var formdata = new FormData();
        formdata.append('file', file);
        return $http.post(uploadUrl, formdata, {
            transformRequest: angular.identity,
            headers: { 'Content-Type' : undefined}
        });
    }

    service.compareCourses = function() {
        return $http.get(CONSTANTS.compareCourses);
    };

    service.closeApplication = function() {
        return $http.get(CONSTANTS.closeApplication);
    }

    return service;
}]);