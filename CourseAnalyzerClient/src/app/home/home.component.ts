import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { HomeService } from './home.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  
  constructor(private router: Router, private homeService: HomeService) {
  }

  ngOnInit(): void {
    this.router.events.subscribe((res) => {
      if (this.router.url == this.homeService.INPUT_URL ||
          this.router.url == this.homeService.REPORT_URL) {
        this.homeService.previousTab = this.router.url;
      }
    });
  }

  ngAfterViewInit() {
    if (this.homeService.previousTab != null) {
      this.router.navigate([this.homeService.previousTab]);
    } else {
      this.router.navigate([this.homeService.INPUT_URL]);
    }
  }
}