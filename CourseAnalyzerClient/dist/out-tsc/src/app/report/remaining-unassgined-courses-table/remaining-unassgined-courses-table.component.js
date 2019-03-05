import * as tslib_1 from "tslib";
import { Component } from '@angular/core';
import { ReportService } from '../report.service';
var RemainingUnassginedCoursesTableComponent = /** @class */ (function () {
    function RemainingUnassginedCoursesTableComponent(reportService) {
        this.reportService = reportService;
        this.displayedColumns = ['ects', 'courseType', 'courseName'];
    }
    RemainingUnassginedCoursesTableComponent.prototype.ngOnInit = function () {
    };
    RemainingUnassginedCoursesTableComponent = tslib_1.__decorate([
        Component({
            selector: 'app-remaining-unassgined-courses-table',
            templateUrl: './remaining-unassgined-courses-table.component.html',
            styleUrls: ['./remaining-unassgined-courses-table.component.css']
        }),
        tslib_1.__metadata("design:paramtypes", [ReportService])
    ], RemainingUnassginedCoursesTableComponent);
    return RemainingUnassginedCoursesTableComponent;
}());
export { RemainingUnassginedCoursesTableComponent };
//# sourceMappingURL=remaining-unassgined-courses-table.component.js.map