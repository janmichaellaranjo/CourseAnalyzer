import * as tslib_1 from "tslib";
import { Component, ViewChild, ComponentFactoryResolver } from '@angular/core';
import { ReportService } from './report.service';
import { ListDirective } from './list.directive';
import { RemainingMandatoryCourseTableComponent } from './remaining-mandatory-course-table/remaining-mandatory-course-table.component';
import { RemainingUnassginedCoursesTableComponent } from './remaining-unassgined-courses-table/remaining-unassgined-courses-table.component';
import { NgbDropdownConfig } from '@ng-bootstrap/ng-bootstrap';
var ReportComponent = /** @class */ (function () {
    function ReportComponent(reportService, componentFactoryResolver) {
        this.reportService = reportService;
        this.componentFactoryResolver = componentFactoryResolver;
        this.REMAINING_MANDATORY_COURSES_TABLE = "RemainingMandatoryCourseTableComponent";
        this.REMAINING_UNASSIGNED_COURSES_TABLE = "RemainingUnassginedCoursesTableComponent";
        this.displayedColumns = ['ects', 'courseType', 'courseName'];
        if (this.reportService.selectedTable == null) {
            this.reportService.selectedTable = this.REMAINING_MANDATORY_COURSES_TABLE;
        }
    }
    ReportComponent.prototype.ngOnInit = function () {
        this.loadTable();
    };
    ReportComponent.prototype.ngAfterViewInit = function () {
    };
    ReportComponent.prototype.selectRemainingMandatoryCourseTableComponent = function () {
        this.reportService.selectedTable = this.REMAINING_MANDATORY_COURSES_TABLE;
        this.loadTable();
    };
    ReportComponent.prototype.selectRemainingUnassignedCourseTableComponent = function () {
        this.reportService.selectedTable = this.REMAINING_UNASSIGNED_COURSES_TABLE;
        this.loadTable();
    };
    ReportComponent.prototype.loadTable = function () {
        var selectedTableComponent;
        if (this.reportService.selectedTable == this.REMAINING_MANDATORY_COURSES_TABLE) {
            selectedTableComponent = RemainingMandatoryCourseTableComponent;
        }
        else if (this.reportService.selectedTable == this.REMAINING_UNASSIGNED_COURSES_TABLE) {
            selectedTableComponent = RemainingUnassginedCoursesTableComponent;
        }
        var componentFactory = this.componentFactoryResolver.resolveComponentFactory(selectedTableComponent);
        var viewContainerRef = this.listHost.viewContainerRef;
        viewContainerRef.clear();
        viewContainerRef.createComponent(componentFactory);
    };
    tslib_1.__decorate([
        ViewChild(ListDirective),
        tslib_1.__metadata("design:type", ListDirective)
    ], ReportComponent.prototype, "listHost", void 0);
    ReportComponent = tslib_1.__decorate([
        Component({
            selector: 'app-report',
            templateUrl: './report.component.html',
            styleUrls: ['./report.component.css'],
            providers: [NgbDropdownConfig]
        }),
        tslib_1.__metadata("design:paramtypes", [ReportService,
            ComponentFactoryResolver])
    ], ReportComponent);
    return ReportComponent;
}());
export { ReportComponent };
//# sourceMappingURL=report.component.js.map