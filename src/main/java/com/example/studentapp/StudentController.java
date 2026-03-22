package com.example.studentapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/dashboard")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public String viewHomePage(Model model, 
                              @RequestParam(required = false) String search,
                              @RequestParam(required = false) String department,
                              @RequestParam(required = false) String status) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("students", studentService.searchStudents(search));
            model.addAttribute("searchTerm", search);
        } else if (department != null && !department.isEmpty()) {
            model.addAttribute("students", studentService.filterByDepartment(department));
            model.addAttribute("selectedDepartment", department);
        } else if (status != null && !status.isEmpty()) {
            model.addAttribute("students", studentService.filterByStatus(status));
            model.addAttribute("selectedStatus", status);
        } else {
            model.addAttribute("students", studentService.getAllStudents());
        }
        
        model.addAttribute("totalStudents", studentService.getTotalStudents());
        model.addAttribute("activeStudents", studentService.getActiveStudents());
        model.addAttribute("averageMarks", String.format("%.2f", studentService.getAverageMarks()));
        model.addAttribute("departments", studentService.getAllDepartments());
        return "index";
    }

    @GetMapping("/add")
    public String showAddStudentForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("departments", studentService.getAllDepartments());
        return "add-student";
    }

    @PostMapping("/save")
    public String saveStudent(@Valid @ModelAttribute("student") Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departments", studentService.getAllDepartments());
            return "add-student";
        }
        studentService.saveStudent(student);
        return "redirect:/dashboard/";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateStudentForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        model.addAttribute("student", student);
        model.addAttribute("departments", studentService.getAllDepartments());
        return "update-student";
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable Long id, @Valid @ModelAttribute("student") Student student, 
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departments", studentService.getAllDepartments());
            return "update-student";
        }
        student.setId(id);
        studentService.saveStudent(student);
        return "redirect:/dashboard/";
    }

    @GetMapping("/view/{id}")
    public String viewStudentDetails(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        model.addAttribute("student", student);
        return "view-student";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/dashboard/";
    }
}