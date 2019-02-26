import { Injectable } from '@angular/core';

@Injectable()
export class ReportService {
    isAccessible: boolean;

    constructor() {
        this.isAccessible = false;
    }
}