import { Injectable } from '@angular/core';
import { Router, NavigationStart } from '@angular/router';
import { HomeService } from './home/home.service';

@Injectable() 
export class AppRoutingService {

    constructor(private router: Router, private homeService: HomeService) {}
    
    navigateToPreviousVisitedTab() {
        if (this.homeService.previousTab != null) {
            this.router.navigate([this.homeService.previousTab]);
          } else {
            this.router.navigate([this.homeService.INPUT_URL]);
          }
    }

    maintainSameHomeTabWhenHomeClicked() {
        this.router.events.subscribe((event) => {
            if(event instanceof NavigationStart) {
              let navigationStartEvent = event;
      
              if (navigationStartEvent.url == "/") {
                if (this.homeService.previousTab == this.homeService.INPUT_URL) {
                  this.router.navigate([this.homeService.INPUT_URL]);
                } else if (this.homeService.previousTab == this.homeService.REPORT_URL) {
                  this.router.navigate([this.homeService.REPORT_URL]);
                }
              }
            }
          });
    }
}