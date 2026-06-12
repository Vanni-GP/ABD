import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { StudentListComponent } from './student-list';
import { StudentService } from '../../services/student';

describe('StudentListComponent', () => {
  let component: StudentListComponent;
  let fixture: ComponentFixture<StudentListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StudentListComponent],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        StudentService
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(StudentListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges(); // Dispara el ciclo de vida inicial (ngOnInit)
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});