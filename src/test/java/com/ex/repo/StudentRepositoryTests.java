package com.ex.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ex.model.Student;

/**
 * Repository Layer Tests using @SpringBootTest
 * 
 * Tests database interactions directly with real database
 * - Full Spring context loaded
 * - Tests CRUD operations on StudentRepository
 * - Each test is isolated with @BeforeEach cleanup
 */
@SpringBootTest
class StudentRepositoryTests {

	@Autowired
	private StudentRepo studentRepo;

	@BeforeEach
	void setUp() {
		studentRepo.deleteAll();
	}

	@Test
	void testSaveStudent() {
		Student student = new Student();
		student.setName("John Doe");
		student.setEmail("john@example.com");

		Student saved = studentRepo.save(student);

		assertNotNull(saved.getId());
		assertEquals("John Doe", saved.getName());
	}

	@Test
	void testFindById() {
		Student student = new Student();
		student.setName("Jane Doe");
		student.setEmail("jane@example.com");
		Student saved = studentRepo.save(student);

		var retrieved = studentRepo.findById(saved.getId());

		assertTrue(retrieved.isPresent());
		assertEquals("Jane Doe", retrieved.get().getName());
	}

	@Test
	void testFindAll() {
		studentRepo.save(createStudent("Alice", "alice@example.com"));
		studentRepo.save(createStudent("Bob", "bob@example.com"));

		assertEquals(2, studentRepo.count());
	}

	@Test
	void testUpdateStudent() {
		Student student = new Student();
		student.setName("Original");
		student.setEmail("original@example.com");
		Student saved = studentRepo.save(student);

		saved.setName("Updated");
		studentRepo.save(saved);

		assertEquals("Updated", studentRepo.findById(saved.getId()).get().getName());
	}

	@Test
	void testDeleteStudent() {
		Student student = createStudent("Delete", "delete@example.com");
		Student saved = studentRepo.save(student);

		studentRepo.deleteById(saved.getId());

		assertTrue(studentRepo.findById(saved.getId()).isEmpty());
	}

	private Student createStudent(String name, String email) {
		Student student = new Student();
		student.setName(name);
		student.setEmail(email);
		return student;
	}
}
