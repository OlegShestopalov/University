package ua.com.foxminded.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.com.foxminded.domain.controller.TeacherController;
import ua.com.foxminded.domain.entity.Teacher;
import ua.com.foxminded.domain.service.TeacherService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class TeacherControllerTest {

    private List<Teacher> teachers;
    private MockMvc mockMvc;

    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private TeacherController teacherController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
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
        when(teacherService.findAll()).thenReturn(teachers);

        mockMvc.perform(get("/teachers/allTeachers"))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/allTeachers"))
                .andExpect(model().attribute("teachers", hasSize(3)));

        verify(teacherService, atLeastOnce()).findAll();
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
        doNothing().when(teacherService).update(anyLong(), any(Teacher.class));
        assertEquals(teacherController.update(teachers.get(0), teachers.get(0).getId()), "redirect:/teachers/allTeachers");
    }
}
