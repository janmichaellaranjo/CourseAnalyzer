import { Injectable } from '@angular/core';

@Injectable()
export class ReportService {
    isAccessible: boolean;
    data: any;
    selectedTable: string;
    
    constructor() {
        this.isAccessible = false;
    }
}