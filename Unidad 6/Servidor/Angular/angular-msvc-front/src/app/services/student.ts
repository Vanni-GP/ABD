import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StudentService {
private apiUrl = 'http://vannisystem.local/api/student';
  constructor(private http: HttpClient) {}

  // 1. Consultar todos (Regresa la lista JSON de MySQL correctamente)
  getStudents(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/all`);
  }

  // 2. Crear un nuevo estudiante (POST) - Configurado para recibir respuesta vacía
  saveStudent(student: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/save`, student, { responseType: 'text' });
  }

  // 3. Actualizar un estudiante (PUT) - Configurado para procesar la frase de éxito
  updateStudent(id: number, student: any): Observable<string> {
    return this.http.put(`${this.apiUrl}/update/${id}`, student, { responseType: 'text' });
  }

  // 4. Eliminar un estudiante (DELETE) - Configurado para procesar la frase de éxito
  deleteStudent(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`, { responseType: 'text' });
  }
}