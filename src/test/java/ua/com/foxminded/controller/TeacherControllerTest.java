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
import ua.com.foxminded.domain.controller.TeacherController;
import ua.com.foxminded.domain.entity.Student;
import ua.com.foxminded.domain.entity.Teacher;
import ua.com.foxminded.domain.service.TeacherService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
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
public class TeacherControllerTest {

    private List<Teacher> teachers;
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    @Autowired
    private TeacherController teacherController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(teacherController)
                .build();
    }

    @BeforeEach
    void init() {
        this.teachers = new ArrayList<>();
        this.teachers.add(new Teacher(1L, "User1", "User1", "user1@gmail.com"));
        this.teachers.add(new Teacher(2L, "User2", "User2", "user2@gmail.com"));
        this.teachers.add(new Teacher(3L, "User3", "User3", "user3@gmail.com"));
    }

    @Test
    void findAllTeachers() throws Exception {
        int pageNumber = 1;
        Teacher one = new Teacher(1L, "teacher1", "teacher1", "teacher1@gmail.com");
        Page<Teacher> teachers = new PageImpl<>(Collections.singletonList(one));

        when(teacherService.findAll(pageNumber)).thenReturn(teachers);

        mockMvc.perform(get("/teachers/allTeachers"))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/allTeachers"))
                .andExpect(model().attribute("teachers", hasSize(1)));
    }

    @Test
    void findTeachersByName() throws Exception {
        when(teacherService.findByPersonalData(any())).thenReturn(teachers);

        mockMvc.perform(get("/teachers/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/teachersByName"))
                .andExpect(model().attribute("teachers", hasSize(3)));

        verify(teacherService, atLeastOnce()).findByPersonalData(any());
        verifyNoMoreInteractions(teacherService);
    }

    @Test
    void findTeacherById() throws Exception {
        when(teacherService.findById(1L)).thenReturn(teachers.get(0));

        mockMvc.perform(get("/teachers/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/teacher"))
                .andExpect(model().attribute("teacher", instanceOf(Teacher.class)));

        verify(teacherService, times(1)).findById(1L);
        verifyNoMoreInteractions(teacherService);
    }

    @Test
    void editTeacher() throws Exception {
        when(teacherService.findById(1L)).thenReturn(teachers.get(0));

        mockMvc.perform(get("/teachers/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/edit"))
                .andExpect(model().attribute("teacher", instanceOf(Teacher.class)));
    }

    @Test
    void createNewTeacher() throws Exception {
        verifyNoMoreInteractions(teacherService);

        mockMvc.perform(get("/teachers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/new"))
                .andExpect(model().attribute("teacher", instanceOf(Teacher.class)));
    }

    @Test
    public void deleteTeacher() throws Exception {
        doNothing().when(teacherService).delete(teachers.get(0).getId());

        mockMvc.perform(
                get("/teachers/{id}/delete", teachers.get(0).getId()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/teachers/allTeachers"));

        verify(teacherService, times(1)).delete(teachers.get(0).getId());
    }

    @Test
    void updateTeacher() throws Exception {
        doNothing().when(teacherService).create(any(Teacher.class));

        mockMvc.perform(post("/teachers/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/edit"))
                .andExpect(model().attribute("teacher", instanceOf(Teacher.class)));
    }
}
