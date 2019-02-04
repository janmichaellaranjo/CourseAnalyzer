'use strict'
var module = angular.module('CourseAnalyzer.controllers', []);
module.controller('courseAnalyzerController',['$scope', '$q', 'courseAnalyzerService', 'fileUpload', 'CONSTANTS',
    function($scope, $q, courseAnalyzerService, fileUpload, CONSTANTS) {
        $scope.mandatoryCoursesDto = {
            additionalMandatoryCourses: null
        };
        $scope.courseReport = {
            additionalMandatoryCoursesEcts: 0,
            mandatoryCoursesEcts: 0
        };
        $scope.certificateList = null;
        $scope.studyPlan = null;

        $scope.analyzeCourses = function() {
            $q.all([
                fileUpload.uploadFileToUrl($scope.certificateList, CONSTANTS.readCertificateList),
                fileUpload.uploadFileToUrl($scope.studyPlan, CONSTANTS.readStudyPlan),
                courseAnalyzerService.analyzeAdditionalMandatoryCourses($scope.mandatoryCoursesDto)
            ])
            .then(function() {
                courseAnalyzerService.compareCourses()
                .then(function(response){
                   $scope.courseReport.mandatoryCoursesEcts = response.data.mandatoryCoursesEcts;
                   $scope.courseReport.additionalMandatoryCoursesEcts = response.data.additionalMandatoryCoursesEcts;
                });
            });
        };

        $scope.addFile = function (variable, file) {
            $scope.variable = file;
        };
    }
])
.directive('filestudyplan', function () {
    return {
        scope: {
              studyPlan: '='
        },
        link: function(scope, element, attrs) {
            element.bind('change', function(){
                scope.$apply(function(){
                    scope.$parent.studyPlan = element[0].files[0];
                });
            });
        }
    };
})
.directive('filecertificatelist', function () {
    return {
        scope: {
              certificateList: '='
        },
        link: function(scope, element, attrs) {
            element.bind('change', function(){
                scope.$apply(function(){
                    scope.$parent.certificateList = element[0].files[0];
                });
            });
        }
    };
});

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

