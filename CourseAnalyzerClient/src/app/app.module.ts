import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { UpgradeModule } from '@angular/upgrade/static';
import { MatChipsModule, MatIconModule, MatTabsModule, MatTableModule } from '@angular/material'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import moduleName from './courseanalyzer.module.ajs';
import { AppComponent } from './app.component'
import { HomeComponent } from './home/home.component';
import { SelectComponent } from './select/select.component';
import { NavComponent } from './nav/nav.component';
import { AboutComponent } from './about/about.component';
import { ContactComponent } from './contact/contact.component';
import { LanguageComponent } from './language/language.component';

import { AppRoutingService } from './app-routing.service';
import { ReportAccessGuard } from './report/reportaccessguard.service';
import { HomeService } from './home/home.service';
import { InputService } from './input/input.service';
import { ReportService } from './report/report.service';

import { InputComponent } from './input/input.component';
import { ReportComponent } from './report/report.component';
import { RemainingMandatoryCourseTableComponent } from './report/remaining-mandatory-course-table/remaining-mandatory-course-table.component';
import { RemainingUnassginedCoursesTableComponent } from './report/remaining-unassgined-courses-table/remaining-unassgined-courses-table.component';
import { ListDirective } from './report/list.directive';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SelectComponent,
    NavComponent,
    AboutComponent,
    ContactComponent,
    LanguageComponent,
    InputComponent,
    ReportComponent,
    RemainingMandatoryCourseTableComponent,
    RemainingUnassginedCoursesTableComponent,
    ListDirective,
  ],
  imports: [
    BrowserModule,
    UpgradeModule,
    HttpClientModule,
    AppRoutingModule,
    MatChipsModule,
    MatIconModule,
    MatTabsModule,
    MatTableModule,
    BrowserAnimationsModule,
    NgbModule.forRoot()
  ],
  providers: [
    NavComponent,
    AppRoutingService,
    HomeService,
    InputService,
    ReportAccessGuard,
    ReportService
  ],
  entryComponents: [RemainingMandatoryCourseTableComponent, RemainingUnassginedCoursesTableComponent],
  bootstrap: [AppComponent]
})
export class AppModule { 
  constructor(private upgrade: UpgradeModule) { }

    ngDoBootstrap(){
        this.upgrade.bootstrap(document.documentElement, [moduleName], {strictDi: true});
    }
}
