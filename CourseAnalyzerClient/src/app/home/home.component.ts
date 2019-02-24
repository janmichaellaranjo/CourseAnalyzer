import { Component, OnInit, ChangeDetectorRef, ElementRef } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { HomeService } from './home.service'

import { ViewChild } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
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

  private backEndUrl: string = 'http://localhost:8080/courseanalyzer';

  @ViewChild('studyPlanFileInput')
  studyPlanFileInput: ElementRef;

  @ViewChild('transitionalProvisionFileInput')
  transitionalProvisionFileInput: ElementRef;

  @ViewChild('finishedCoursesFileInput')
  finishedCoursesFileInput: ElementRef;

  constructor(
    private http: HttpClient,
    private homeService: HomeService,
    private cdRef:ChangeDetectorRef) {
      this.isButtonDisabled = true;
      this.isStudyPlanFileCorrect = false; 
  }

  ngOnInit() {
  }

  ngAfterViewInit() {
    if (this.homeService.studyPlan != null) {
      this.studyPlanFilePath = this.homeService.studyPlan.name;
    }
    
    if (this.homeService.transitionalProvision != null) {
      this.transitionalProvisionFilePath = this.homeService.transitionalProvision.name;
    }
    
    if (this.homeService.finishedCourses != null) {
      this.finishedCourseFilePath = this.homeService.finishedCourses.name;
    }

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
        catchError(this.handleError)
      )
      .subscribe(
        (jsonData) => {},
        (err) => {
          console.error(err);

          this.studyPlanErrorMessage = err;

          this.isStudyPlanFileSelected = true;
          this.isStudyPlanFileCorrect = false;
        },
        () => {       
          this.homeService.studyPlan = file;
          this.isStudyPlanFileCorrect = true;
          this.isStudyPlanFileSelected = true;
          this.studyPlanFilePath = file.name;
          this.activateButton();
        }
      );
    } else {
      this.isStudyPlanFileSelected = true;
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
        catchError(this.handleError)
      )
      .subscribe(
        (data) => {},
        (err) => {
          console.error(err);

          this.transitionalProvisionErrorMessage = err;

          this.isTransitionalProvisionFileSelected = true;
          this.isTransitionalProvisionFileCorrect = false;
        },
        () => {
          this.homeService.transitionalProvision = file;
          this.transitionalProvisionFilePath = file.name;
          this.isTransitionalProvisionFileCorrect = true;
          this.isTransitionalProvisionFileSelected = true;

          this.activateButton();
        }
      );
    } else {
      this.isTransitionalProvisionFileSelected = true;
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
        catchError(this.handleError)).
        subscribe(
        (data) => {},
        (err) => {
          console.error(err);

          this.finishedCoursesErrorMessage = err;
          
          this.isFinishedCoursesFileSelected = true;
          this.isFinishedCoursesFileCorrect = false;
        },
        () => {
          this.homeService.finishedCourses = file;
          this.finishedCourseFilePath = file.name;
          this.isFinishedCoursesFileCorrect = true;
          this.isFinishedCoursesFileSelected = true;
          this.activateButton();
        }
      );
    } else {
      this.isFinishedCoursesFileSelected = true;
    }
  }

  compareCourses() {
    let url: string = this.backEndUrl + "/compareCourses";
    this.http.get(url)
    .pipe(catchError(this.handleError))
    .subscribe(
      (data) =>{
        this.handleResult(data);
      },
      (err) => console.error(err),
      () => {}
    );
  }

  handleResult(data) {
    
  }

  removeStudyPlan() {
      this.homeService.studyPlan = null;
      this.isStudyPlanFileSelected = false;
      this.isStudyPlanFileCorrect = false;
      this.studyPlanFileInput.nativeElement.value = "";
  }

  removeTransitionalProvision() {
      this.homeService.transitionalProvision = null;
      this.isTransitionalProvisionFileCorrect = false;
      this.isTransitionalProvisionFileSelected = false;
      this.transitionalProvisionFileInput.nativeElement.value = "";
  }

  removeFinishedCourses() {
      this.homeService.finishedCourses = null;
      this.isFinishedCoursesFileCorrect = false;
      this.isFinishedCoursesFileSelected = false;
      this.finishedCoursesFileInput.nativeElement.value = "";
  }

  private handleError(error: HttpErrorResponse) {
    //TODO: extract into service
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    }else if(error instanceof HttpErrorResponse) {
      return throwError(error.error.message);
    } else {
      console.error('Backend returned code ${error.status}, body was: ${error.error}');
    }
    return throwError('Something bad happened; please try again later.');
  };

  activateButton() {
    if (this.homeService.isMandatoryFilesSelected()) {
      this.isButtonDisabled = false;
    } else {
      this.isButtonDisabled = true;
    }
  }

}
