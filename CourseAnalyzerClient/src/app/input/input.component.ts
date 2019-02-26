import { Component, OnInit, ChangeDetectorRef, ElementRef } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

import { InputService } from './input.service';
import { ReportService } from '../report/report.service';

import { ViewChild } from '@angular/core';
import { HomeService } from '../home/home.service';

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.css']
})
export class InputComponent implements OnInit {
  isButtonDisabled: boolean;
  isStudyPlanFileSelected: boolean;
  isTransitionalProvisionFileSelected: boolean;
  isFinishedCoursesFileSelected: boolean;
  isStudyPlanFileCorrect: boolean;
  isTransitionalProvisionFileCorrect: boolean;
  isFinishedCoursesFileCorrect: boolean;
  studyPlanFilePath: string;
  transitionalProvisionFilePath: string;
  finishedCourseFilePath: string;
  studyPlanErrorMessage: string;
  transitionalProvisionErrorMessage: string;
  finishedCoursesErrorMessage: string;
  removable: boolean = true;

  INPUT_URL: string = "/input";
  private backEndUrl: string = 'http://localhost:8080/courseanalyzer';

  @ViewChild('studyPlanFileInput')
  studyPlanFileInput: ElementRef;

  @ViewChild('transitionalProvisionFileInput')
  transitionalProvisionFileInput: ElementRef;

  @ViewChild('finishedCoursesFileInput')
  finishedCoursesFileInput: ElementRef;

  constructor(
    private http: HttpClient,
    private router: Router,
    private inputService: InputService,
    private reportService: ReportService,
    private homeService: HomeService,
    private cdRef: ChangeDetectorRef) {
    this.isButtonDisabled = true;
  }

  ngOnInit(): void {
    this.router.events.subscribe((res) => {
      if (this.router.url == this.INPUT_URL) {

      }
    });
  }

  ngAfterViewInit() {
    if (this.inputService.studyPlan != null) {
      this.studyPlanFilePath = this.inputService.studyPlan.name;
      this.isStudyPlanFileCorrect = true;
      this.isStudyPlanFileSelected = true;
    }

    if (this.inputService.transitionalProvision != null) {
      this.transitionalProvisionFilePath = this.inputService.transitionalProvision.name;
      this.isTransitionalProvisionFileCorrect = true;
      this.isTransitionalProvisionFileSelected = true;
    }

    if (this.inputService.finishedCourses != null) {
      this.finishedCourseFilePath = this.inputService.finishedCourses.name;
      this.isFinishedCoursesFileCorrect = true;
      this.isFinishedCoursesFileSelected = true;
    }

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

            this.studyPlanErrorMessage = error;

            this.isStudyPlanFileSelected = true;
            this.isStudyPlanFileCorrect = false;
          },
          () => {
            this.inputService.studyPlan = file;
            this.isStudyPlanFileCorrect = true;
            this.isStudyPlanFileSelected = true;
            this.studyPlanFilePath = file.name;
            this.studyPlanErrorMessage = null;

            this.activateButton();
          }
        );
    } else {
      this.isStudyPlanFileSelected = true;
    }

    this.inputService.errorMsg = null;
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
          (data) => { },
          (error) => {
            console.error(error);

            this.transitionalProvisionErrorMessage = error;

            this.isTransitionalProvisionFileSelected = true;
            this.isTransitionalProvisionFileCorrect = false;
          },
          () => {
            this.inputService.transitionalProvision = file;
            this.transitionalProvisionFilePath = file.name;
            this.isTransitionalProvisionFileCorrect = true;
            this.isTransitionalProvisionFileSelected = true;

            this.activateButton();
          }
        );
    } else {
      this.isTransitionalProvisionFileSelected = true;
    }

    this.inputService.errorMsg = null;
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
          (data) => { },
          (error) => {
            console.error(error);

            this.finishedCoursesErrorMessage = error;

            this.isFinishedCoursesFileSelected = true;
            this.isFinishedCoursesFileCorrect = false;
          },
          () => {
            this.inputService.finishedCourses = file;
            this.finishedCourseFilePath = file.name;
            this.isFinishedCoursesFileCorrect = true;
            this.isFinishedCoursesFileSelected = true;
            this.activateButton();
          }
        );
    } else {
      this.isFinishedCoursesFileSelected = true;
    }

    this.inputService.errorMsg = null;
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
    this.reportService.isAccessible = true;
    this.homeService.disableRouterTab(false);

    this.router.navigate([this.homeService.REPORT_URL]);
  }

  removeStudyPlan() {
    this.inputService.studyPlan = null;
    this.studyPlanErrorMessage = null;
    this.isStudyPlanFileSelected = false;
    this.isStudyPlanFileCorrect = false;
    this.studyPlanFileInput.nativeElement.value = "";

    this.inputService.resetFile('studyPlanFile')
      .subscribe(
        () => { },
        (error) => {
          console.error(error);
          this.inputService.errorMsg = error;
        },
        () => { }
    );

    this.activateButton();
  }

  removeTransitionalProvision() {
    this.inputService.transitionalProvision = null;
    this.isTransitionalProvisionFileCorrect = false;
    this.isTransitionalProvisionFileSelected = false;
    this.transitionalProvisionFileInput.nativeElement.value = "";

    this.inputService.resetFile('transitionalProvisionFile')
      .subscribe(
        () => { },
        (error) => console.error(error),
        () => { }
      );
  }

  removeFinishedCourses() {
    this.inputService.finishedCourses = null;
    this.isFinishedCoursesFileCorrect = false;
    this.isFinishedCoursesFileSelected = false;
    this.finishedCoursesFileInput.nativeElement.value = "";

    this.inputService.resetFile('finishedCoursesFile')
      .subscribe(
        () => { },
        (error) => console.error(error),
        () => { }
      );

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
