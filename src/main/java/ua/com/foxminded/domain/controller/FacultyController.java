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
import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.domain.service.FacultyService;
import ua.com.foxminded.exception.AlreadyExistException;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/faculties")
public class FacultyController {

    private final FacultyService facultyService;

    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/menu")
    public String showMenu() {
        return "faculties/menu";
    }

    @GetMapping("/allFaculties")
    public String showAllFaculties(Model model) throws NotFoundException {
        return showListByPage(model, "name", 1);
    }

    @GetMapping("/page/{pageNumber}")
    public String showListByPage(Model model,
                                 @Param("name") String name,
                                 @PathVariable("pageNumber") int currentPage) throws NotFoundException {
        Page<Faculty> page = facultyService.findAll(currentPage);
        int totalPages = page.getTotalPages();
        List<Faculty> faculties = page.getContent();

        String link = "/faculties/page/";
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("faculties", faculties);
        model.addAttribute("name", name);
        model.addAttribute("link", link);
        return "faculties/allFaculties";
    }

    @GetMapping("/search")
    public String showFacultiesByName(Model model, @Param("name") String name) throws NotFoundException {
        model.addAttribute("faculties", facultyService.findByName(name));
        model.addAttribute("name", name);
        return "faculties/facultiesByName";
    }

    @GetMapping("/{id}")
    public String showInfo(@PathVariable("id") Long id, Model model) throws NotFoundException {
        model.addAttribute("faculty", facultyService.findById(id));
        return "faculties/faculty";
    }

    @GetMapping("/new")
    public String newFaculty(@ModelAttribute("faculty") Faculty faculty) {
        return "faculties/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("faculty") @Valid Faculty faculty, BindingResult bindingResult) throws AlreadyExistException {
        if (bindingResult.hasErrors()) {
            return "faculties/new";
        }
        facultyService.create(faculty);
        return "redirect:/faculties/allFaculties";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) throws NotFoundException {
        model.addAttribute("faculty", facultyService.findById(id));
        return "faculties/edit";
    }

    @PostMapping("/{id}")
    public String update(@Valid Faculty faculty, BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors()) {
            return "faculties/edit";
        }
        facultyService.update(faculty);
        return "redirect:/faculties/allFaculties";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) throws NotFoundException {
        facultyService.delete(id);
        return "redirect:/faculties/allFaculties";
    }
}
