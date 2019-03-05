import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';


@Injectable() 
export class InputService {
    studyPlan: File = null;
    transitionalProvision: File = null;
    finishedCourses: File = null;
    errorMsgs: Array<string>;
    studyPlanErrorMsg: string;
    transitionalProvisionErrorMsg: string;
    finishedCoursesErrorMsg: string;

    private backEndUrl: string = 'http://localhost:8080/courseanalyzer';

    constructor(private http: HttpClient) {
      this.errorMsgs = new Array<string>();
    }

    isMandatoryFilesSelected() : boolean {
        return this.studyPlan != null && this.finishedCourses != null;
    }

    isErrorMsgsShown(): boolean {
      return this.errorMsgs.length > 0;
    }

    addStudyPlanErrorMsg(errorMsg: string) {
      this.studyPlanErrorMsg = errorMsg;
      this.errorMsgs.push(errorMsg);
    }

    addTransitionalProvisionErrorMsg(errorMsg: string) {
      this.transitionalProvisionErrorMsg = errorMsg;
      this.errorMsgs.push(errorMsg);
    }

    addFinishedCoursesErrorMsg(errorMsg: string) {
      this.finishedCoursesErrorMsg = errorMsg;
      this.errorMsgs.push(errorMsg);
    }

    removeStudyPlanErrorMsg() {
      if (this.errorMsgs.indexOf(this.studyPlanErrorMsg) > -1) {
        let id = this.errorMsgs.indexOf(this.studyPlanErrorMsg);
        this.errorMsgs.splice(id, 1);

        this.studyPlanErrorMsg = null;
      }
    }

    removeTransitionalProvisionErrorMsg() {
      if (this.errorMsgs.indexOf(this.transitionalProvisionErrorMsg) > -1) {
        let id = this.errorMsgs.indexOf(this.transitionalProvisionErrorMsg);
        this.errorMsgs.splice(id, 1);

        this.transitionalProvisionErrorMsg = null;
      }
    }

    removeFinishedCoursesErrorMsg() {
      if (this.errorMsgs.indexOf(this.finishedCoursesErrorMsg) > -1) {
        let id = this.errorMsgs.indexOf(this.finishedCoursesErrorMsg);
        this.errorMsgs.splice(id, 1);

        this.finishedCoursesErrorMsg = null;
      }
    }

    resetFile(fileName: string) {
        
        let url = this.backEndUrl + "/deleteSelectedFile/" + fileName;

        return this.http.delete(url)
        .pipe(catchError(this.handleError));
    }

    handleError(error: HttpErrorResponse) {
        if (error.error instanceof ErrorEvent) {
          console.error('An error occurred:', error.error.message);
        }else if(error instanceof HttpErrorResponse) {
          return throwError(error.error.message);
        } else {
          console.error('Backend returned code ${error.status}, body was: ${error.error}');
        }
        return throwError('Something bad happened; please try again later.');
    };
}