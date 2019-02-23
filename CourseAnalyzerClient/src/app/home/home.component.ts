import { Component, OnInit, ChangeDetectorRef, ElementRef } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { HomeService } from './home.service'
import { NavComponent } from '../nav/nav.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  public isButtonDisabled: boolean = true;
  public isStudyPlanFileCorrect: boolean = false;
  public studyPlanFilePath: string;
  public transitionalProvisionFilePath: string;
  public finishedCourseFilePath: string;
  
  private backEndUrl: string = 'http://localhost:8080/courseanalyzer';

  constructor(
    private http: HttpClient,
    private homeService: HomeService,
    private navComponent: NavComponent,
    private cdRef:ChangeDetectorRef) { 
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
      ).subscribe(
        (jsonData) => {},
        (err) => console.error(err),
        () => {       
          this.homeService.studyPlan = file;
          this.isStudyPlanFileCorrect = true;
          this.studyPlanFilePath = file.name;
          this.activateButton();
        }
      );
    } else {
      this.homeService.studyPlan = null;
      this.studyPlanFilePath = '';
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
        catchError(this.handleError)
      ).subscribe(
        (jsonData) => {},
        (err) => console.error(err),
        () => {
          this.homeService.transitionalProvision = file;
          this.transitionalProvisionFilePath = file.name;
        }
      );
    } else {
      this.homeService.transitionalProvision = null;
      this.transitionalProvisionFilePath = '';
    }

    this.activateButton();
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
        (err) => console.error(err),
        () => {
          this.homeService.finishedCourses = file;
          this.finishedCourseFilePath = file.name;

          this.activateButton();
        }
      );
    } else {
      this.homeService.finishedCourses = null;
      this.finishedCourseFilePath = '';

      this.activateButton();
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

  private handleError(error: HttpErrorResponse) {
    //TODO: extract into service
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    }else if(error instanceof HttpErrorResponse) {
      return throwError(error.message);
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

  ngOnInit() {
  }

}
