import { Injectable } from '@angular/core';

@Injectable()
export class ReportService {
    isAccessible: boolean;
    data: any;

    constructor() {
        this.isAccessible = false;
    }
}