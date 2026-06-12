import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { FormsModule } from '@angular/forms'; 
import { CourseService } from '../../services/course';

@Component({
  selector: 'app-course-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './course-list.html', 
  styleUrl: './course-list.scss'    
})
export class CourseListComponent implements OnInit {
  // Control del menú: 'all' | 'search' | 'create' | 'edit' | 'delete'
  activeTab: string = 'all'; 

  coursesList: any[] = [];          // Para listar todos
  singleCourse: any = null;         // Para guardar el resultado de buscar por ID
  searchId: number | null = null;   // El ID que escribe el usuario para buscar/eliminar

  // Objeto para el formulario de Cursos
  currentCourse: any = { id: null, name: '', teacher: '' };

  constructor(
    private courseService: CourseService,
    private cdRef: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadAllCourses();
  }

  // Cambiar de pestaña en el menú
  switchTab(tabName: string): void {
    this.activeTab = tabName;
    this.singleCourse = null;
    this.searchId = null;
    if (tabName === 'all') {
      this.loadAllCourses();
    } else {
      this.clearForm();
    }
    this.cdRef.detectChanges();
  }

  // GET: Cargar todos
  loadAllCourses(): void {
    this.courseService.getCourses().subscribe({
      next: (data) => {
        this.coursesList = data;
        this.cdRef.detectChanges();
      },
      error: (err) => console.error('Error al cargar cursos:', err)
    });
  }

  // GET por ID: Consultar materia individual
  onSearch(): void {
    if (!this.searchId) return;
    this.courseService.getCourses().subscribe({
      next: (allCourses) => {
        const found = allCourses.find(c => c.id === this.searchId);
        this.singleCourse = found ? found : { error: true };
        this.cdRef.detectChanges();
      },
      error: (err) => {
        this.singleCourse = { error: true };
        this.cdRef.detectChanges();
      }
    });
  }

  // POST: Crear nuevo curso
  onCreate(): void {
    this.courseService.saveCourse(this.currentCourse).subscribe({
      next: () => {
        alert('¡Curso registrado con éxito, pa!');
        this.switchTab('all');
      },
      error: (err) => alert('Error al registrar curso')
    });
  }

  // Cargar datos en el formulario para editar tras buscar por ID
  prepareEdit(): void {
    this.currentCourse = { ...this.singleCourse };
    this.activeTab = 'edit';
    this.cdRef.detectChanges();
  }

  // PUT: Modificar curso
  onUpdate(): void {
    this.courseService.updateCourse(this.currentCourse.id, this.currentCourse).subscribe({
      next: () => {
        alert('¡Curso actualizado con éxito!');
        this.switchTab('all');
      },
      error: (err) => alert('Error al actualizar curso')
    });
  }

  // DELETE: Eliminar curso por ID
  onDelete(): void {
    if (!this.searchId) return;
    if (confirm(`¿Seguro que quieres eliminar el curso con ID ${this.searchId}?`)) {
      this.courseService.deleteCourse(this.searchId).subscribe({
        next: () => {
          alert('Curso eliminado correctamente.');
          this.switchTab('all');
        },
        error: (err) => alert('No se pudo eliminar. Verifica si el ID existe en Postgres.')
      });
    }
  }

  clearForm(): void {
    this.currentCourse = { id: null, name: '', teacher: '' };
  }
}