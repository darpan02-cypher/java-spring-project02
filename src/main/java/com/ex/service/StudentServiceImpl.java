// Implementation for StudentService interface
package com.ex.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ex.exception.InvalidStudentException;
import com.ex.exception.StudentNotFoundException;
import com.ex.model.Student;
import com.ex.repo.StudentRepo;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepo studentRepo;    //dependency injection- it allows Spring to automatically inject an instance of StudentRepo into the StudentServiceImpl class. This means that you can use the studentRepo object to perform database operations without having to manually create an instance of it. Spring will take care of managing the lifecycle of the studentRepo bean and ensuring that it is available for use in the StudentServiceImpl class.
    @Override
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }
    @Override
    public Student getStudentById(Long id) {
        Optional<Student> optionalStudent = studentRepo.findById(id);
        return optionalStudent.orElseThrow(() -> 
            new StudentNotFoundException("Student not found with ID: " + id));
    }   
    @Override
    public Student createStudent(Student student) {
        // Validate input
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new InvalidStudentException("Student name cannot be empty");
        }
        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
            throw new InvalidStudentException("Student email cannot be empty");
        }
        return studentRepo.save(student);
    }
    @Override
    public Student updateStudent(Long id, Student student) {
        Optional<Student> optionalStudent = studentRepo.findById(id);
        if (optionalStudent.isPresent()) {
            Student existingStudent = optionalStudent.get();
            existingStudent.setName(student.getName());
            existingStudent.setEmail(student.getEmail());
            if (student.getStudentDetails() != null) {
                existingStudent.setStudentDetails(student.getStudentDetails());
            }
            return studentRepo.save(existingStudent);
        }
        throw new StudentNotFoundException("Student not found with ID: " + id);
    }
    @Override
    public void deleteStudent(Long id) {
        Optional<Student> optionalStudent = studentRepo.findById(id);
        if (optionalStudent.isPresent()) {
            studentRepo.deleteById(id);
        } else {
            throw new StudentNotFoundException("Student not found with ID: " + id);
        }
    }
}   
