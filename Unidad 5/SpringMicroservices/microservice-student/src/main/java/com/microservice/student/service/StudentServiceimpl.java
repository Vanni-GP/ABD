package com.microservice.student.service;

import com.microservice.student.entities.student;
import com.microservice.student.persistence.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceimpl implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<student> findAll() {
        return (List<student>) studentRepository.findAll();
    }

    @Override
    public student findById(Long id) {
        // Usamos orElse(null) para que el controlador pueda manejar el Not Found
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public void save(student student) {
        studentRepository.save(student);
    }

    @Override
    public List<student> findAllStudentByCourse(Long courseId) {
        return studentRepository.findAllStudentByCourse(courseId);
    }

    // Este es el método que faltaba para corregir el error en el Controller
    @Override
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }
}