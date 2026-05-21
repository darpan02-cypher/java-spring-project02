//interface for service layer
package com.ex.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ex.model.Student;

@Service
public interface StudentService {
    List<Student> getAllStudents();
    Student getStudentById(Long id);
    Student createStudent(Student student);
    Student updateStudent(Long id, Student student);
    void deleteStudent(Long id);
    List<Student> getStudentsPaginated(int page, int size);
}   