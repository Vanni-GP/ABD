package com.microservice.student.controller;

import com.microservice.student.entities.student;
import com.microservice.student.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
// <-- ROMPE EL BLOQUEO DE CORS
public class StudentController {

    @Autowired
    private IStudentService studentService;

    // Ejercicio 1: Guardar un estudiante
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveStudent(@RequestBody student student) {
        studentService.save(student);
    }

    // Ejercicio 2: Listar todos los estudiantes
    @GetMapping("/all")
    public ResponseEntity<?> findAllStudents() {
        return ResponseEntity.ok(studentService.findAll());
    }

    // Buscar estudiante por ID
    @GetMapping("/search/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.findById(id));
    }

    // Ejercicio 3: Actualizar estudiante
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody student studentDetails) {
        student studentExisting = studentService.findById(id);
        if (studentExisting != null) {
            studentExisting.setName(studentDetails.getName());
            studentExisting.setLastname(studentDetails.getLastname());
            studentExisting.setEmail(studentDetails.getEmail());
            studentService.save(studentExisting);
            return ResponseEntity.ok("Estudiante actualizado con éxito");
        }
        return ResponseEntity.notFound().build();
    }

    // Ejercicio 4: Eliminar estudiante
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        student studentExisting = studentService.findById(id);
        if (studentExisting != null) {
            studentService.deleteById(id);
            return ResponseEntity.ok("Estudiante eliminado con éxito");
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint clave: Buscar estudiantes por ID de curso
    @GetMapping("/search-by-course/{courseId}")
    public ResponseEntity<?> findAllStudentByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(studentService.findAllStudentByCourse(courseId));
    }
}