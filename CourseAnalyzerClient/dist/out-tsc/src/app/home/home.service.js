import * as tslib_1 from "tslib";
import { Injectable } from '@angular/core';
var HomeService = /** @class */ (function () {
    function HomeService() {
        this.INPUT_INDEX = 0;
        this.REPORT_INDEX = 1;
        this.BASE_URL = "/";
        this.INPUT_URL = "/input";
        this.REPORT_URL = "/report";
        this.isReportDisabled = true;
        this.navLinks = [
            {
                label: 'Eingabe',
                link: [this.INPUT_URL],
                index: 0,
                isDisabled: false
            },
            {
                label: 'Ergebnis',
                link: [this.REPORT_URL],
                index: 1,
                isDisabled: true
            }
        ];
        this.previousTab = null;
    }
    HomeService.prototype.displayComponent = function () {
        var componentFactory = this.componentFactoryResolver.resolveComponentFactory(this.component);
        var viewContainerRef = this.componentHost.viewContainerRef;
        viewContainerRef.clear();
        viewContainerRef.createComponent(componentFactory);
    };
    HomeService.prototype.disableRouterTab = function (isDisabled) {
        this.navLinks[1].isDisabled = isDisabled;
    };
    HomeService = tslib_1.__decorate([
        Injectable(),
        tslib_1.__metadata("design:paramtypes", [])
    ], HomeService);
    return HomeService;
}());
export { HomeService };
//# sourceMappingURL=home.service.js.map