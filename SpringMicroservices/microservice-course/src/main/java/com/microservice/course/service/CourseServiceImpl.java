package com.microservice.course.service;

import com.microservice.course.client.StudentClient;
import com.microservice.course.dto.StudentDTO;
import com.microservice.course.entities.Course;
import com.microservice.course.http.response.StudentByCourseResponse;
import com.microservice.course.persistence.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private ICourseRepository courseRepository;

    @Autowired
    private StudentClient studentClient; // Inyección del cliente Feign

    @Override
    public List<Course> findAll() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    public Course findById(Long id) {
        // Cambiado a orElse(null) para facilitar la validación en el Controller
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }

    // Método implementado para corregir el error en el Controller y completar el Ejercicio 8
    @Override
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public StudentByCourseResponse findStudentsByIdCourse(Long courseId) {
        // 1. Consultar el curso en la base de datos local (PostgreSQL)
        Course course = courseRepository.findById(courseId).orElse(new Course());

        // 2. Consultar los estudiantes en el microservicio msvc-student mediante Feign
        List<StudentDTO> studentDTOList = studentClient.findAllStudentByCourse(courseId);

        // 3. Retornar la respuesta unificada (Curso + Lista de Alumnos)
        return StudentByCourseResponse.builder()
                .name(course.getName())
                .teacher(course.getTeacher())
                .studentDTOList(studentDTOList)
                .build();
    }
}