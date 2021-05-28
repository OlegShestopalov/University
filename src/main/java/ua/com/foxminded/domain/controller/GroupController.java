package ua.com.foxminded.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import javax.validation.Valid;

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
    public String showAllGroups(Model model) {
        model.addAttribute("groups", groupService.findAll());
        return "groups/allGroups";
    }

    @GetMapping("/{id}")
    public String showInfo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("group", groupService.findById(id));
        return "groups/group";
    }

    @GetMapping("/new")
    public String newGroup(@ModelAttribute("group") Group group) {
        return "groups/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("group") @Valid Group group, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "groups/new";
        }
        groupService.create(group);
        return "redirect:/groups/allGroups";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("group", groupService.findById(id));
        return "groups/edit";
    }

    @PostMapping("/{id}")
    public String update(@Valid Group group, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "groups/edit";
        }
        groupService.create(group);
        return "redirect:/groups/allGroups";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        groupService.delete(id);
        return "redirect:/groups/allGroups";
    }
}
