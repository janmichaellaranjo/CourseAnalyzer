'use strict'
var module = angular.module('CourseAnalyzer.controllers', []);
module.controller('courseAnalyzerController',['$scope', '$q', 'courseAnalyzerService', 'fileUpload', 'CONSTANTS',
    function($scope, $q, courseAnalyzerService, fileUpload, CONSTANTS) {
        $scope.mandatoryCoursesDto = {
            mandatoryCourses: null,
            additionalMandatoryCourses: null
        };
        $scope.certificateList = null;

        $scope.analyzeCourses = function() {
            $q.all([
                fileUpload.uploadFileToUrl($scope.certificateList, CONSTANTS.readCertificateList),
                courseAnalyzerService.analyzeMandatoryCourses($scope.mandatoryCoursesDto),
                courseAnalyzerService.analyzeAdditionalMandatoryCourses($scope.mandatoryCoursesDto)
            ])
            .then(function() {
                courseAnalyzerService.compareCourses();
            });
        };
    }
])
.directive('filemodel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            element.bind('change', function(){
                scope.$apply(function(){
                    scope.certificateList =  element[0].files[0];
                });
            });
        }
    };
}]);
module.service('fileUpload', ['$q','$http', function ($q, $http) {
    this.uploadFileToUrl = function(file, uploadUrl){
        var formdata = new FormData();
        formdata.append('file', file);
        return $http.post(uploadUrl, formdata, {
            transformRequest: angular.identity,
            headers: { 'Content-Type' : undefined}
        });
    }
}]);

