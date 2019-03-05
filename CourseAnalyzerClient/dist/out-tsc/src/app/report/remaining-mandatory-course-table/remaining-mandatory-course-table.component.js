import * as tslib_1 from "tslib";
import { Component } from '@angular/core';
import { ReportService } from '../report.service';
var RemainingMandatoryCourseTableComponent = /** @class */ (function () {
    function RemainingMandatoryCourseTableComponent(reportService) {
        this.reportService = reportService;
        this.displayedColumns = ['ects', 'courseType', 'courseName'];
    }
    RemainingMandatoryCourseTableComponent.prototype.ngOnInit = function () {
    };
    RemainingMandatoryCourseTableComponent = tslib_1.__decorate([
        Component({
            selector: 'app-remaining-mandatory-course-table',
            templateUrl: './remaining-mandatory-course-table.component.html',
            styleUrls: ['./remaining-mandatory-course-table.component.css']
        }),
        tslib_1.__metadata("design:paramtypes", [ReportService])
    ], RemainingMandatoryCourseTableComponent);
    return RemainingMandatoryCourseTableComponent;
}());
export { RemainingMandatoryCourseTableComponent };
//# sourceMappingURL=remaining-mandatory-course-table.component.js.map