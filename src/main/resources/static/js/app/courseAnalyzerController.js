'use strict'
var module = angular.module('CourseAnalyzer.controllers', []);
module.controller('courseAnalyzerController',['$scope', '$q', 'courseAnalyzerService', 'fileUpload',
    function($scope, $q, courseAnalyzerService, fileUpload) {
        $scope.mandatoryCoursesDto = {
            mandatoryCourses: null
        };
        $scope.certificateList = null;

        $scope.analyzeCourses = function() {
            courseAnalyzerService.analyzeMandatoryCourses($scope.mandatoryCoursesDto);

            $scope.dataUpload = true;
            $scope.errVisibility = false;
            var file = $scope.certificateList;
            var uploadUrl = '/courseanalyzer/readCertificateList';
            fileUpload.uploadFileToUrl(file, uploadUrl).then(function(result){
                $scope.errors = fileUpload.getResponse();
                console.log($scope.errors);
                $scope.errVisibility = true;
            }, function(error) {
                alert('error');
            }).then(function() {
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
module.service('fileUpload', ['$q','$http', function ($q,$http) {
    var deffered = $q.defer();
    var responseData;
    this.uploadFileToUrl = function(file, uploadUrl){
            var fd = new FormData();
            fd.append('file', file);
            return $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: { 'Content-Type' : undefined}
        });
    }
    this.getResponse = function() {
        return responseData;
    }
}]);

