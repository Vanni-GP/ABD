package com.microservice.course;

import com.microservice.course.client.StudentClient;
import com.microservice.course.dto.StudentDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CourseCommunicationTest {

	@Autowired
	private StudentClient studentClient; // Inyectamos el cliente Feign REAL

	@Test
	void testFeignCommunicationWithStudentMicroservice() {
		// GIVEN: El ID de un curso que sabemos que tiene alumnos (o el ID 1)
		Long courseId = 1L;

		// WHEN: Llamamos al microservicio de Estudiantes a través de Feign
		// Nota: Asegúrate de que el método en tu StudentClient se llame findAllStudentByCourse
		List<StudentDTO> students = studentClient.findAllStudentByCourse(courseId);

		// THEN: Validamos la comunicación
		assertNotNull(students, "La respuesta no debería ser nula");

		// Si hay datos en tu DB de estudiantes, esta lista debería tener elementos
		System.out.println("Comunicación exitosa. Alumnos recibidos: " + students.size());
	}
}