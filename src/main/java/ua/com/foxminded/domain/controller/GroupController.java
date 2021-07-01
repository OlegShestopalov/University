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
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.domain.service.GroupService;
import ua.com.foxminded.exception.AlreadyExistException;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/menu")
    public String showMenu() {
        return "groups/menu";
    }

    @GetMapping("/allGroups")
    public String showAllGroups(Model model) throws NotFoundException {
        return showListByPage(model, "name", 1);
    }

    @GetMapping("/page/{pageNumber}")
    public String showListByPage(Model model, @Param("name") String name, @PathVariable("pageNumber") int currentPage) throws NotFoundException {
        Page<Group> page = groupService.findAll(currentPage);
        int totalPages = page.getTotalPages();
        List<Group> groups = page.getContent();

        String link = "/groups/page/";
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("groups", groups);
        model.addAttribute("name", name);
        model.addAttribute("link", link);
        return "groups/allGroups";
    }

    @GetMapping("/search")
    public String showGroupsByName(Model model, @Param("name") String name) throws NotFoundException {
        model.addAttribute("groups", groupService.findByName(name));
        model.addAttribute("name", name);
        return "groups/groupsByName";
    }

    @GetMapping("/{id}")
    public String showInfo(@PathVariable("id") Long id, Model model) throws NotFoundException {
        model.addAttribute("group", groupService.findById(id));
        return "groups/group";
    }

    @GetMapping("/new")
    public String newGroup(@ModelAttribute("group") Group group) {
        return "groups/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("group") @Valid Group group, BindingResult bindingResult) throws AlreadyExistException {
        if (bindingResult.hasErrors()) {
            return "groups/new";
        }
        groupService.create(group);
        return "redirect:/groups/allGroups";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) throws NotFoundException {
        model.addAttribute("group", groupService.findById(id));
        return "groups/edit";
    }

    @PostMapping("/{id}")
    public String update(@Valid Group group, BindingResult bindingResult) throws AlreadyExistException {
        if (bindingResult.hasErrors()) {
            return "groups/edit";
        }
        groupService.create(group);
        return "redirect:/groups/allGroups";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) throws NotFoundException {
        groupService.delete(id);
        return "redirect:/groups/allGroups";
    }
}
