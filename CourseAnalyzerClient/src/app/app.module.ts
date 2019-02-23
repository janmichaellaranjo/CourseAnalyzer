import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { UpgradeModule } from '@angular/upgrade/static';

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

import { HomeService } from './home/home.service'

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SelectComponent,
    NavComponent,
    AboutComponent,
    ContactComponent,
    LanguageComponent
  ],
  imports: [
    BrowserModule,
    UpgradeModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [
    HomeService,
    NavComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { 
  constructor(private upgrade: UpgradeModule) { }

    ngDoBootstrap(){
        this.upgrade.bootstrap(document.documentElement, [moduleName], {strictDi: true});
    }
}
