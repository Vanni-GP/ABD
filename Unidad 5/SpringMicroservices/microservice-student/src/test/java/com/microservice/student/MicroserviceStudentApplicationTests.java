package com.microservice.student;

import com.microservice.student.entities.student;
import com.microservice.student.persistence.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // Carga el contexto completo de Spring
@Transactional   // Limpia los datos de la base de datos al finalizar el test
class StudentIntegrationTest {

	@Autowired
	private StudentRepository studentRepository; // Repositorio real conectado a MySQL

	@Test
	void testSaveAndFindStudentInRealDatabase() {
		// GIVEN: Creamos un objeto estudiante real
		student newStudent = new student(null, "Vanni", "García", "vanni@tec.com", 1L);

		// WHEN: Persistimos los datos en la base de datos real
		student savedStudent = studentRepository.save(newStudent);

		// THEN: Validamos que se generó un ID y que los datos coinciden
		assertNotNull(savedStudent.getId());

		student foundStudent = studentRepository.findById(savedStudent.getId()).orElse(null);
		assertNotNull(foundStudent);
		assertEquals("Vanni", foundStudent.getName());
	}
}