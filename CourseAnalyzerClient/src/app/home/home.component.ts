import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { HomeService } from './home.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  navLinks: any[];
  private INPUT_URL: string = "/input";
  private REPORT_URL: string = "/report";
  
  constructor(private router: Router, private homeService: HomeService) {
    this.navLinks = [
      {
          label: 'Eingabe',
          link: [this.INPUT_URL],
          index: 0
      }, 
      {
          label: 'Ergebnis',
          link: ['/report'],
          index: 1
      }];
  }

  ngOnInit(): void {
    this.router.events.subscribe((res) => {
      if (this.router.url == this.INPUT_URL ||
          this.router.url == this.REPORT_URL) {
        this.homeService.previousTab = this.router.url;
      }
    });
  }

  ngAfterViewInit() {
    if (this.homeService.previousTab != null) {
      this.router.navigate([this.homeService.previousTab]);
    }
  }
}