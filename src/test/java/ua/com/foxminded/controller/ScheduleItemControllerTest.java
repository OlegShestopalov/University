package ua.com.foxminded.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.com.foxminded.domain.controller.ScheduleItemController;
import ua.com.foxminded.domain.entity.Audience;
import ua.com.foxminded.domain.entity.Day;
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.domain.entity.Lesson;
import ua.com.foxminded.domain.entity.ScheduleItem;
import ua.com.foxminded.domain.entity.Student;
import ua.com.foxminded.domain.entity.Subject;
import ua.com.foxminded.domain.entity.Teacher;
import ua.com.foxminded.domain.service.ScheduleItemService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
public class ScheduleItemControllerTest {

    private List<ScheduleItem> scheduleItems;
    private MockMvc mockMvc;

    @MockBean
    private ScheduleItemService scheduleItemService;

    private final ScheduleItemController scheduleItemController;

    @Autowired
    public ScheduleItemControllerTest(ScheduleItemController scheduleItemController) {
        this.scheduleItemController = scheduleItemController;
    }

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(scheduleItemController)
                .build();
    }

    @BeforeEach
    void init() {
        Lesson lesson = new Lesson();
        Subject subject = new Subject();
        Audience audience = new Audience();
        Day day = new Day();
        this.scheduleItems = new ArrayList<>();
        this.scheduleItems.add(new ScheduleItem(1L, lesson, subject, audience, day));
        this.scheduleItems.add(new ScheduleItem(2L, lesson, subject, audience, day));
        this.scheduleItems.add(new ScheduleItem(3L, lesson, subject, audience, day));
    }

    @Test
    void findAllScheduleItems() throws Exception {
        int pageNumber = 1;
        Lesson lesson = new Lesson();
        Subject subject = new Subject();
        Audience audience = new Audience();
        Day day = new Day();
        ScheduleItem one = new ScheduleItem(1L, lesson, subject, audience, day);
        Page<ScheduleItem> scheduleItems = new PageImpl<>(Collections.singletonList(one));

        when(scheduleItemService.findAll(pageNumber, day.getDay())).thenReturn(scheduleItems);
        given(scheduleItemService.findAll(pageNumber, day.getDay())).willReturn(scheduleItems);

        mockMvc.perform(get("/scheduleItems/page/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("scheduleItems/allScheduleItems"))
                .andExpect(model().attribute("scheduleItems", hasSize(1)));
    }

    @Test
    void findAllScheduleItemsForGroup() throws Exception {
        int pageNumber = 1;
        Lesson lesson = new Lesson();
        Subject subject = new Subject();
        Audience audience = new Audience();
        Day day = new Day();
        Group group = new Group();
        ScheduleItem one = new ScheduleItem(1L, lesson, subject, audience, day);
        Page<ScheduleItem> scheduleItems = new PageImpl<>(Collections.singletonList(one));

        when(scheduleItemService.findByGroupNameOrDay(pageNumber, group.getName(), day.getDay())).thenReturn(scheduleItems);
        given(scheduleItemService.findByGroupNameOrDay(pageNumber, group.getName(), day.getDay())).willReturn(scheduleItems);

        mockMvc.perform(get("/scheduleItems/groups/page/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("scheduleItems/forGroups"))
                .andExpect(model().attribute("scheduleItems", hasSize(1)));
    }

    @Test
    void findAllScheduleItemsForTeacher() throws Exception {
        int pageNumber = 1;
        Lesson lesson = new Lesson();
        Subject subject = new Subject();
        Audience audience = new Audience();
        Day day = new Day();
        Teacher teacher = new Teacher();
        ScheduleItem one = new ScheduleItem(1L, lesson, subject, audience, day);
        Page<ScheduleItem> scheduleItems = new PageImpl<>(Collections.singletonList(one));

        when(scheduleItemService.findByTeacherNameOrDay(pageNumber, teacher.getName(), day.getDay())).thenReturn(scheduleItems);
        given(scheduleItemService.findByTeacherNameOrDay(pageNumber, teacher.getName(), day.getDay())).willReturn(scheduleItems);

        mockMvc.perform(get("/scheduleItems/teachers/page/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("scheduleItems/forTeachers"))
                .andExpect(model().attribute("scheduleItems", hasSize(1)));
    }

    @Test
    void findScheduleItemById() throws Exception {
        when(scheduleItemService.findById(1L)).thenReturn(scheduleItems.get(0));

        mockMvc.perform(get("/scheduleItems/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("scheduleItems/schedule"))
                .andExpect(model().attribute("scheduleItem", instanceOf(ScheduleItem.class)));

        verify(scheduleItemService, times(1)).findById(1L);
        verifyNoMoreInteractions(scheduleItemService);
    }

    @Test
    void editScheduleItem() throws Exception {
        when(scheduleItemService.findById(1L)).thenReturn(scheduleItems.get(0));

        mockMvc.perform(get("/scheduleItems/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("scheduleItems/edit"))
                .andExpect(model().attribute("scheduleItem", instanceOf(ScheduleItem.class)));
    }

    @Test
    void createNewScheduleItem() throws Exception {
        verifyNoMoreInteractions(scheduleItemService);

        mockMvc.perform(get("/scheduleItems/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("scheduleItems/new"))
                .andExpect(model().attribute("scheduleItem", instanceOf(ScheduleItem.class)));
    }

    @Test
    public void deleteScheduleItem() throws Exception {
        doNothing().when(scheduleItemService).delete(scheduleItems.get(0).getId());

        mockMvc.perform(
                get("/scheduleItems/{id}/delete", scheduleItems.get(0).getId()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/scheduleItems/page/1"));

        verify(scheduleItemService, times(1)).delete(scheduleItems.get(0).getId());
    }

    @Test
    void updateScheduleItem() throws Exception {
        doNothing().when(scheduleItemService).create(any(ScheduleItem.class));

        mockMvc.perform(post("/scheduleItems/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/scheduleItems/page/1"))
                .andExpect(model().attribute("scheduleItem", instanceOf(ScheduleItem.class)));
    }
}
