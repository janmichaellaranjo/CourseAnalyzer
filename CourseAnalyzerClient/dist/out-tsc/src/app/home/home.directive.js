import * as tslib_1 from "tslib";
import { Directive, ViewContainerRef } from '@angular/core';
var HomeDirective = /** @class */ (function () {
    function HomeDirective(viewContainerRef) {
        this.viewContainerRef = viewContainerRef;
    }
    HomeDirective = tslib_1.__decorate([
        Directive({
            selector: '[appHome]'
        }),
        tslib_1.__metadata("design:paramtypes", [ViewContainerRef])
    ], HomeDirective);
    return HomeDirective;
}());
export { HomeDirective };
//# sourceMappingURL=home.directive.js.map