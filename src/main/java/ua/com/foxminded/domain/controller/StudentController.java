package ua.com.foxminded.domain.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.domain.entity.Student;
import ua.com.foxminded.domain.service.StudentService;
import ua.com.foxminded.exception.AlreadyExistException;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/menu")
    public String showMenu() {
        return "students/menu";
    }

    @GetMapping("/allStudents")
    public String showAllStudents(Model model) throws NotFoundException {
        return showListByPage(model, "name", 1);
    }

    @GetMapping("/page/{pageNumber}")
    public String showListByPage(Model model,
                                 @Param("name") String name,
                                 @PathVariable("pageNumber") int currentPage) throws NotFoundException {
        Page<Student> page = studentService.findAll(currentPage);
        int totalPages = page.getTotalPages();
        List<Student> students = page.getContent();

        String link = "/students/page/";
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("students", students);
        model.addAttribute("name", name);
        model.addAttribute("link", link);
        return "students/allStudents";
    }

    @GetMapping("/search")
    public String showStudentsByName(Model model, @Param("name") String name) throws NotFoundException {
        model.addAttribute("students", studentService.findByPersonalData(name));
        model.addAttribute("name", name);
        return "students/studentsByName";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) throws NotFoundException {
        model.addAttribute("student", studentService.findById(id));
        return "students/student";
    }

    @GetMapping("/new")
    public String newStudent(@ModelAttribute("student") Student student) {
        return "students/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("student") @Valid Student student, BindingResult bindingResult) throws AlreadyExistException {
        if (bindingResult.hasErrors()) {
            return "students/new";
        }
        studentService.create(student);
        return "redirect:/students/allStudents";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) throws NotFoundException {
        model.addAttribute("student", studentService.findById(id));
        return "students/edit";
    }

    @PostMapping("/{id}")
    public String update(@Valid Student student, BindingResult bindingResult) throws AlreadyExistException {
        if (bindingResult.hasErrors()) {
            return "students/edit";
        }
        studentService.create(student);
        return "redirect:/students/allStudents";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) throws NotFoundException {
        studentService.delete(id);
        return "redirect:/students/allStudents";
    }
}
