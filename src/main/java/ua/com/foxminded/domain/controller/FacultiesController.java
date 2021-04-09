package ua.com.foxminded.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.com.foxminded.domain.service.FacultyService;

@Controller
public class FacultiesController {

    private final FacultyService facultyService;

    @Autowired
    public FacultiesController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/faculties")
    public String showAllFaculties(Model model) {
        model.addAttribute("faculties", facultyService.findAllFaculties());
        return "faculties/menu";
    }
}
