import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpRequest  } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  public studyPlan: File = null;
  public transitionalProvision: File = null;
  public finishedCourses: File = null;
  public isButtonDisabled: boolean = true;
  private backEndUrl: string = 'http://localhost:8080/courseanalyzer';
  constructor(private http: HttpClient) { }
  
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
        () => this.studyPlan = file
      );
    }
    
    this.activateButton();
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
        () => this.transitionalProvision = file
      );
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
        catchError(this.handleError)
      ).subscribe(
        (jsonData) => {},
        (err) => console.error(err),
        () => this.finishedCourses = file
      );
    }
    this.activateButton();
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
    if (this.studyPlan != null && this.finishedCourses != null) {
      this.isButtonDisabled = false;
    } else {
      this.isButtonDisabled = true;
    }
  }

  ngOnInit() {
  }

}
