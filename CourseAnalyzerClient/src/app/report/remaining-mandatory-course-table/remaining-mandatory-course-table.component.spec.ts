import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RemainingMandatoryCourseTableComponent } from './remaining-mandatory-course-table.component';

describe('RemainingMandatoryCourseTableComponent', () => {
  let component: RemainingMandatoryCourseTableComponent;
  let fixture: ComponentFixture<RemainingMandatoryCourseTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RemainingMandatoryCourseTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RemainingMandatoryCourseTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
