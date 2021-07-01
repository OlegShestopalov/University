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
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.domain.entity.Student;
import ua.com.foxminded.domain.rest.StudentRestController;
import ua.com.foxminded.domain.service.StudentService;

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
public class StudentRestControllerTest {

    @MockBean
    private StudentService studentService;

    private MockMvc mockMvc;
    private final StudentRestController studentRestController;
    private Group group;

    @Autowired
    public StudentRestControllerTest(StudentRestController studentRestController) {
        this.studentRestController = studentRestController;
    }

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(studentRestController)
                .build();
    }

    @Test
    public void shouldReturnListOfStudents() throws Exception {
        when(studentService.findAll()).thenReturn(Arrays.asList(
                new Student(1L, group, "test1", "test", "Male", 20, "test@gmail.com"),
                new Student(2L, group, "test2", "test2", "Female", 18, "test2@gmail.com")));

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("test1", "test2")));
    }

    @Test
    public void shouldReturnStudentById() throws Exception {
        when(studentService.findById(anyLong())).thenReturn(
                new Student(1L, group, "test", "test", "Male", 20, "test@gmail.com"));

        mockMvc.perform(get("/api/v1/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("test")));
    }

    @Test
    public void shouldReturnStudentByName() throws Exception {
        when(studentService.findByPersonalData("test")).thenReturn(Arrays.asList(
                new Student(1L, group, "test", "test", "Male", 20, "test@gmail.com")));

        mockMvc.perform(get("/api/v1/students/name?name=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("test")));

        verify(studentService, times(1)).findByPersonalData(anyString());
    }

    @Test
    public void shouldCreateNewStudent() throws Exception {
        mockMvc.perform(post("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"test\"}"))
                .andExpect(status().isOk());

        verify(studentService, times(1)).create(any(Student.class));
    }

    @Test
    public void shouldUpdateStudent() throws Exception {
        mockMvc.perform(put("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"test\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Student successfully updated"));

        verify(studentService, times(1)).update(any(Student.class));
    }

    @Test
    public void studentNameShouldBeValid() throws Exception {
        Student student = new Student(group, "", "", "Male", 20, "test@gmail.com");

        mockMvc.perform(put("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(student)))
                .andExpect(status().isBadRequest());

        verify(studentService, times(0)).update(student);
    }

    @Test
    public void shouldDeleteStudent() throws Exception {
        mockMvc.perform(delete("/api/v1/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andReturn();

        verify(studentService, times(1)).delete(anyLong());
    }

    @Test
    public void shouldReturnPageWithStudents() throws Exception {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, group, "test1", "test", "Male", 20, "test@gmail.com"));
        Page<Student> page = new PageImpl<>(students);

        given(studentService.findAll(1)).willReturn(page);

        mockMvc.perform(get("/api/v1/students/pages/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("test1")));
    }
}
