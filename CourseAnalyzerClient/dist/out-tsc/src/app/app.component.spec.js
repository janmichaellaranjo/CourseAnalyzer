import { TestBed, async } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HomeComponent } from './home/home.component';
describe('AppComponent', function () {
    beforeEach(async(function () {
        TestBed.configureTestingModule({
            imports: [
                RouterTestingModule
            ],
            declarations: [
                HomeComponent
            ],
        }).compileComponents();
    }));
    it('should create the app', function () {
        var fixture = TestBed.createComponent(HomeComponent);
        var app = fixture.debugElement.componentInstance;
        expect(app).toBeTruthy();
    });
    it("should have as title 'CourseAnalyzer'", function () {
        var fixture = TestBed.createComponent(HomeComponent);
        var app = fixture.debugElement.componentInstance;
        expect(app.title).toEqual('CourseAnalyzer');
    });
    it('should render title in a h1 tag', function () {
        var fixture = TestBed.createComponent(HomeComponent);
        fixture.detectChanges();
        var compiled = fixture.debugElement.nativeElement;
        expect(compiled.querySelector('h1').textContent).toContain('Welcome to CourseAnalyzer!');
    });
});
//# sourceMappingURL=app.component.spec.js.map