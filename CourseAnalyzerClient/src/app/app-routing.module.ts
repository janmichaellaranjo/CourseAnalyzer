import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { ContactComponent } from './contact/contact.component';
import { InputComponent } from './input/input.component';
import { ReportComponent } from './report/report.component'

const routes: Routes = [
  { 
    path: '',
    component: HomeComponent,
    children : [
      { 
        path: 'input',
        component: InputComponent 
      },
      { 
        path: 'report',
        component: ReportComponent 
      } 
  ] 
  },
  { 
    path: 'about', 
    component: AboutComponent 
  },
  { 
    path: 'contact',
    component: ContactComponent 
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
