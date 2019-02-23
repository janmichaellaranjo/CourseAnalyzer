import { Injectable } from '@angular/core';

@Injectable() 
export class HomeService {
    public studyPlan: File = null;
    public transitionalProvision: File = null;
    public finishedCourses: File = null;

    isMandatoryFilesSelected() : boolean {
        return this.studyPlan != null && this.finishedCourses != null;
    }
}