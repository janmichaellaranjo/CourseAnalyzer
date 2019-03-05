'use strict';
var CourseAnalyzerController = /** @class */ (function () {
    function CourseAnalyzerController(courseAnalyzerService, CONSTANTS) {
        this.studyPlan = null;
        this.transitionalProvision = null;
        this.finishedCourses = null;
        this.courseReport = {
            additionalMandatoryCoursesEcts: 0,
            mandatoryCoursesEcts: 0,
            optionalModuleCoursesEcts: 0,
            transferableSkillsEcts: 0,
            remainingMandatoryCourses: null,
            remainingUnassignedFinishedCourses: null
        };
        this.showElement = {
            isMandatoryFilesFilled: false,
            isResultFilled: false
        };
        this.courseAnalyzerService = courseAnalyzerService;
        this.CONSTANTS = CONSTANTS;
    }
    CourseAnalyzerController.prototype.analyzeCourses = function ($q) {
        $q.all([
            this.courseAnalyzerService.uploadFileToUrl(this.finishedCourses, this.CONSTANTS.readFinishedCourseList),
            this.courseAnalyzerService.uploadFileToUrl(this.transitionalProvision, this.CONSTANTS.readTransitionalProvision),
            this.courseAnalyzerService.uploadFileToUrl(this.studyPlan, this.CONSTANTS.readStudyPlan)
        ])
            .then(function () {
            this.courseAnalyzerService.compareCourses()
                .then(function (response) {
                this.courseReport.mandatoryCoursesEcts = response.data.mandatoryCoursesEcts;
                this.courseReport.additionalMandatoryCoursesEcts = response.data.additionalMandatoryCoursesEcts;
                this.courseReport.optionalModuleCoursesEcts = response.data.optionalModuleEcts;
                this.courseReport.transferableSkillsEcts = response.data.transferableSkillsEcts;
                this.courseReport.remainingMandatoryCourses = response.data.remainingMandatoryCourses;
                this.courseReport.remainingUnassignedFinishedCourses = response.data.remainingUnassignedFinishedCourses;
                this.showElement.isResultFilled = true;
            });
        })
            .catch(function (e) {
            alert(e.data.message);
        });
    };
    CourseAnalyzerController.prototype.mandatoryFilesChecker = function () {
        if (this.studyPlan != null && this.finishedCourses != null) {
            this.showElement.isMandatoryFilesFilled = true;
        }
    };
    ;
    CourseAnalyzerController.prototype.closeApplication = function () {
        this.courseAnalyzerService.closeApplication();
    };
    CourseAnalyzerController.$inject = ['$scope', 'courseAnalyzerService'];
    return CourseAnalyzerController;
}());
angular.module('CourseAnalyzer.controllers', [])
    .controller('courseAnalyzerController', ['$scope', '$q', 'courseAnalyzerService', 'CONSTANTS',
    function ($scope, $q, courseAnalyzerService, CONSTANTS) {
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
        $scope.analyzeCourses = function () {
            $q.all([
                courseAnalyzerService.uploadFileToUrl($scope.finishedCourses, CONSTANTS.readFinishedCourseList),
                courseAnalyzerService.uploadFileToUrl($scope.transitionalProvision, CONSTANTS.readTransitionalProvision),
                courseAnalyzerService.uploadFileToUrl($scope.studyPlan, CONSTANTS.readStudyPlan)
            ])
                .then(function () {
                courseAnalyzerService.compareCourses()
                    .then(function (response) {
                    $scope.courseReport.mandatoryCoursesEcts = response.data.mandatoryCoursesEcts;
                    $scope.courseReport.additionalMandatoryCoursesEcts = response.data.additionalMandatoryCoursesEcts;
                    $scope.courseReport.optionalModuleCoursesEcts = response.data.optionalModuleEcts;
                    $scope.courseReport.transferableSkillsEcts = response.data.transferableSkillsEcts;
                    $scope.courseReport.remainingMandatoryCourses = response.data.remainingMandatoryCourses;
                    $scope.courseReport.remainingUnassignedFinishedCourses = response.data.remainingUnassignedFinishedCourses;
                    $scope.showElement.isResultFilled = true;
                });
            })
                .catch(function (e) {
                alert(e.data.message);
            });
        };
        $scope.showElement = {
            isMandatoryFilesFilled: false,
            isResultFilled: false
        };
        $scope.mandatoryFilesChecker = function () {
            if ($scope.studyPlan != null && $scope.finishedCourses != null) {
                $scope.showElement.isMandatoryFilesFilled = true;
            }
        };
        $scope.closeApplication = function () {
            courseAnalyzerService.closeApplication();
        };
    }
])
    .directive('filestudyplan', function () {
    return {
        scope: {
            studyPlan: '='
        },
        link: function (scope, element, attrs) {
            element.bind('change', function () {
                scope.$apply(function () {
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
        link: function (scope, element, attrs) {
            element.bind('change', function () {
                scope.$apply(function () {
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
        link: function (scope, element, attrs) {
            element.bind('change', function () {
                scope.$apply(function () {
                    var parentScope = scope.$parent;
                    parentScope.finishedCourses = element[0].files[0];
                    parentScope.mandatoryFilesChecker();
                });
            });
        }
    };
});
//# sourceMappingURL=courseAnalyzerController.js.map