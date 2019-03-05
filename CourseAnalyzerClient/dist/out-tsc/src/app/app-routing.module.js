import * as tslib_1 from "tslib";
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { ContactComponent } from './contact/contact.component';
import { InputComponent } from './input/input.component';
import { ReportComponent } from './report/report.component';
import { ReportAccessGuard } from './report/reportaccessguard.service';
var routes = [
    {
        path: '',
        component: HomeComponent,
        children: [
            {
                path: 'input',
                component: InputComponent
            },
            {
                path: 'report',
                component: ReportComponent,
                canActivate: [ReportAccessGuard],
            }
        ]
    },
    {
        path: 'about',
        component: AboutComponent
    },
    {
        path: 'contact',
        component: ContactComponent
    }
];
var AppRoutingModule = /** @class */ (function () {
    function AppRoutingModule() {
    }
    AppRoutingModule = tslib_1.__decorate([
        NgModule({
            imports: [RouterModule.forRoot(routes)],
            exports: [RouterModule]
        })
    ], AppRoutingModule);
    return AppRoutingModule;
}());
export { AppRoutingModule };
//# sourceMappingURL=app-routing.module.js.map