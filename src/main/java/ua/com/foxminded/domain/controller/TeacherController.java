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
import ua.com.foxminded.domain.entity.Teacher;
import ua.com.foxminded.domain.service.TeacherService;
import ua.com.foxminded.exception.AlreadyExistException;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/menu")
    public String showMenu() {
        return "teachers/menu";
    }

    @GetMapping("/allTeachers")
    public String showAllTeachers(Model model) throws NotFoundException {
        return showListByPage(model, "name", 1);
    }

    @GetMapping("/page/{pageNumber}")
    public String showListByPage(Model model, @Param("name") String name, @PathVariable("pageNumber") int currentPage) throws NotFoundException {
        Page<Teacher> page = teacherService.findAll(currentPage);
        int totalPages = page.getTotalPages();
        List<Teacher> teachers = page.getContent();

        String link = "/teachers/page/";
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("teachers", teachers);
        model.addAttribute("name", name);
        model.addAttribute("link", link);
        return "teachers/allTeachers";
    }

    @GetMapping("/search")
    public String showTeachersByName(Model model, @Param("name") String name) throws NotFoundException {
        model.addAttribute("teachers", teacherService.findByPersonalData(name));
        model.addAttribute("name", name);
        return "teachers/teachersByName";
    }

    @GetMapping("/{id}")
    public String showInfo(@PathVariable("id") Long id, Model model) throws NotFoundException {
        model.addAttribute("teacher", teacherService.findById(id));
        return "teachers/teacher";
    }

    @GetMapping("/new")
    public String newTeacher(@ModelAttribute("teacher") Teacher teacher) {
        return "teachers/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("teacher") @Valid Teacher teacher, BindingResult bindingResult) throws AlreadyExistException {
        if (bindingResult.hasErrors()) {
            return "teachers/new";
        }
        teacherService.create(teacher);
        return "redirect:/teachers/allTeachers";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) throws NotFoundException {
        model.addAttribute("teacher", teacherService.findById(id));
        return "teachers/edit";
    }

    @PostMapping("/{id}")
    public String update(@Valid Teacher teacher, BindingResult bindingResult) throws AlreadyExistException {
        if (bindingResult.hasErrors()) {
            return "teachers/edit";
        }
        teacherService.create(teacher);
        return "redirect:/teachers/allTeachers";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) throws NotFoundException {
        teacherService.delete(id);
        return "redirect:/teachers/allTeachers";
    }
}
