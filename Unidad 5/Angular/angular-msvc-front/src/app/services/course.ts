import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  private apiUrl = 'http://localhost:8080/api/course';

  constructor(private http: HttpClient) {}

  // 1. Consultar todos (Funciona bien porque el backend regresa una lista JSON)
  getCourses(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/all`);
  }

  // 2. Crear un nuevo curso (POST) - Ajustado para ignorar la respuesta vacía (void)
  saveCourse(course: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/create`, course, { responseType: 'text' });
  }

  // 3. Actualizar un curso (PUT) - Ajustado porque el backend regresa un String plano
  updateCourse(id: number, course: any): Observable<string> {
    return this.http.put(`${this.apiUrl}/update/${id}`, course, { responseType: 'text' });
  }

  // 4. Eliminar un curso (DELETE) - Ajustado porque el backend regresa un String plano
  deleteCourse(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`, { responseType: 'text' });
  }
}