import { Injectable } from '@angular/core';

@Injectable() 
export class HomeService {
  previousTab: string;

  constructor() {
    this.previousTab = null;
  }
}