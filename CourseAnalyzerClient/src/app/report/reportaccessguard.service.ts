import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HomeService } from '../home/home.service'
import { ReportService } from './report.service';

  
@Injectable()
export class ReportAccessGuard implements CanActivate {
    constructor(
      private router:Router, 
      private reportService: ReportService,
      private homeService: HomeService) {}
  
    canActivate(
      route: ActivatedRouteSnapshot,
      state: RouterStateSnapshot
    ): Observable<boolean>|Promise<boolean>|boolean {

      if (!this.reportService.isAccessible) {
        this.router.navigate([this.homeService.INPUT_URL]);
      }

      return this.reportService.isAccessible;
    }
  }