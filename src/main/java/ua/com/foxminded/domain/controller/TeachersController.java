package ua.com.foxminded.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.domain.entity.Teacher;
import ua.com.foxminded.domain.service.TeacherService;

@Controller
@RequestMapping("/teachers")
public class TeachersController {

    private final TeacherService teacherService;

    @Autowired
    public TeachersController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/menu")
    public String showMenu(Model model) {
        return "teachers/menu";
    }

    @GetMapping("allTeachers")
    public String showAllTeachers(Model model) {
        model.addAttribute("teachers", teacherService.findAll());
        return "teachers/allTeachers";
    }

    @GetMapping("/{id}")
    public String showInfo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("teacher", teacherService.findById(id));
        return "teachers/teacher";
    }

    @GetMapping("/new")
    public String newStudent(@ModelAttribute("teacher") Teacher teacher) {
        return "teachers/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("teacher") Teacher teacher) {
        teacherService.create(teacher);
        return "redirect:/teachers/allTeachers";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("teacher", teacherService.findById(id));
        return "teachers/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("teacher") Teacher teacher, @PathVariable("id") Long id) {
        teacherService.update(id, teacher);
        return "redirect:/teachers/allTeachers";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        teacherService.delete(id);
        return "redirect:/teachers/allTeachers";
    }
}
