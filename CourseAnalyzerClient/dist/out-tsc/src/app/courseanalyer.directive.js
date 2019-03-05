import * as tslib_1 from "tslib";
import { ViewChild, ElementRef } from '@angular/core';
var CourseAnalyzerDirective = /** @class */ (function () {
    function CourseAnalyzerDirective(courseAnalyzerController) {
        this.restrict = 'A';
        this.require = 'ngModel';
        this.scope = {
            ngModel: '='
        };
        this.link = function (scope, element) {
        };
        this.courseAnalyzerController = courseAnalyzerController;
    }
    CourseAnalyzerDirective.factory = function () {
        var directive = function (courseAnalyzerController) { return new CourseAnalyzerDirective(courseAnalyzerController); };
        directive.$inject = ['$http'];
        return directive;
    };
    CourseAnalyzerDirective.$inject = ['$scope', 'courseAnalyzerController'];
    tslib_1.__decorate([
        ViewChild('fileInput'),
        tslib_1.__metadata("design:type", ElementRef)
    ], CourseAnalyzerDirective.prototype, "fileInput", void 0);
    return CourseAnalyzerDirective;
}());
export { CourseAnalyzerDirective };
//# sourceMappingURL=courseanalyer.directive.js.map