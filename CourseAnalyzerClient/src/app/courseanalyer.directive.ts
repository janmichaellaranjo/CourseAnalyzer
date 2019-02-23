import { CourseAnalyzerController } from './courseAnalyzer.controller'
import {ViewChild, ElementRef} from '@angular/core';

export class CourseAnalyzerDirective implements ng.IDirective {
    restrict = 'A';
    require = 'ngModel';
    scope = {
        ngModel: '='
    }
    courseAnalyzerController: CourseAnalyzerController;
    @ViewChild('fileInput') fileInput: ElementRef;

    static $inject = ['$scope', 'courseAnalyzerController'];

    constructor(courseAnalyzerController: CourseAnalyzerController) {
        this.courseAnalyzerController = courseAnalyzerController;
    }

    link = (scope: ng.IScope, element: JQuery) => {



    }

    static factory(): ng.IDirectiveFactory {
        const directive = (courseAnalyzerController: CourseAnalyzerController) => new CourseAnalyzerDirective(courseAnalyzerController);
        directive.$inject = ['$http'];
        return directive;
    }
}