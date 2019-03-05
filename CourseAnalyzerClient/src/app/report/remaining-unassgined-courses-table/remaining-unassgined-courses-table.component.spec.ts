import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RemainingUnassginedCoursesTableComponent } from './remaining-unassgined-courses-table.component';

describe('RemainingUnassginedCoursesTableComponent', () => {
  let component: RemainingUnassginedCoursesTableComponent;
  let fixture: ComponentFixture<RemainingUnassginedCoursesTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RemainingUnassginedCoursesTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RemainingUnassginedCoursesTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
