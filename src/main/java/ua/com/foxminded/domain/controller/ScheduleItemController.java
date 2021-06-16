package ua.com.foxminded.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.domain.entity.ScheduleItem;
import ua.com.foxminded.domain.service.ScheduleItemService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/scheduleItems")
public class ScheduleItemController {

    private final ScheduleItemService scheduleItemService;

    @Autowired
    public ScheduleItemController(ScheduleItemService scheduleItemService) {
        this.scheduleItemService = scheduleItemService;
    }

    @GetMapping("/menu")
    public String showMenu() {
        return "scheduleItems/menu";
    }

    @GetMapping("/page/{pageNumber}")
    public String showListByPage(Model model,
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day,
                                 @PathVariable("pageNumber") int currentPage) {
        Page<ScheduleItem> page = scheduleItemService.findAll(currentPage, day);
        int totalPages = page.getTotalPages();
        List<ScheduleItem> scheduleItems = page.getContent();

        String link = "/scheduleItems/page/";
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("scheduleItems", scheduleItems);
        model.addAttribute("day", day);
        model.addAttribute("link", link);
        return "scheduleItems/allScheduleItems";
    }

    @GetMapping("/groups/page/{pageNumber}")
    public String showAllSchedulesForGroup(Model model,
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day,
                                           String name,
                                           @PathVariable("pageNumber") int currentPage) {
        Page<ScheduleItem> page = scheduleItemService.findByGroupNameOrDay(currentPage, name, day);
        int totalPages = page.getTotalPages();
        List<ScheduleItem> scheduleItems = page.getContent();

        String link = "/scheduleItems/groups/page/";
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("scheduleItems", scheduleItems);
        model.addAttribute("day", day);
        model.addAttribute("name", name);
        model.addAttribute("link", link);
        return "scheduleItems/forGroups";
    }

    @GetMapping("/teachers/page/{pageNumber}")
    public String showAllSchedulesForTeacher(Model model,
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day,
                                             String name,
                                             @PathVariable("pageNumber") int currentPage) {
        Page<ScheduleItem> page = scheduleItemService.findByTeacherNameOrDay(currentPage, name, day);
        int totalPages = page.getTotalPages();
        List<ScheduleItem> scheduleItems = page.getContent();

        String link = "/scheduleItems/teachers/page/";
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("scheduleItems", scheduleItems);
        model.addAttribute("day", day);
        model.addAttribute("name", name);
        model.addAttribute("link", link);
        return "scheduleItems/forTeachers";
    }

    @GetMapping("/{id}")
    public String showInfo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("scheduleItem", scheduleItemService.findById(id));
        return "scheduleItems/schedule";
    }

    @GetMapping("/new")
    public String newScheduleItem(@ModelAttribute("scheduleItem") ScheduleItem scheduleItem) {
        return "scheduleItems/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("scheduleItem") @Valid ScheduleItem scheduleItem, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "scheduleItems/new";
        }
        scheduleItemService.create(scheduleItem);
        return "redirect:/scheduleItems/page/1";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("scheduleItem", scheduleItemService.findById(id));
        return "scheduleItems/edit";
    }

    @PostMapping("/{id}")
    public String update(@Valid ScheduleItem scheduleItem, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "scheduleItems/edit";
        }
        scheduleItemService.create(scheduleItem);
        return "redirect:/scheduleItems/page/1";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        scheduleItemService.delete(id);
        return "redirect:/scheduleItems/page/1";
    }
}
