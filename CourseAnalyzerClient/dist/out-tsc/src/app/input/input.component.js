import * as tslib_1 from "tslib";
import { Component, ChangeDetectorRef, ElementRef, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { HomeService } from '../home/home.service';
import { AppRoutingService } from '../app-routing.service';
import { InputService } from './input.service';
import { ReportService } from '../report/report.service';
import { ReportComponent } from '../report/report.component';
var InputComponent = /** @class */ (function () {
    function InputComponent(http, router, inputService, reportService, appRoutingService, homeService, cdRef) {
        this.http = http;
        this.router = router;
        this.inputService = inputService;
        this.reportService = reportService;
        this.appRoutingService = appRoutingService;
        this.homeService = homeService;
        this.cdRef = cdRef;
        this.removable = true;
        this.backEndUrl = 'http://localhost:8080/courseanalyzer';
        this.isButtonDisabled = true;
    }
    InputComponent.prototype.ngOnInit = function () {
        this.appRoutingService.maintainSameHomeTabWhenHomeClicked();
    };
    InputComponent.prototype.ngAfterViewInit = function () {
        if (this.inputService.studyPlan != null) {
            this.studyPlanFilePath = this.inputService.studyPlan.name;
            this.isStudyPlanFileCorrect = true;
            this.isStudyPlanFileSelected = true;
        }
        if (this.inputService.transitionalProvision != null) {
            this.transitionalProvisionFilePath = this.inputService.transitionalProvision.name;
            this.isTransitionalProvisionFileCorrect = true;
            this.isTransitionalProvisionFileSelected = true;
        }
        if (this.inputService.finishedCourses != null) {
            this.finishedCourseFilePath = this.inputService.finishedCourses.name;
            this.isFinishedCoursesFileCorrect = true;
            this.isFinishedCoursesFileSelected = true;
        }
        this.activateButton();
        this.cdRef.detectChanges();
    };
    InputComponent.prototype.handleStudyPlanInput = function (files) {
        var _this = this;
        var file = files.item(0);
        if (file != null) {
            var url = this.backEndUrl + '/readStudyPlan';
            var formData = new FormData();
            var options = {};
            formData.append('studyPlan', file, file.name);
            console.log('The file ' + file.name + " is sent to " + url);
            this.http.post(url, formData, options)
                .pipe(catchError(this.inputService.handleError))
                .subscribe(function () { }, function (error) {
                if (error != undefined) {
                    console.error(error);
                }
                _this.studyPlanErrorMessage = error;
                _this.isStudyPlanFileSelected = true;
                _this.isStudyPlanFileCorrect = false;
            }, function () {
                _this.inputService.studyPlan = file;
                _this.isStudyPlanFileCorrect = true;
                _this.isStudyPlanFileSelected = true;
                _this.studyPlanFilePath = file.name;
                _this.studyPlanErrorMessage = null;
                _this.activateButton();
            });
        }
        else {
            this.isStudyPlanFileSelected = true;
        }
        this.inputService.errorMsg = null;
    };
    InputComponent.prototype.handleTransitionalProvisionInput = function (files) {
        var _this = this;
        var file = files.item(0);
        if (file != null) {
            var url = this.backEndUrl + '/readTransitionalProvision';
            var formData = new FormData();
            var options = {};
            formData.append('transitionalProvision', file, file.name);
            console.log('The file ' + file.name + " is sent to " + url);
            this.http.post(url, formData, options)
                .pipe(catchError(this.inputService.handleError))
                .subscribe(function (data) { }, function (error) {
                console.error(error);
                _this.transitionalProvisionErrorMessage = error;
                _this.isTransitionalProvisionFileSelected = true;
                _this.isTransitionalProvisionFileCorrect = false;
            }, function () {
                _this.inputService.transitionalProvision = file;
                _this.transitionalProvisionFilePath = file.name;
                _this.isTransitionalProvisionFileCorrect = true;
                _this.isTransitionalProvisionFileSelected = true;
                _this.activateButton();
            });
        }
        else {
            this.isTransitionalProvisionFileSelected = true;
        }
        this.inputService.errorMsg = null;
    };
    InputComponent.prototype.handleFinishedCoursesInput = function (files) {
        var _this = this;
        var file = files.item(0);
        if (file != null) {
            var url = this.backEndUrl + '/readFinishedCourseList';
            var formData = new FormData();
            var options = {};
            formData.append('finishedCourses', file, file.name);
            console.log('The file ' + file.name + " is sent to " + url);
            this.http.post(url, formData, options)
                .pipe(catchError(this.inputService.handleError)).
                subscribe(function (data) { }, function (error) {
                console.error(error);
                _this.finishedCoursesErrorMessage = error;
                _this.isFinishedCoursesFileSelected = true;
                _this.isFinishedCoursesFileCorrect = false;
            }, function () {
                _this.inputService.finishedCourses = file;
                _this.finishedCourseFilePath = file.name;
                _this.isFinishedCoursesFileCorrect = true;
                _this.isFinishedCoursesFileSelected = true;
                _this.activateButton();
            });
        }
        else {
            this.isFinishedCoursesFileSelected = true;
        }
        this.inputService.errorMsg = null;
    };
    InputComponent.prototype.compareCourses = function () {
        var _this = this;
        var url = this.backEndUrl + "/compareCourses";
        this.http.get(url)
            .pipe(catchError(this.inputService.handleError))
            .subscribe(function (data) {
            _this.handleResult(data);
        }, function (error) { return console.error(error); }, function () { });
    };
    InputComponent.prototype.handleResult = function (data) {
        this.reportService.data = data;
        this.reportService.isAccessible = true;
        this.selectReportComponent();
        //this.homeService.disableRouterTab(false);
        //this.router.navigate([this.homeService.REPORT_URL]);
    };
    InputComponent.prototype.selectReportComponent = function () {
        this.homeService.previousTab = this.homeService.REPORT_URL;
        this.homeService.component = ReportComponent;
        this.homeService.selectedTabIndex = this.homeService.REPORT_INDEX;
        this.homeService.isReportDisabled = false;
        this.homeService.displayComponent();
    };
    InputComponent.prototype.removeStudyPlan = function () {
        var _this = this;
        this.inputService.studyPlan = null;
        this.studyPlanErrorMessage = null;
        this.isStudyPlanFileSelected = false;
        this.isStudyPlanFileCorrect = false;
        this.studyPlanFileInput.nativeElement.value = "";
        this.inputService.resetFile('studyPlanFile')
            .subscribe(function () { }, function (error) {
            console.error(error);
            _this.inputService.errorMsg = error;
        }, function () { });
        this.activateButton();
    };
    InputComponent.prototype.removeTransitionalProvision = function () {
        this.inputService.transitionalProvision = null;
        this.isTransitionalProvisionFileCorrect = false;
        this.isTransitionalProvisionFileSelected = false;
        this.transitionalProvisionFileInput.nativeElement.value = "";
        this.inputService.resetFile('transitionalProvisionFile')
            .subscribe(function () { }, function (error) { return console.error(error); }, function () { });
    };
    InputComponent.prototype.removeFinishedCourses = function () {
        this.inputService.finishedCourses = null;
        this.isFinishedCoursesFileCorrect = false;
        this.isFinishedCoursesFileSelected = false;
        this.finishedCoursesFileInput.nativeElement.value = "";
        this.inputService.resetFile('finishedCoursesFile')
            .subscribe(function () { }, function (error) { return console.error(error); }, function () { });
        this.activateButton();
    };
    InputComponent.prototype.activateButton = function () {
        if (this.inputService.isMandatoryFilesSelected()) {
            this.isButtonDisabled = false;
        }
        else {
            this.isButtonDisabled = true;
        }
    };
    tslib_1.__decorate([
        ViewChild('studyPlanFileInput'),
        tslib_1.__metadata("design:type", ElementRef)
    ], InputComponent.prototype, "studyPlanFileInput", void 0);
    tslib_1.__decorate([
        ViewChild('transitionalProvisionFileInput'),
        tslib_1.__metadata("design:type", ElementRef)
    ], InputComponent.prototype, "transitionalProvisionFileInput", void 0);
    tslib_1.__decorate([
        ViewChild('finishedCoursesFileInput'),
        tslib_1.__metadata("design:type", ElementRef)
    ], InputComponent.prototype, "finishedCoursesFileInput", void 0);
    InputComponent = tslib_1.__decorate([
        Component({
            selector: 'app-input',
            templateUrl: './input.component.html',
            styleUrls: ['./input.component.css']
        }),
        tslib_1.__metadata("design:paramtypes", [HttpClient,
            Router,
            InputService,
            ReportService,
            AppRoutingService,
            HomeService,
            ChangeDetectorRef])
    ], InputComponent);
    return InputComponent;
}());
export { InputComponent };
//# sourceMappingURL=input.component.js.map