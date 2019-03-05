import * as tslib_1 from "tslib";
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HomeService } from '../home/home.service';
import { ReportService } from './report.service';
var ReportAccessGuard = /** @class */ (function () {
    function ReportAccessGuard(router, reportService, homeService) {
        this.router = router;
        this.reportService = reportService;
        this.homeService = homeService;
    }
    ReportAccessGuard.prototype.canActivate = function (route, state) {
        if (!this.reportService.isAccessible) {
            this.router.navigate([this.homeService.INPUT_URL]);
        }
        return this.reportService.isAccessible;
    };
    ReportAccessGuard = tslib_1.__decorate([
        Injectable(),
        tslib_1.__metadata("design:paramtypes", [Router,
            ReportService,
            HomeService])
    ], ReportAccessGuard);
    return ReportAccessGuard;
}());
export { ReportAccessGuard };
//# sourceMappingURL=reportaccessguard.service.js.map