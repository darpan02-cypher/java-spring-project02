//package com.ex.model;
package com.ex.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity                                                     //you specify entity in model class
@Table(name = "students")                                   //you specify the table name in database where you want to store the data
public class Student {
    @Id                                                      //Primary key where you specify @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)      //this means it will autogenerate the id value
    private Long id;
    private String name;
    private String email;

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
}