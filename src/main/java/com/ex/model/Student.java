//package com.ex.model;
package com.ex.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity                                                     //you specify entity in model class
@Table(name = "students")                                   //you specify the table name in database where you want to store the data
public class Student {
    @Id                                                      //Primary key where you specify @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)      //this means it will autogenerate the id value
    private Long id;
    
    @NotBlank(message = "Student name cannot be blank")       //Validation annotation - name must not be empty
    private String name;
    
    @NotBlank(message = "Student email cannot be blank")      //Validation annotation - email must not be empty
    @Email(message = "Email should be valid")                 //Validation annotation - email format validation
    private String email;
    
    @OneToOne(cascade = CascadeType.ALL)
    private StudentDetails studentDetails; //one to one relationship with student details

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StudentDetails getStudentDetails() {
        return studentDetails;
    }

    public void setStudentDetails(StudentDetails studentDetails) {
        this.studentDetails = studentDetails;
    }

}