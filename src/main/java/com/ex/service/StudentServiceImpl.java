// Implementation for StudentService interface
package com.ex.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ex.model.Student;
import com.ex.repo.StudentRepo;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepo studentRepo;    
    @Override
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }
    @Override
    public Student getStudentById(Long id) {
        Optional<Student> optionalStudent = studentRepo.findById(id);
        return optionalStudent.orElse(null);
    }   
    @Override
    public Student createStudent(Student student) {
        return studentRepo.save(student);
    }
    @Override
    public Student updateStudent(Long id, Student student) {
        Optional<Student> optionalStudent = studentRepo.findById(id);
        if (optionalStudent.isPresent()) {
            Student existingStudent = optionalStudent.get();
            existingStudent.setName(student.getName());
            existingStudent.setEmail(student.getEmail());
            return studentRepo.save(existingStudent);
        }
        return null;
    }
    @Override
    public void deleteStudent(Long id) {
        studentRepo.deleteById(id);
    }
}   
