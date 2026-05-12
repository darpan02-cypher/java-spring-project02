package com.ex.student;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ex.model.Student;
import com.ex.repo.StudentRepo;
import com.ex.service.StudentService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentApplicationTests {

	@Autowired
	private StudentService studentService;

	@Autowired
	private StudentRepo studentRepo;

	@BeforeEach
	void setUp() {
		studentRepo.deleteAll();
	}

	// ==================== Student Service Tests ====================

	@Test
	void testCreateStudent() {
		// Create a new student
		Student student = new Student();
		student.setName("John Doe");
		student.setEmail("john@example.com");

		// Save to database
		Student saved = studentRepo.save(student);

		// Assertions
		assertNotNull(saved);
		assertNotNull(saved.getId());
		assertEquals("John Doe", saved.getName());
		assertEquals("john@example.com", saved.getEmail());
	}

	@Test
	void testGetAllStudents() {
		// Create test data
		Student student1 = new Student();
		student1.setName("Alice");
		student1.setEmail("alice@example.com");
		studentRepo.save(student1);

		Student student2 = new Student();
		student2.setName("Bob");
		student2.setEmail("bob@example.com");
		studentRepo.save(student2);

		// Retrieve all
		var allStudents = studentRepo.findAll();   // var is used to infer the type of allStudents, which will be List<Student> based on the return type of findAll() method. This allows for cleaner code without explicitly declaring the type on the left-hand side.

		// Assertions
		assertNotNull(allStudents);
		assertEquals(2, allStudents.size());
	}

	@Test
	void testGetStudentById() {
		// Create and save a student
		Student student = new Student();
		student.setName("Jane Doe");
		student.setEmail("jane@example.com");
		Student saved = studentRepo.save(student);

		// Retrieve by ID
		var retrieved = studentRepo.findById(saved.getId());

		// Assertions
		assertTrue(retrieved.isPresent());
		assertEquals("Jane Doe", retrieved.get().getName());
		assertEquals("jane@example.com", retrieved.get().getEmail());
	}

	@Test
	void testUpdateStudent() {
		// Create initial student
		Student student = new Student();
		student.setName("Original Name");
		student.setEmail("original@example.com");
		Student saved = studentRepo.save(student);

		// Update the student
		saved.setName("Updated Name");
		saved.setEmail("updated@example.com");
		Student updated = studentRepo.save(saved);

		// Retrieve and verify
		var retrieved = studentRepo.findById(updated.getId()).orElse(null);

		assertNotNull(retrieved);
		assertEquals("Updated Name", retrieved.getName());
		assertEquals("updated@example.com", retrieved.getEmail());
	}

	@Test
	void testDeleteStudent() {
		// Create and save a student
		Student student = new Student();
		student.setName("Delete Me");
		student.setEmail("delete@example.com");
		Student saved = studentRepo.save(student);
		Long studentId = saved.getId();

		// Verify it exists
		assertTrue(studentRepo.findById(studentId).isPresent());

		// Delete the student
		studentRepo.deleteById(studentId);

		// Verify it's deleted
		assertTrue(studentRepo.findById(studentId).isEmpty());
	}

	@Test
	void testStudentNotFound() {
		// Try to retrieve non-existent student
		var result = studentRepo.findById(999L);

		// Assertions
		assertTrue(result.isEmpty());
	}

	@Test
	void testFindByEmail() {
		// Create and save a student
		Student student = new Student();
		student.setName("Email Test");
		student.setEmail("emailtest@example.com");
		studentRepo.save(student);

		// Find by email (if method exists)
		var allStudents = studentRepo.findAll();
		var found = allStudents.stream()
				.filter(s -> s.getEmail().equals("emailtest@example.com"))
				.findFirst();

		// Assertions
		assertTrue(found.isPresent());
		assertEquals("Email Test", found.get().getName());
	}

	@Test
	void testMultipleStudentsCreation() {
		// Create multiple students
		for (int i = 1; i <= 5; i++) {
			Student student = new Student();
			student.setName("Student " + i);
			student.setEmail("student" + i + "@example.com");
			studentRepo.save(student);
		}

		// Verify count
		var allStudents = studentRepo.findAll();
		assertEquals(5, allStudents.size());
	}

	@Test
	void testStudentFieldsNotNull() {
		// Create a student with all fields
		Student student = new Student();
		student.setName("Test Student");
		student.setEmail("test@example.com");

		Student saved = studentRepo.save(student);

		// Assertions - verify fields are not null
		assertNotNull(saved.getId());
		assertNotNull(saved.getName());
		assertNotNull(saved.getEmail());
		assertFalse(saved.getName().isEmpty());
		assertFalse(saved.getEmail().isEmpty());
	}

	@Test
	void testContextLoads() {
		// Verify Spring context loads successfully
		assertNotNull(studentService);
		assertNotNull(studentRepo);
	}

}
