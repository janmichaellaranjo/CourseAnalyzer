import { Component, OnInit } from '@angular/core';
import { ReportService } from '../report.service';

@Component({
  selector: 'app-remaining-unassgined-courses-table',
  templateUrl: './remaining-unassgined-courses-table.component.html',
  styleUrls: ['./remaining-unassgined-courses-table.component.css']
})
export class RemainingUnassginedCoursesTableComponent implements OnInit {
  
  displayedColumns: string[] = ['ects','courseType', 'courseName'];

  constructor(private reportService: ReportService) { }

  ngOnInit() {
  }

}
