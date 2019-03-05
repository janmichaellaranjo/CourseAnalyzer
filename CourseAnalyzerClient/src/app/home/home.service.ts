import { Injectable, ComponentFactoryResolver } from '@angular/core';
import { HomeDirective } from './home.directive';

@Injectable() 
export class HomeService {
  selectedTabIndex: number;
  INPUT_INDEX = 0;
  REPORT_INDEX = 1;
  isReportDisabled: boolean = true;
  component: any;
  componentFactoryResolver: ComponentFactoryResolver;
  componentHost: HomeDirective;

  constructor() {
  }

  displayComponent() {

    let componentFactory = this.componentFactoryResolver.resolveComponentFactory(this.component);

    let viewContainerRef = this.componentHost.viewContainerRef;
    viewContainerRef.clear();

    viewContainerRef.createComponent(componentFactory);
  }
}