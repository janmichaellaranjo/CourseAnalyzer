'use strict'
var module = angular.module('CourseAnalyzer.controllers', []);
module.controller('courseAnalyzerController',['$scope', '$q', 'courseAnalyzerService', 'fileUpload', 'CONSTANTS',
    function($scope, $q, courseAnalyzerService, fileUpload, CONSTANTS) {
        $scope.mandatoryCoursesDto = {
            mandatoryCourses: null
        };
        $scope.certificateList = null;

        $scope.analyzeCourses = function() {
            courseAnalyzerService.analyzeMandatoryCourses($scope.mandatoryCoursesDto).then(function() {
                var file = $scope.certificateList;
                fileUpload.uploadFileToUrl(file, CONSTANTS.readCertificateList).then(function(result){
                    $scope.errors = fileUpload.getResponse();
                    console.log($scope.errors);
                }, function(error) {
                    alert('error');
                }).then(function() {
                    courseAnalyzerService.compareCourses();
                });

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
    var responseData;
    this.uploadFileToUrl = function(file, uploadUrl){
        var formdata = new FormData();
        formdata.append('file', file);
        return $http.post(uploadUrl, formdata, {
            transformRequest: angular.identity,
            headers: { 'Content-Type' : undefined}
        });
    }
    this.getResponse = function() {
        return responseData;
    }
}]);

