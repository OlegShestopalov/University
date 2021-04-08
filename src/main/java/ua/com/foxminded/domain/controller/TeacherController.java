package ua.com.foxminded.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.com.foxminded.domain.service.TeacherService;

@Controller
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/teachers")
    public String getAllTeachers(Model model) {
        model.addAttribute("teachers", teacherService.findAll());
        return "teachers/allTeachers";
    }
}
