import { Component, OnInit, ViewChild, ComponentFactoryResolver} from '@angular/core';
import { HomeService } from './home.service';
import { InputComponent } from '../input/input.component'
import { ReportComponent } from '../report/report.component'
import { HomeDirective } from './home.directive';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  @ViewChild(HomeDirective) componentHost: HomeDirective;

  constructor(
     private homeService: HomeService,
     private componentFactoryResolver: ComponentFactoryResolver) { 
       if (this.homeService.component == null) {
         this.homeService.component = InputComponent;
       }
       this.homeService.componentFactoryResolver = componentFactoryResolver;
  }

  ngOnInit(): void {
    this.homeService.componentHost = this.componentHost;
    this.homeService.displayComponent();
  }

  displaySelectedComponent($event) {
    if ($event.index == this.homeService.INPUT_INDEX) {
      this.selectInputComponent();
    } else if ($event.index == this.homeService.REPORT_INDEX) {
      this.selectReportComponent();
    }
    this.homeService.displayComponent();
  }

  private selectInputComponent() {
    this.homeService.component = InputComponent;
    this.homeService.selectedTabIndex = this.homeService.INPUT_INDEX;
  }

  private selectReportComponent() {
    this.homeService.component = ReportComponent;
    this.homeService.selectedTabIndex = this.homeService.REPORT_INDEX;
  }
}