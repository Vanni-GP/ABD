import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { FormsModule } from '@angular/forms'; 
import { StudentService } from '../../services/student';

@Component({
  selector: 'app-student-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './student-list.html', 
  styleUrl: './student-list.scss'    
})
export class StudentListComponent implements OnInit {
  // Control del menú dinámico: 'all' | 'search' | 'create' | 'edit' | 'delete'
  activeTab: string = 'all'; 

  studentsList: any[] = [];         // Para listar todos
  singleStudent: any = null;        // Para guardar el resultado de buscar por ID
  searchId: number | null = null;   // El ID que escribe el usuario para buscar/eliminar

  // Objeto para el formulario de Crear / Editar
  currentStudent: any = { id: null, name: '', lastname: '', email: '', courseId: null };

  constructor(
    private studentService: StudentService,
    private cdRef: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadAllStudents();
  }

  // Cambiar de pestaña en el menú
  switchTab(tabName: string): void {
    this.activeTab = tabName;
    this.singleStudent = null;
    this.searchId = null;
    if (tabName === 'all') {
      this.loadAllStudents();
    } else {
      this.clearForm();
    }
    this.cdRef.detectChanges();
  }

  // GET: Cargar todos
  loadAllStudents(): void {
    this.studentService.getStudents().subscribe({
      next: (data) => {
        this.studentsList = data;
        this.cdRef.detectChanges();
      },
      error: (err) => console.error('Error al cargar alumnos:', err)
    });
  }

  // GET por ID: Consultar alumno individual
  onSearch(): void {
    if (!this.searchId) return;
    this.studentService.getStudents().subscribe({
      next: (allStudents) => {
        const found = allStudents.find(s => s.id === this.searchId);
        this.singleStudent = found ? found : { error: true };
        this.cdRef.detectChanges();
      },
      error: (err) => {
        this.singleStudent = { error: true };
        this.cdRef.detectChanges();
      }
    });
  }

  // POST: Crear nuevo alumno
  onCreate(): void {
    this.studentService.saveStudent(this.currentStudent).subscribe({
      next: () => {
        alert('¡Alumno registrado con éxito, pa!');
        this.switchTab('all');
      },
      error: (err) => alert('Error al registrar alumno')
    });
  }

  // Cargar datos en el formulario para editar tras buscar por ID
  prepareEdit(): void {
    this.currentStudent = { ...this.singleStudent };
    this.activeTab = 'edit';
    this.cdRef.detectChanges();
  }

  // PUT: Modificar alumno
  onUpdate(): void {
    this.studentService.updateStudent(this.currentStudent.id, this.currentStudent).subscribe({
      next: () => {
        alert('¡Registro actualizado con éxito!');
        this.switchTab('all');
      },
      error: (err) => alert('Error al actualizar registro')
    });
  }

  // DELETE: Eliminar por ID
  onDelete(): void {
    if (!this.searchId) return;
    if (confirm(`¿Seguro que quieres eliminar al alumno con ID ${this.searchId}?`)) {
      this.studentService.deleteStudent(this.searchId).subscribe({
        next: () => {
          alert('Alumno eliminado correctamente.');
          this.switchTab('all');
        },
        error: (err) => alert('No se pudo eliminar. Verifica si el ID existe.')
      });
    }
  }

  clearForm(): void {
    this.currentStudent = { id: null, name: '', lastname: '', email: '', courseId: null };
  }
} // <-- ESTA ES LA ÚNICA LLAVE QUE DEBE CERRAR LA CLASE HASTA EL FINAL