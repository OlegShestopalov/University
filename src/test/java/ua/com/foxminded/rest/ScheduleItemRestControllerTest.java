package ua.com.foxminded.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.com.foxminded.domain.entity.Audience;
import ua.com.foxminded.domain.entity.Day;
import ua.com.foxminded.domain.entity.Lesson;
import ua.com.foxminded.domain.entity.ScheduleItem;
import ua.com.foxminded.domain.entity.Subject;
import ua.com.foxminded.domain.rest.ScheduleItemRestController;
import ua.com.foxminded.domain.service.ScheduleItemService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ScheduleItemRestControllerTest {

    @MockBean
    private ScheduleItemService scheduleItemService;

    private MockMvc mockMvc;
    private final ScheduleItemRestController scheduleItemRestController;
    private Lesson lesson = new Lesson(1L, "lesson");
    private Subject subject = new Subject(1L, "subject", "subject");
    private Audience audience = new Audience(1L, 1, 1);
    private Day day = new Day(1L, LocalDate.parse("2020-09-01"));

    @Autowired
    public ScheduleItemRestControllerTest(ScheduleItemRestController scheduleItemRestController) {
        this.scheduleItemRestController = scheduleItemRestController;
    }

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(scheduleItemRestController)
                .build();
    }

    @Test
    public void shouldReturnListOfScheduleItems() throws Exception {
        when(scheduleItemService.findAll()).thenReturn(Arrays.asList(
                new ScheduleItem(1L, lesson, subject, audience, day)));

        mockMvc.perform(get("/api/v1/scheduleItems"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1)));
    }

    @Test
    public void shouldReturnScheduleItemById() throws Exception {
        when(scheduleItemService.findById(anyLong())).thenReturn(
                new ScheduleItem(1L, lesson, subject, audience, day));

        mockMvc.perform(get("/api/v1/scheduleItems/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    public void shouldCreateNewScheduleItem() throws Exception {
        mockMvc.perform(post("/api/v1/scheduleItems")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"lesson\": \"lesson\"}"))
                .andExpect(status().isOk());

        verify(scheduleItemService, times(1)).create(any(ScheduleItem.class));
    }

    @Test
    public void shouldUpdateScheduleItem() throws Exception {
        mockMvc.perform(put("/api/v1/scheduleItems")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"lesson\": \"lesson\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("ScheduleItem successfully updated"));

        verify(scheduleItemService, times(1)).update(any(ScheduleItem.class));
    }

    @Test
    public void shouldDeleteScheduleItem() throws Exception {
        mockMvc.perform(delete("/api/v1/scheduleItems/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andReturn();

        verify(scheduleItemService, times(1)).delete(anyLong());
    }

    @Test
    public void shouldReturnPageWithScheduleItems() throws Exception {
        LocalDate date = LocalDate.parse("2020-09-01");
        List<ScheduleItem> scheduleItems = new ArrayList<>();
        scheduleItems.add(new ScheduleItem(1L, lesson, subject, audience, day));
        Page<ScheduleItem> page = new PageImpl<>(scheduleItems);

        given(scheduleItemService.findAll(1, day.getDay())).willReturn(page);

        mockMvc.perform(get("/api/v1/scheduleItems/pages/1?day=2020-09-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1)));
    }

    @Test
    public void shouldReturnPageWithScheduleItemsByDay() throws Exception {
        LocalDate date = LocalDate.parse("2020-09-01");
        List<ScheduleItem> scheduleItems = new ArrayList<>();
        scheduleItems.add(new ScheduleItem(1L, lesson, subject, audience, day));
        Page<ScheduleItem> page = new PageImpl<>(scheduleItems);

        given(scheduleItemService.findByDay(1, date)).willReturn(page);

        mockMvc.perform(get("/api/v1/scheduleItems/pagesByDay/1?day=2020-09-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1)));
    }

    @Test
    public void shouldReturnPageWithScheduleItemsByGroupNameOrDay() throws Exception {
        LocalDate date = LocalDate.parse("2020-09-01");
        String name = "test";
        List<ScheduleItem> scheduleItems = new ArrayList<>();
        scheduleItems.add(new ScheduleItem(1L, lesson, subject, audience, day));
        Page<ScheduleItem> page = new PageImpl<>(scheduleItems);

        given(scheduleItemService.findByGroupNameOrDay(1, name, date)).willReturn(page);

        mockMvc.perform(get("/api/v1/scheduleItems/pagesForGroup/1?day=2020-09-01&name=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1)));
    }

    @Test
    public void shouldReturnPageWithScheduleItemsByTeacherNameOrDay() throws Exception {
        LocalDate date = LocalDate.parse("2020-09-01");
        String name = "test";
        List<ScheduleItem> scheduleItems = new ArrayList<>();
        scheduleItems.add(new ScheduleItem(1L, lesson, subject, audience, day));
        Page<ScheduleItem> page = new PageImpl<>(scheduleItems);

        given(scheduleItemService.findByTeacherNameOrDay(1, name, date)).willReturn(page);

        mockMvc.perform(get("/api/v1/scheduleItems/pagesForTeacher/1?day=2020-09-01&name=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1)));
    }
}
