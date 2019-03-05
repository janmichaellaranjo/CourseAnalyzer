import { Component, OnInit, ViewChild, ComponentFactoryResolver  } from '@angular/core';
import { ReportService } from './report.service';
import { ListDirective } from './list.directive';
import { RemainingMandatoryCourseTableComponent } from './remaining-mandatory-course-table/remaining-mandatory-course-table.component';
import { RemainingUnassginedCoursesTableComponent } from './remaining-unassgined-courses-table/remaining-unassgined-courses-table.component';
import { NgbDropdownConfig } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css'],
  providers: [NgbDropdownConfig]
})
export class ReportComponent implements OnInit {
  REMAINING_MANDATORY_COURSES_TABLE: string = "RemainingMandatoryCourseTableComponent";
  REMAINING_UNASSIGNED_COURSES_TABLE: string = "RemainingUnassginedCoursesTableComponent";
  displayedColumns: string[] = ['ects','courseType', 'courseName'];
  selectedTable: string;

  @ViewChild(ListDirective) listHost: ListDirective;

  constructor(
    private reportService: ReportService,
    private componentFactoryResolver: ComponentFactoryResolver) {
      if (this.reportService.selectedTable == null) {
        this.reportService.selectedTable = this.REMAINING_MANDATORY_COURSES_TABLE;
      }
    }

  ngOnInit() {
    this.loadTable();
  }

  ngAfterViewInit() {
  }

  selectRemainingMandatoryCourseTableComponent() {
    this.reportService.selectedTable = this.REMAINING_MANDATORY_COURSES_TABLE;
    this.loadTable();
  }

  selectRemainingUnassignedCourseTableComponent() {
    this.reportService.selectedTable = this.REMAINING_UNASSIGNED_COURSES_TABLE;
    this.loadTable();
  }

  loadTable() {
    let selectedTableComponent;
    if (this.reportService.selectedTable == this.REMAINING_MANDATORY_COURSES_TABLE) {
      selectedTableComponent = RemainingMandatoryCourseTableComponent;
    } else if (this.reportService.selectedTable == this.REMAINING_UNASSIGNED_COURSES_TABLE) {
      selectedTableComponent = RemainingUnassginedCoursesTableComponent;
    }

    let componentFactory = this.componentFactoryResolver.resolveComponentFactory(selectedTableComponent);

    let viewContainerRef = this.listHost.viewContainerRef;
    viewContainerRef.clear();

    viewContainerRef.createComponent(componentFactory);
  }
}
