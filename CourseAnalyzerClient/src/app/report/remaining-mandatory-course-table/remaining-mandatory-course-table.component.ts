import { Component, OnInit } from '@angular/core';
import { ReportService } from '../report.service';

@Component({
  selector: 'app-remaining-mandatory-course-table',
  templateUrl: './remaining-mandatory-course-table.component.html',
  styleUrls: ['./remaining-mandatory-course-table.component.css']
})
export class RemainingMandatoryCourseTableComponent implements OnInit {

  displayedColumns: string[] = ['ects','courseType', 'courseName'];

  constructor(private reportService: ReportService) { }

  ngOnInit() {
  }

}
