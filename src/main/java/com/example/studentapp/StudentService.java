package com.example.studentapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student saveStudent(Student student) {
        if (student.getId() == null) {
            student.setEnrollmentDate(LocalDate.now());
            if (student.getStatus() == null) {
                student.setStatus("ACTIVE");
            }
        }
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> searchStudents(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllStudents();
        }
        return studentRepository.searchStudents(searchTerm);
    }

    public List<Student> filterByDepartment(String department) {
        return studentRepository.findByDepartment(department);
    }

    public List<Student> filterByStatus(String status) {
        return studentRepository.findByStatus(status);
    }

    public List<String> getAllDepartments() {
        return studentRepository.findAll().stream()
                .map(Student::getDepartment)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public double getAverageMarks() {
        return studentRepository.findAll().stream()
                .mapToInt(Student::getMarks)
                .average()
                .orElse(0.0);
    }

    public long getTotalStudents() {
        return studentRepository.count();
    }

    public long getActiveStudents() {
        return studentRepository.findByStatus("ACTIVE").size();
    }
}