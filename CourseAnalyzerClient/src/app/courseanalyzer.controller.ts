import { Component } from '@angular/core';
import { Services } from "@angular/core/src/view";
import { CourseAnalyzerService } from './courseanalyzer.service'
import {CONSTANTS} from './courseanalyzer.module.ajs'


export class CourseAnalyzerController {

    public studyPlan = null;
    public transitionalProvision = null;
    public finishedCourses = null;
    public courseReport = {
        additionalMandatoryCoursesEcts: 0,
        mandatoryCoursesEcts: 0,
        optionalModuleCoursesEcts: 0,
        transferableSkillsEcts: 0,
        remainingMandatoryCourses: null,
        remainingUnassignedFinishedCourses: null
    };
    public showElement = {
        isMandatoryFilesFilled: false,
        isResultFilled: false
    };
    courseAnalyzerService: CourseAnalyzerService;
    CONSTANTS: any;

    constructor(courseAnalyzerService: CourseAnalyzerService, CONSTANTS: CONSTANTS) {
        this.courseAnalyzerService = courseAnalyzerService;
        this.CONSTANTS = CONSTANTS;
    }

    private analyzeCourses($q) {
        $q.all([
            this.courseAnalyzerService.uploadFileToUrl(this.finishedCourses, this.CONSTANTS.readFinishedCourseList),
            this.courseAnalyzerService.uploadFileToUrl(this.transitionalProvision, this.CONSTANTS.readTransitionalProvision),
            this.courseAnalyzerService.uploadFileToUrl(this.studyPlan, this.CONSTANTS.readStudyPlan)
        ])
        .then(function() {
            this.courseAnalyzerService.compareCourses()
            .then(function(response){
                this.courseReport.mandatoryCoursesEcts = response.data.mandatoryCoursesEcts;
                this.courseReport.additionalMandatoryCoursesEcts = response.data.additionalMandatoryCoursesEcts;
                this.courseReport.optionalModuleCoursesEcts = response.data.optionalModuleEcts;
                this.courseReport.transferableSkillsEcts = response.data.transferableSkillsEcts;

                this.courseReport.remainingMandatoryCourses = response.data.remainingMandatoryCourses;
                this.courseReport.remainingUnassignedFinishedCourses = response.data.remainingUnassignedFinishedCourses;

                this.showElement.isResultFilled = true;
            });
        })
        .catch(function(e) {
            alert(e.data.message);
        });
    }

    private mandatoryFilesChecker() {
        if (this.studyPlan != null && this.finishedCourses != null) {
            this.showElement.isMandatoryFilesFilled = true;
        }
    };

    private closeApplication() {
        this.courseAnalyzerService.closeApplication();
    }

}
/*
angular.module('CourseAnalyzer.controllers', [])
  .controller('courseAnalyzerController', ['$scope', '$q', 'courseAnalyzerService', 'CONSTANTS',
    function($scope, $q, courseAnalyzerService, CONSTANTS) {

        $scope.studyPlan = null;
        $scope.transitionalProvision = null;
        $scope.finishedCourses = null;
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
                courseAnalyzerService.uploadFileToUrl($scope.finishedCourses, CONSTANTS.readFinishedCourseList),
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
            if ($scope.studyPlan != null && $scope.finishedCourses != null) {
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
                    var parentScope = scope;
                    studyPlan = element[0].files[0];
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
              finishedCourses: '='
        },
        link: function(scope, element, attrs) {
            element.bind('change', function(){
                scope.$apply(function(){
                    var parentScope = scope.$parent;
                    parentScope.finishedCourses = element[0].files[0];
                    parentScope.mandatoryFilesChecker();
                });
            });
        }
    };
});
*/