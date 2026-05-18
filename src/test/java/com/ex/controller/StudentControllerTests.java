package com.ex.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.ex.model.Student;
import com.ex.service.StudentService;

/**
 * Controller Layer Tests using Mockito
 * 
 * @ExtendWith(MockitoExtension.class) - Enables Mockito in JUnit5
 * @Mock - Mocks StudentService (no actual business logic)
 * @InjectMocks - Injects mocked service into StudentController
 * 
 * Key Points:
 * - Tests HTTP request handling in controller
 * - No HTTP requests made (unit tests)
 * - Verifies controller delegates to service correctly
 * - Very fast execution
 */
@ExtendWith(MockitoExtension.class)
class StudentControllerTests {

	@Mock
	private StudentService studentService;

	@InjectMocks
	private StudentController studentController;

	private Student testStudent;

	@BeforeEach
	void setUp() {
		testStudent = new Student();
		testStudent.setId(1L);
		testStudent.setName("John Doe");
		testStudent.setEmail("john@example.com");
	}

	// ==================== GET Tests ====================

	@Test
	void testGetAllStudents() {
		// ARRANGE: Mock service to return list
		java.util.List<Student> students = java.util.Arrays.asList(testStudent);
		when(studentService.getAllStudents())
				.thenReturn(students);

		// ACT: Call controller method
		java.util.List<Student> result = studentController.getAllStudents();

		// ASSERT: Verify result
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("John Doe", result.get(0).getName());

		// VERIFY: Service method called
		verify(studentService, times(1)).getAllStudents();
	}

	@Test
	void testGetStudentById() {
		// ARRANGE: Mock service to return student
		when(studentService.getStudentById(1L))
				.thenReturn(testStudent);

		// ACT: Call controller method
		ResponseEntity<Student> response = studentController.getStudentById(1L);

		// ASSERT: Verify response
		assertEquals(ResponseEntity.ok(testStudent).getStatusCode(), response.getStatusCode());
		assertEquals("John Doe", response.getBody().getName());

		// VERIFY: Service called with correct ID
		verify(studentService, times(1)).getStudentById(1L);
	}

	@Test
	void testGetStudentByIdNotFound() {
		// ARRANGE: Mock service to return null (not found)
		when(studentService.getStudentById(999L))
				.thenReturn(null);

		// ACT: Call controller method
		ResponseEntity<Student> response = studentController.getStudentById(999L);

		// ASSERT: Verify 404 not found response
		assertEquals(org.springframework.http.HttpStatus.NOT_FOUND, response.getStatusCode());

		// VERIFY: Service called
		verify(studentService, times(1)).getStudentById(999L);
	}

	// ==================== POST Tests ====================

	@Test
	void testCreateStudent() {
		// ARRANGE: Mock service to return created student
		when(studentService.createStudent(any(Student.class)))
				.thenReturn(testStudent);

		// ACT: Call controller method
		Student student = new Student();
		student.setName("John Doe");
		student.setEmail("john@example.com");
		Student result = studentController.createStudent(student);

		// ASSERT: Verify result
		assertNotNull(result);
		assertEquals("John Doe", result.getName());
		assertEquals("john@example.com", result.getEmail());

		// VERIFY: Service.create() called once
		verify(studentService, times(1)).createStudent(any(Student.class));
	}

	// ==================== PUT Tests ====================

	@Test
	void testUpdateStudent() {
		// ARRANGE: Mock service to return updated student
		testStudent.setName("Updated Name");
		when(studentService.updateStudent(anyLong(), any(Student.class)))
				.thenReturn(testStudent);

		// ACT: Call controller method
		ResponseEntity<Student> response = studentController.updateStudent(1L, testStudent);

		// ASSERT: Verify response
		assertEquals(org.springframework.http.HttpStatus.OK, response.getStatusCode());
		assertEquals("Updated Name", response.getBody().getName());

		// VERIFY: Service.update() called
		verify(studentService, times(1)).updateStudent(1L, testStudent);
	}

	// ==================== DELETE Tests ====================

	@Test
	void testDeleteStudent() {
		// ARRANGE: Mock service delete operation
		doNothing().when(studentService).deleteStudent(1L);

		// ACT: Call controller method
		ResponseEntity<Void> response = studentController.deleteStudent(1L);

		// ASSERT: Verify no content response
		assertEquals(org.springframework.http.HttpStatus.NO_CONTENT, response.getStatusCode());

		// VERIFY: Service.delete() called with correct ID
		verify(studentService, times(1)).deleteStudent(1L);
	}

	// ==================== VERIFICATION Tests ====================

	@Test
	void testControllerDelegatesToService() {
		// This test verifies controller properly delegates to service
		// ARRANGE
		when(studentService.getAllStudents())
				.thenReturn(java.util.Arrays.asList(testStudent));

		// ACT
		studentController.getAllStudents();

		// VERIFY: Service was definitely called
		verify(studentService).getAllStudents();
	}

	@Test
	void testServiceCalledWithCorrectParameters() {
		// This test ensures controller passes correct params to service
		// ARRANGE
		when(studentService.getStudentById(1L))
				.thenReturn(testStudent);

		// ACT
		studentController.getStudentById(1L);

		// VERIFY: Service called with exactly id=1L
		verify(studentService).getStudentById(1L);
	}

}
