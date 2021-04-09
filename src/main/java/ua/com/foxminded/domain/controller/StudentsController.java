package ua.com.foxminded.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.domain.entity.Student;
import ua.com.foxminded.domain.service.StudentService;

@Controller
//@RequestMapping("/students")
public class StudentsController {

    private final StudentService studentService;

    @Autowired
    public StudentsController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/allStudents")
    public String showMenu() {
        return "students/allStudents";
    }

    @GetMapping("/students")
    public String showAllStudents(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "students/menu";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("student", studentService.find(id));
        return "students/student";
    }

    @GetMapping("/new")
    public String newStudent(@ModelAttribute("student") Student student) {
        return "students/new";
    }

    @PostMapping
    public String create(@ModelAttribute("student") Student student) {
        studentService.add(student);
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("student", studentService.find(id));
        return "students/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("student") Student student, @PathVariable("id") Long id) {
        studentService.update(id, student);
        return "redirect:/students";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        studentService.remove(id);
        return "redirect:/students";
    }
}
