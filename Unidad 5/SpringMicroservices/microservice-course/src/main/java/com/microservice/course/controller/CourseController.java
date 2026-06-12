package com.microservice.course.controller;

import com.microservice.course.entities.Course;
import com.microservice.course.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private ICourseService courseService;

    // Ejercicio 5: Crear un nuevo curso
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCourse(@RequestBody Course course) {
        courseService.save(course);
    }

    // Listar todos los cursos (solo datos locales)
    @GetMapping("/all")
    public ResponseEntity<?> findAllCourses() {
        return ResponseEntity.ok(courseService.findAll());
    }

    // Ejercicio 6: Buscar curso por ID (solo datos locales)
    @GetMapping("/search/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.findById(id));
    }

    // Ejercicio 7: Actualizar un curso (PUT)
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody Course courseDetails) {
        Course courseExisting = courseService.findById(id);
        if (courseExisting != null) {
            courseExisting.setName(courseDetails.getName());
            courseExisting.setTeacher(courseDetails.getTeacher());
            courseService.save(courseExisting);
            return ResponseEntity.ok("Curso actualizado con éxito");
        }
        return ResponseEntity.notFound().build();
    }

    // Ejercicio 8: Eliminar un curso (DELETE)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        Course courseExisting = courseService.findById(id);
        if (courseExisting != null) {
            courseService.deleteById(id);
            return ResponseEntity.ok("Curso eliminado con éxito");
        }
        return ResponseEntity.notFound().build();
    }

    // --- NUEVO ENDPOINT DE MICROSERVICIOS ---
    // Este endpoint junta los datos del curso con los estudiantes del otro microservicio
    @GetMapping("/search-student/{courseId}")
    public ResponseEntity<?> findStudentsByIdCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.findStudentsByIdCourse(courseId));
    }
}