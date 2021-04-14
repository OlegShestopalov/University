package ua.com.foxminded.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.domain.service.FacultyService;

@Controller
@RequestMapping("/faculties")
public class FacultiesController {

    private final FacultyService facultyService;

    @Autowired
    public FacultiesController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/menu")
    public String showMenu() {
        return "faculties/menu";
    }

    @GetMapping("/allFaculties")
    public String showAllFaculties(Model model) {
        model.addAttribute("faculties", facultyService.findAllFaculties());
        return "faculties/allFaculties";
    }

    @GetMapping("/{id}")
    public String showInfo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("faculty", facultyService.find(id));
        return "faculties/faculty";
    }

    @GetMapping("/new")
    public String newFaculty(@ModelAttribute("faculty") Faculty faculty) {
        return "faculties/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("faculty") Faculty faculty) {
        facultyService.add(faculty);
        return "redirect:/faculties/allFaculties";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("faculty", facultyService.find(id));
        return "faculties/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("faculty") Faculty faculty, @PathVariable("id") Long id) {
        facultyService.update(id, faculty);
        return "redirect:/faculties/allFaculties";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        facultyService.remove(id);
        return "redirect:/faculties/allFaculties";
    }
}
