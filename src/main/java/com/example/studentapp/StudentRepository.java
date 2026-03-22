package com.example.studentapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByNameContainingIgnoreCase(String name);
    List<Student> findByDepartment(String department);
    List<Student> findByStatus(String status);
    
    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(s.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "s.rollNumber LIKE CONCAT('%', :searchTerm, '%')")
    List<Student> searchStudents(@Param("searchTerm") String searchTerm);
}