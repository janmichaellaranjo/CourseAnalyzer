import * as tslib_1 from "tslib";
import { Injectable } from '@angular/core';
import { Router, NavigationStart } from '@angular/router';
import { HomeService } from './home/home.service';
var AppRoutingService = /** @class */ (function () {
    function AppRoutingService(router, homeService) {
        this.router = router;
        this.homeService = homeService;
    }
    AppRoutingService.prototype.navigateToPreviousVisitedTab = function () {
        if (this.homeService.previousTab != null) {
            this.router.navigate([this.homeService.previousTab]);
        }
        else {
            this.router.navigate([this.homeService.INPUT_URL]);
        }
    };
    AppRoutingService.prototype.maintainSameHomeTabWhenHomeClicked = function () {
        var _this = this;
        this.router.events.subscribe(function (event) {
            if (event instanceof NavigationStart) {
                var navigationStartEvent = event;
                if (navigationStartEvent.url == "/") {
                    if (_this.homeService.previousTab == _this.homeService.INPUT_URL) {
                        _this.router.navigate([_this.homeService.INPUT_URL]);
                    }
                    else if (_this.homeService.previousTab == _this.homeService.REPORT_URL) {
                        _this.router.navigate([_this.homeService.REPORT_URL]);
                    }
                }
            }
        });
    };
    AppRoutingService = tslib_1.__decorate([
        Injectable(),
        tslib_1.__metadata("design:paramtypes", [Router, HomeService])
    ], AppRoutingService);
    return AppRoutingService;
}());
export { AppRoutingService };
//# sourceMappingURL=app-routing.service.js.map