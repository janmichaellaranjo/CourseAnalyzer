import * as tslib_1 from "tslib";
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CONSTANTS } from './courseanalyzer.module.ajs';
var CourseAnalyzerService = /** @class */ (function () {
    function CourseAnalyzerService(http) {
        this.http = http;
    }
    CourseAnalyzerService.prototype.uploadFileToUrl = function (file, uploadUrl) {
        var formdata = new FormData();
        formdata.append('file', file, file.name);
        return this.http.post(uploadUrl, formdata, {
            headers: { 'Content-Type': undefined }
        })
            .toPromise()
            .then(function (response) { return response; });
    };
    CourseAnalyzerService.prototype.compareCourses = function () {
        return this.http.get(CONSTANTS.COMPARE_COURSES)
            .toPromise()
            .then(function (response) { return response; });
    };
    ;
    CourseAnalyzerService.prototype.closeApplication = function () {
        return this.http.get(CONSTANTS.CLOSES_APPLICATION)
            .toPromise()
            .then(function (response) { return response; });
    };
    CourseAnalyzerService = tslib_1.__decorate([
        Injectable(),
        tslib_1.__metadata("design:paramtypes", [HttpClient])
    ], CourseAnalyzerService);
    return CourseAnalyzerService;
}());
export { CourseAnalyzerService };
//# sourceMappingURL=courseanalyzer.service.js.map