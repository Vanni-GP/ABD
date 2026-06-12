import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { StudentListComponent } from './components/student-list/student-list';
import { CourseListComponent } from './components/course-list/course-list'; // Nueva importación

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, StudentListComponent, CourseListComponent], // Registramos ambos componentes
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('angular-msvc-front');
}