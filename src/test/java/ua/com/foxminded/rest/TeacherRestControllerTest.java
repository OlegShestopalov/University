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
import ua.com.foxminded.domain.entity.Teacher;
import ua.com.foxminded.domain.rest.TeacherRestController;
import ua.com.foxminded.domain.service.TeacherService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
public class TeacherRestControllerTest {

    @MockBean
    private TeacherService teacherService;

    private MockMvc mockMvc;
    private final TeacherRestController teacherRestController;

    @Autowired
    public TeacherRestControllerTest(TeacherRestController teacherRestController) {
        this.teacherRestController = teacherRestController;
    }

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(teacherRestController)
                .build();
    }

    @Test
    public void shouldReturnListOfTeachers() throws Exception {
        when(teacherService.findAll()).thenReturn(Arrays.asList(
                new Teacher(1L, "test1", "test1", "test1@gmail.com"),
                new Teacher(2L, "test2", "test2", "test2@gmail.com")));

        mockMvc.perform(get("/api/v1/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("test1", "test2")))
                .andExpect(jsonPath("$[*].surname", containsInAnyOrder("test1", "test2")))
                .andExpect(jsonPath("$[*].email", containsInAnyOrder("test1@gmail.com", "test2@gmail.com")));
    }

    @Test
    public void shouldReturnTeacherById() throws Exception {
        when(teacherService.findById(anyLong())).thenReturn(
                new Teacher(1L, "test", "test", "test@gmail.com"));

        mockMvc.perform(get("/api/v1/teachers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("test")))
                .andExpect(jsonPath("$.surname", equalTo("test")))
                .andExpect(jsonPath("$.email", equalTo("test@gmail.com")));

        verify(teacherService, times(1)).findById(anyLong());
    }

    @Test
    public void shouldReturnTeacherByName() throws Exception {
        when(teacherService.findByPersonalData("test")).thenReturn(Arrays.asList(
                new Teacher(1L, "test", "test", "test@gmail.com")));

        mockMvc.perform(get("/api/v1/teachers/name?name=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("test")));

        verify(teacherService, times(1)).findByPersonalData(anyString());
    }

    @Test
    public void shouldCreateNewTeacher() throws Exception {
        mockMvc.perform(post("/api/v1/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"test\"}"))
                .andExpect(status().isOk());

        verify(teacherService, times(1)).create(any(Teacher.class));
    }

    @Test
    public void shouldUpdateTeacher() throws Exception {
        mockMvc.perform(put("/api/v1/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"test\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Teacher successfully updated"));

        verify(teacherService, times(1)).update(any(Teacher.class));
    }

    @Test
    public void teacherNameShouldBeValid() throws Exception {
        Teacher teacher = new Teacher("", "", "");

        mockMvc.perform(put("/api/v1/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(teacher)))
                .andExpect(status().isBadRequest());

        verify(teacherService, times(0)).update(teacher);
    }

    @Test
    public void shouldDeleteTeacher() throws Exception {
        mockMvc.perform(delete("/api/v1/teachers/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andReturn();

        verify(teacherService, times(1)).delete(anyLong());
    }

    @Test
    public void shouldReturnPageWithTeachers() throws Exception {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher(1L, "test", "test", "test@gmail.com"));
        Page<Teacher> page = new PageImpl<>(teachers);

        given(teacherService.findAll(1)).willReturn(page);

        mockMvc.perform(get("/api/v1/teachers/pages/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("test")))
                .andExpect(jsonPath("$[*].surname", containsInAnyOrder("test")));
    }
}
