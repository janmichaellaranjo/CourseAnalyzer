import * as tslib_1 from "tslib";
import { Directive, ViewContainerRef } from '@angular/core';
var ListDirective = /** @class */ (function () {
    function ListDirective(viewContainerRef) {
        this.viewContainerRef = viewContainerRef;
    }
    ListDirective = tslib_1.__decorate([
        Directive({
            selector: '[appList]'
        }),
        tslib_1.__metadata("design:paramtypes", [ViewContainerRef])
    ], ListDirective);
    return ListDirective;
}());
export { ListDirective };
//# sourceMappingURL=list.directive.js.map