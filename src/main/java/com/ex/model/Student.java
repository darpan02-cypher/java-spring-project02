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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity                                                     //you specify entity in model class
@Table(name = "students")                                   //you specify the table name in database where you want to store the data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}