import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { ReportService } from './report.service';

  
@Injectable()
export class ReportAccessGuard implements CanActivate {
    constructor(private reportService: ReportService) {}
  
    canActivate(
      route: ActivatedRouteSnapshot,
      state: RouterStateSnapshot
    ): Observable<boolean>|Promise<boolean>|boolean {
      return this.reportService.isAccessible;
    }
  }