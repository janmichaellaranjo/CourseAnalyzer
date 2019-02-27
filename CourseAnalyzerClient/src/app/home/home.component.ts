import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { HomeService } from './home.service';
import { keyframes } from '@angular/animations';
import { AppRoutingService } from '../app-routing.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  
  constructor(
    private router: Router,
     private homeService: HomeService,
     private appRoutingService: AppRoutingService) { 
  }

  ngOnInit(): void {

    this.appRoutingService.navigateToPreviousVisitedTab();

    this.router.events.subscribe(() => {
      if (this.router.url == this.homeService.INPUT_URL ||
          this.router.url == this.homeService.REPORT_URL) {
        this.homeService.previousTab = this.router.url;
      }
    });
  }
}