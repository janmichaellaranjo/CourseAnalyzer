import { Injectable } from '@angular/core';

@Injectable() 
export class HomeService {
  navLinks: any[];
  previousTab: string;
  BASE_URL: string = "/";
  INPUT_URL: string = "/input";
  REPORT_URL: string = "/report";
  constructor() {
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
      }];
    this.previousTab = null;
  }

  disableRouterTab(isDisabled: boolean) {
    this.navLinks[1].isDisabled = isDisabled;
  }
}