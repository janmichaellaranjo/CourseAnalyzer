import { async, TestBed } from '@angular/core/testing';
import { RemainingMandatoryCourseTableComponent } from './remaining-mandatory-course-table.component';
describe('RemainingMandatoryCourseTableComponent', function () {
    var component;
    var fixture;
    beforeEach(async(function () {
        TestBed.configureTestingModule({
            declarations: [RemainingMandatoryCourseTableComponent]
        })
            .compileComponents();
    }));
    beforeEach(function () {
        fixture = TestBed.createComponent(RemainingMandatoryCourseTableComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });
    it('should create', function () {
        expect(component).toBeTruthy();
    });
});
//# sourceMappingURL=remaining-mandatory-course-table.component.spec.js.map