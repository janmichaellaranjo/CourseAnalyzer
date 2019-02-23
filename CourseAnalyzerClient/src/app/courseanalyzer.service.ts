import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CONSTANTS } from './courseanalyzer.module.ajs'

@Injectable()
export class CourseAnalyzerService {
    constructor(private http: HttpClient) {
    }

    uploadFileToUrl(file, uploadUrl): Promise<any> {
      var formdata = new FormData();
      formdata.append('file', file, file.name);
      return this.http.post(uploadUrl, formdata, {
        headers: { 'Content-Type': undefined }
      })
        .toPromise()
        .then(response => response);
    }

    compareCourses(): Promise<any> {
      return this.http.get(CONSTANTS.COMPARE_COURSES)
        .toPromise()
        .then(response => response);
    };

    closeApplication(): Promise<any> {
      return this.http.get(CONSTANTS.CLOSES_APPLICATION)
        .toPromise()
        .then(response => response);
    }
}
