import { Component, OnInit, ChangeDetectorRef, ElementRef, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

import { HomeService } from '../home/home.service';
import { InputService } from './input.service';
import { ReportService } from '../report/report.service';

import { ReportComponent } from '../report/report.component'

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.css']
})
export class InputComponent implements OnInit {
  isButtonDisabled: boolean;
  removable: boolean = true;

  private backEndUrl: string = 'http://localhost:8080/courseanalyzer';

  @ViewChild('studyPlanFileInput')
  studyPlanFileInput: ElementRef;

  @ViewChild('transitionalProvisionFileInput')
  transitionalProvisionFileInput: ElementRef;

  @ViewChild('finishedCoursesFileInput')
  finishedCoursesFileInput: ElementRef;

  constructor(
    private http: HttpClient,
    private inputService: InputService,
    private reportService: ReportService,
    private homeService: HomeService,
    private cdRef: ChangeDetectorRef) {
    this.isButtonDisabled = true;
  }

  ngOnInit(): void {
  }

  ngAfterViewInit() {
    this.activateButton();
    this.cdRef.detectChanges();
  }

  handleStudyPlanInput(files: FileList) {
    let file = files.item(0);

    if (file != null) {
      let url: string = this.backEndUrl + '/readStudyPlan';
      let formData: FormData = new FormData();
      let options = {};

      formData.append('studyPlan', file, file.name);

      console.log('The file ' + file.name + " is sent to " + url);

      this.http.post<File>(url, formData, options)
        .pipe(
          catchError(this.inputService.handleError)
        )
        .subscribe(
          () => { },
          (error) => {
            if (error != undefined) {
              console.error(error);
            }
            this.inputService.studyPlan = null;

            this.inputService.addStudyPlanErrorMsg(error);
            this.activateButton();
          },
          () => {
            this.inputService.studyPlan = file;
            
            this.inputService.removeStudyPlanErrorMsg();
            this.activateButton();
          }
        );
        
        this.activateButton();
    }
  }

  handleTransitionalProvisionInput(files: FileList) {
    let file = files.item(0);

    if (file != null) {
      let url: string = this.backEndUrl + '/readTransitionalProvision';
      let formData: FormData = new FormData();
      let options = {};

      formData.append('transitionalProvision', file, file.name);

      console.log('The file ' + file.name + " is sent to " + url);

      this.http.post<File>(url, formData, options)
        .pipe(
          catchError(this.inputService.handleError)
        )
        .subscribe(
          () => { },
          (error) => {
            console.error(error);

            this.inputService.transitionalProvision = null;

            this.inputService.addTransitionalProvisionErrorMsg(error);
          },
          () => {
            this.inputService.transitionalProvision = file;

            this.inputService.removeTransitionalProvisionErrorMsg();
          }
      );
    }
  }

  handleFinishedCoursesInput(files: FileList) {
    let file = files.item(0);

    if (file != null) {
      let url: string = this.backEndUrl + '/readFinishedCourseList';
      let formData: FormData = new FormData();
      let options = {};

      formData.append('finishedCourses', file, file.name);

      console.log('The file ' + file.name + " is sent to " + url);

      this.http.post<File>(url, formData, options)
        .pipe(
          catchError(this.inputService.handleError)).
        subscribe(
          () => { },
          (error) => {
            console.error(error);

            this.inputService.finishedCourses = null;

            this.inputService.addFinishedCoursesErrorMsg(error);
            this.activateButton();
          },
          () => {
            this.inputService.finishedCourses = file;

            this.inputService.removeFinishedCoursesErrorMsg();
            this.activateButton();
          }
      );
      this.activateButton();
    } 
  }

  compareCourses() {
    let url: string = this.backEndUrl + "/compareCourses";
    this.http.get(url)
      .pipe(catchError(this.inputService.handleError))
      .subscribe(
        (data) => {
          this.handleResult(data);
        },
        (error) => console.error(error),
        () => { }
      );
  }

  handleResult(data) {
    this.reportService.data = data;
    this.reportService.isAccessible = true;

    this.selectReportComponent();
  }

  selectReportComponent() {
    this.homeService.component = ReportComponent;
    this.homeService.selectedTabIndex = this.homeService.REPORT_INDEX;
    this.homeService.isReportDisabled = false;
    this.homeService.displayComponent();
  }

  removeStudyPlan() {
    this.inputService.studyPlan = null;
    this.studyPlanFileInput.nativeElement.value = "";

    this.inputService.resetFile('studyPlanFile')
      .subscribe(
        () => { },
        (error) => {
          console.error(error);
          this.inputService.addStudyPlanErrorMsg(error);
        },
        () => { }
    );

    this.inputService.removeStudyPlanErrorMsg();
    this.activateButton();
  }

  removeTransitionalProvision() {
    this.inputService.transitionalProvision = null;
    this.transitionalProvisionFileInput.nativeElement.value = "";

    this.inputService.resetFile('transitionalProvisionFile')
      .subscribe(
        () => { },
        (error) => {
          console.error(error);
          this.inputService.addTransitionalProvisionErrorMsg(error);
        },
        () => { }
      );

      this.inputService.removeTransitionalProvisionErrorMsg();
  }

  removeFinishedCourses() {
    this.inputService.finishedCourses = null;
    this.finishedCoursesFileInput.nativeElement.value = "";

    this.inputService.resetFile('finishedCoursesFile')
      .subscribe(
        () => { },
        (error) => {
          console.error(error);
          this.inputService.addFinishedCoursesErrorMsg(error);
        },
        () => { }
      );

    this.inputService.removeFinishedCoursesErrorMsg();
    this.activateButton();
  }

  activateButton() {
    if (this.inputService.isMandatoryFilesSelected()) {
      this.isButtonDisabled = false;
    } else {
      this.isButtonDisabled = true;
    }
  }
}
