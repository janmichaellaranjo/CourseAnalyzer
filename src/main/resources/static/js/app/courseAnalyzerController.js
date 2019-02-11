'use strict'
var module = angular.module('CourseAnalyzer.controllers', []);
module.controller('courseAnalyzerController',['$scope', '$q', 'courseAnalyzerService', 'CONSTANTS',
    function($scope, $q, courseAnalyzerService, CONSTANTS) {

        $scope.studyPlan = null;
        $scope.transitionalProvision = null;
        $scope.certificateList = null;
        $scope.courseReport = {
            additionalMandatoryCoursesEcts: 0,
            mandatoryCoursesEcts: 0,
            optionalModuleCoursesEcts: 0,
            transferableSkillsEcts: 0,
            remainingMandatoryCourses: null,
            remainingUnassignedFinishedCourses: null
        };

        $scope.analyzeCourses = function() {
            $q.all([
                courseAnalyzerService.uploadFileToUrl($scope.certificateList, CONSTANTS.readCertificateList),
                courseAnalyzerService.uploadFileToUrl($scope.transitionalProvision, CONSTANTS.readTransitionalProvision),
                courseAnalyzerService.uploadFileToUrl($scope.studyPlan, CONSTANTS.readStudyPlan)
            ])
            .then(function() {
                courseAnalyzerService.compareCourses()
                .then(function(response){
                    $scope.courseReport.mandatoryCoursesEcts = response.data.mandatoryCoursesEcts;
                    $scope.courseReport.additionalMandatoryCoursesEcts = response.data.additionalMandatoryCoursesEcts;
                    $scope.courseReport.optionalModuleCoursesEcts = response.data.optionalModuleEcts;
                    $scope.courseReport.transferableSkillsEcts = response.data.transferableSkillsEcts;

                    $scope.courseReport.remainingMandatoryCourses = response.data.remainingMandatoryCourses;
                    $scope.courseReport.remainingUnassignedFinishedCourses = response.data.remainingUnassignedFinishedCourses;

                    $scope.showElement.isResultFilled = true;
                });
            })
            .catch(function(e) {
                alert(e.data.message);
            });
        };

        $scope.showElement = {
            isMandatoryFilesFilled: false,
            isResultFilled: false
        };

        $scope.mandatoryFilesChecker = function() {
            if ($scope.studyPlan != null && $scope.certificateList != null) {
                $scope.showElement.isMandatoryFilesFilled = true;
            }
        };

        $scope.closeApplication = function() {
            courseAnalyzerService.closeApplication();
        }
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
                    var parentScope = scope.$parent;
                    parentScope.studyPlan = element[0].files[0];
                    parentScope.mandatoryFilesChecker();
                });
            });
        }
    };
})
.directive('filetransitionalprovision', function () {
    return {
        scope: {
              studyPlan: '='
        },
        link: function(scope, element, attrs) {
            element.bind('change', function(){
                scope.$apply(function(){
                    scope.$parent.transitionalProvision = element[0].files[0];
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
                    var parentScope = scope.$parent;
                    parentScope.certificateList = element[0].files[0];
                    parentScope.mandatoryFilesChecker();
                });
            });
        }
    };
});


