import * as tslib_1 from "tslib";
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
var InputService = /** @class */ (function () {
    function InputService(http) {
        this.http = http;
        this.studyPlan = null;
        this.transitionalProvision = null;
        this.finishedCourses = null;
        this.backEndUrl = 'http://localhost:8080/courseanalyzer';
    }
    InputService.prototype.isMandatoryFilesSelected = function () {
        return this.studyPlan != null && this.finishedCourses != null;
    };
    InputService.prototype.isErrorMsgShown = function () {
        return this.errorMsg != null;
    };
    InputService.prototype.resetFile = function (fileName) {
        var url = this.backEndUrl + "/deleteSelectedFile/" + fileName;
        return this.http.delete(url)
            .pipe(catchError(this.handleError));
    };
    InputService.prototype.handleError = function (error) {
        //TODO: extract into service
        if (error.error instanceof ErrorEvent) {
            console.error('An error occurred:', error.error.message);
        }
        else if (error instanceof HttpErrorResponse) {
            return throwError(error.error.message);
        }
        else {
            console.error('Backend returned code ${error.status}, body was: ${error.error}');
        }
        return throwError('Something bad happened; please try again later.');
    };
    ;
    InputService = tslib_1.__decorate([
        Injectable(),
        tslib_1.__metadata("design:paramtypes", [HttpClient])
    ], InputService);
    return InputService;
}());
export { InputService };
//# sourceMappingURL=input.service.js.map