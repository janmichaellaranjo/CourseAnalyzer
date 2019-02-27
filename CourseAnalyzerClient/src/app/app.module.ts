import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { UpgradeModule } from '@angular/upgrade/static';
import { MatChipsModule, MatIconModule } from '@angular/material'
import { MatTabsModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

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
    ReportComponent
  ],
  imports: [
    BrowserModule,
    UpgradeModule,
    HttpClientModule,
    AppRoutingModule,
    MatChipsModule,
    MatIconModule,
    MatTabsModule,
    BrowserAnimationsModule
  ],
  providers: [
    NavComponent,
    AppRoutingService,
    HomeService,
    InputService,
    ReportAccessGuard,
    ReportService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { 
  constructor(private upgrade: UpgradeModule) { }

    ngDoBootstrap(){
        this.upgrade.bootstrap(document.documentElement, [moduleName], {strictDi: true});
    }
}
