import * as tslib_1 from "tslib";
import { Component, ViewChild, ComponentFactoryResolver } from '@angular/core';
import { Router } from '@angular/router';
import { HomeService } from './home.service';
import { InputComponent } from '../input/input.component';
import { ReportComponent } from '../report/report.component';
import { AppRoutingService } from '../app-routing.service';
import { HomeDirective } from './home.directive';
var HomeComponent = /** @class */ (function () {
    function HomeComponent(router, homeService, appRoutingService, componentFactoryResolver) {
        this.router = router;
        this.homeService = homeService;
        this.appRoutingService = appRoutingService;
        this.componentFactoryResolver = componentFactoryResolver;
        if (this.homeService.previousTab == null) {
            this.homeService.previousTab = this.homeService.INPUT_URL;
            this.homeService.selectedTabIndex = this.homeService.INPUT_INDEX;
        }
        this.homeService.componentFactoryResolver = componentFactoryResolver;
    }
    HomeComponent.prototype.ngOnInit = function () {
        this.homeService.componentHost = this.componentHost;
        this.displayComponent();
    };
    HomeComponent.prototype.displaySelectedComponent = function ($event) {
        if ($event.index == this.homeService.INPUT_INDEX) {
            this.homeService.previousTab = this.homeService.INPUT_URL;
        }
        else if ($event.index == this.homeService.REPORT_INDEX) {
            this.homeService.previousTab = this.homeService.REPORT_URL;
        }
        this.displayComponent();
    };
    HomeComponent.prototype.displayComponent = function () {
        //let component;
        if (this.homeService.previousTab == this.homeService.INPUT_URL) {
            this.homeService.component = InputComponent;
            this.homeService.selectedTabIndex = this.homeService.INPUT_INDEX;
        }
        else if (this.homeService.previousTab == this.homeService.REPORT_URL) {
            this.homeService.component = ReportComponent;
            this.homeService.selectedTabIndex = this.homeService.REPORT_INDEX;
        }
        this.homeService.displayComponent();
        /*let componentFactory = this.componentFactoryResolver.resolveComponentFactory(component);
    
        let viewContainerRef = this.componentHost.viewContainerRef;
        viewContainerRef.clear();
    
        viewContainerRef.createComponent(componentFactory);*/
    };
    tslib_1.__decorate([
        ViewChild(HomeDirective),
        tslib_1.__metadata("design:type", HomeDirective)
    ], HomeComponent.prototype, "componentHost", void 0);
    HomeComponent = tslib_1.__decorate([
        Component({
            selector: 'app-home',
            templateUrl: './home.component.html',
            styleUrls: ['./home.component.css']
        }),
        tslib_1.__metadata("design:paramtypes", [Router,
            HomeService,
            AppRoutingService,
            ComponentFactoryResolver])
    ], HomeComponent);
    return HomeComponent;
}());
export { HomeComponent };
//# sourceMappingURL=home.component.js.map