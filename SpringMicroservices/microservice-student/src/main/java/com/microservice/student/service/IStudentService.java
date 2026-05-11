package com.microservice.student.service;

import com.microservice.student.entities.student;
import java.util.List;

public interface IStudentService {

    List<student> findAll();

    student findById(Long id);

    void save(student student);

    List<student> findAllStudentByCourse(Long courseId);

    // Agrega esta línea para corregir el error
    void deleteById(Long id);
}