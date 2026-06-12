package com.microservice.student.persistence;

import com.microservice.student.entities.student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<student, Long> {

    // Query para buscar estudiantes por el ID del curso
    @Query("SELECT s FROM student s WHERE s.courseId = :courseId")
    List<student> findAllStudentByCourse(Long courseId);
}