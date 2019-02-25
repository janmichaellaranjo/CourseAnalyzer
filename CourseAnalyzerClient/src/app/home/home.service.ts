import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable() 
export class HomeService {
    studyPlan: File = null;
    transitionalProvision: File = null;
    finishedCourses: File = null;

    private backEndUrl: string = 'http://localhost:8080/courseanalyzer';

    constructor(private http: HttpClient) {}

    isMandatoryFilesSelected() : boolean {
        return this.studyPlan != null && this.finishedCourses != null;
    }

    resetFile(fileName: string) {
        
        let url = this.backEndUrl + "/deleteSelectedFile/" + fileName;

        return this.http.delete(url)
        .pipe(catchError(this.handleError));
    }

    handleError(error: HttpErrorResponse) {
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
}