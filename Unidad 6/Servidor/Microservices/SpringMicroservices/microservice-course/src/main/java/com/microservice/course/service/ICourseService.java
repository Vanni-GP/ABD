package com.microservice.course.service;

import com.microservice.course.entities.Course;
import com.microservice.course.http.response.StudentByCourseResponse;
import java.util.List;

public interface ICourseService {

    List<Course> findAll();

    Course findById(Long id);

    void save(Course course);

    // Este es el nuevo método para la comunicación con el microservicio Student
    StudentByCourseResponse findStudentsByIdCourse(Long courseId);

    // Método necesario para completar el CRUD (Ejercicio 8 del reporte)
    void deleteById(Long id);
}