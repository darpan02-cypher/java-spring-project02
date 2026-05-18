package com.ex.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ex.model.Student;
import com.ex.repo.StudentRepo;

/**
 * Service Layer Tests using Mockito
 * 
 * @ExtendWith(MockitoExtension.class) - Enables Mockito in JUnit5
 * @Mock - Creates mock StudentRepo (no real database calls)
 * @InjectMocks - Injects mocks into StudentServiceImpl
 * 
 * Benefits:
 * - Tests service logic in isolation
 * - No database interactions (mocked)
 * - Fast test execution
 * - Verifies service calls repository correctly
 */
@ExtendWith(MockitoExtension.class)
class StudentServiceTests {

	@Mock
	private StudentRepo studentRepo;

	@InjectMocks
	private StudentServiceImpl studentService;

	private Student testStudent;

	@BeforeEach
	void setUp() {
		testStudent = new Student();
		testStudent.setId(1L);
		testStudent.setName("Test Student");
		testStudent.setEmail("test@example.com");
	}

	// ==================== CREATE Tests ====================

	@Test
	void testCreateStudent() {
		// ARRANGE: Mock repository to return saved student
		when(studentRepo.save(any(Student.class)))
				.thenReturn(testStudent);

		// ACT: Call service method
		Student student = new Student();
		student.setName("Test Student");
		student.setEmail("test@example.com");
		Student result = studentService.createStudent(student);

		// ASSERT: Verify result
		assertNotNull(result);
		assertEquals("Test Student", result.getName());

		// VERIFY: Ensure repository.save() was called exactly once
		verify(studentRepo, times(1)).save(any(Student.class));
	}

	// ==================== READ Tests ====================

	@Test
	void testGetStudentById() {
		// ARRANGE: Mock repository to return student
		when(studentRepo.findById(1L))
				.thenReturn(java.util.Optional.of(testStudent));

		// ACT: Call service method
		Student result = studentService.getStudentById(1L);

		// ASSERT: Verify result
		assertNotNull(result);
		assertEquals("Test Student", result.getName());

		// VERIFY: Repository method called with correct ID
		verify(studentRepo, times(1)).findById(1L);
	}

	@Test
	void testGetStudentByIdNotFound() {
		// ARRANGE: Mock repository to return empty
		when(studentRepo.findById(999L))
				.thenReturn(java.util.Optional.empty());

		// ACT & ASSERT: Verify exception is thrown when student not found
		assertThrows(com.ex.exception.StudentNotFoundException.class,
				() -> studentService.getStudentById(999L));

		// VERIFY: Repository called with correct ID
		verify(studentRepo, times(1)).findById(999L);
	}

	@Test
	void testGetAllStudents() {
		// ARRANGE: Mock repository to return list
		java.util.List<Student> students = java.util.Arrays.asList(testStudent);
		when(studentRepo.findAll())
				.thenReturn(students);

		// ACT: Call service method
		java.util.List<Student> result = studentService.getAllStudents();

		// ASSERT: Verify result
		assertNotNull(result);
		assertEquals(1, result.size());

		// VERIFY: Repository method called
		verify(studentRepo, times(1)).findAll();
	}

	// ==================== UPDATE Tests ====================

	@Test
	void testUpdateStudent() {
		// ARRANGE: Mock repository to return updated student
		testStudent.setName("Updated Name");
		when(studentRepo.findById(1L))
				.thenReturn(java.util.Optional.of(testStudent));
		when(studentRepo.save(any(Student.class)))
				.thenReturn(testStudent);

		// ACT: Call service method
		Student result = studentService.updateStudent(1L, testStudent);

		// ASSERT: Verify update
		assertEquals("Updated Name", result.getName());

		// VERIFY: Repository save called
		verify(studentRepo, times(1)).save(any(Student.class));
	}

	// ==================== DELETE Tests ====================

	@Test
	void testDeleteStudent() {
		// ARRANGE: Mock repository for delete operation and findById
		when(studentRepo.findById(1L))
				.thenReturn(java.util.Optional.of(testStudent));
		doNothing().when(studentRepo).deleteById(1L);

		// ACT: Call service method
		studentService.deleteStudent(1L);

		// VERIFY: deleteById() called with correct ID
		verify(studentRepo, times(1)).deleteById(1L);
	}

	// ==================== VERIFICATION Tests ====================

	@Test
	void testVerifyMultipleCalls() {
		// ARRANGE: Mock repository
		when(studentRepo.findById(1L))
				.thenReturn(java.util.Optional.of(testStudent));

		// ACT: Call method three times
		studentService.getStudentById(1L);
		studentService.getStudentById(1L);
		studentService.getStudentById(1L);

		// VERIFY: Method called exactly 3 times
		verify(studentRepo, times(3)).findById(1L);
	}

	@Test
	void testNeverCalledScenario() {
		// This test shows how to verify a method was NEVER called
		// ARRANGE
		when(studentRepo.findById(1L))
				.thenReturn(java.util.Optional.of(testStudent));

		// ACT: Call a different method (not deleteById)
		studentService.getStudentById(1L);

		// VERIFY: Ensure deleteById was never called
		verify(studentRepo, never()).deleteById(anyLong());
	}

}
