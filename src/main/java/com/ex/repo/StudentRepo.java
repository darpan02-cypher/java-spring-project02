//its an interface
package com.ex.repo;
import org.springframework.data.jpa.repository.JpaRepository;  //this is the interface provided by Spring Data JPA that provides basic CRUD operations for the Student entity. By extending JpaRepository, you can perform operations like saving, finding, updating, and deleting Student entities without having to write any implementation code.
import org.springframework.stereotype.Repository;
import com.ex.model.Student;



@Repository                  //DAO layer
//this annotation is used to indicate that the class is a repository and it will be responsible for data access operations. It also allows Spring to automatically detect and configure the repository during component scanning.
public interface StudentRepo extends JpaRepository<Student, Long> {
    // JpaRepository provides basic CRUD operations for the Student entity
}
