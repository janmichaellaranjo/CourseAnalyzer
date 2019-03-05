import { async, TestBed } from '@angular/core/testing';
import { RemainingUnassginedCoursesTableComponent } from './remaining-unassgined-courses-table.component';
describe('RemainingUnassginedCoursesTableComponent', function () {
    var component;
    var fixture;
    beforeEach(async(function () {
        TestBed.configureTestingModule({
            declarations: [RemainingUnassginedCoursesTableComponent]
        })
            .compileComponents();
    }));
    beforeEach(function () {
        fixture = TestBed.createComponent(RemainingUnassginedCoursesTableComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });
    it('should create', function () {
        expect(component).toBeTruthy();
    });
});
//# sourceMappingURL=remaining-unassgined-courses-table.component.spec.js.map