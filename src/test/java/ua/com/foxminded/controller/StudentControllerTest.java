package ua.com.foxminded.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.com.foxminded.domain.controller.StudentController;
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.domain.entity.Student;
import ua.com.foxminded.domain.service.StudentService;

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

public class StudentControllerTest {

    private List<Student> students;
    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(studentController)
                .build();
    }

    @BeforeEach
    void init() {
        Group group = new Group();
        this.students = new ArrayList<>();
        this.students.add(new Student(1L, group, "User1", "User1", "Male", 20, "user1@gmail.com"));
        this.students.add(new Student(2L, group, "User2", "User2", "Male", 20, "user2@gmail.com"));
        this.students.add(new Student(3L, group, "User3", "User3", "Male", 20, "user3@gmail.com"));
    }

    @Test
    void findAllStudents() throws Exception {
        when(studentService.findAll()).thenReturn(students);

        mockMvc.perform(get("/students/allStudents"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/allStudents"))
                .andExpect(model().attribute("students", hasSize(3)));

        verify(studentService, atLeastOnce()).findAll();
        verifyNoMoreInteractions(studentService);
    }

    @Test
    void findStudentById() throws Exception {
        when(studentService.findById(1L)).thenReturn(students.get(0));

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/student"))
                .andExpect(model().attribute("student", instanceOf(Student.class)));

        verify(studentService, times(1)).findById(1L);
        verifyNoMoreInteractions(studentService);
    }

    @Test
    void editStudent() throws Exception {
        when(studentService.findById(1L)).thenReturn(students.get(0));

        mockMvc.perform(get("/students/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/edit"))
                .andExpect(model().attribute("student", instanceOf(Student.class)));
    }

    @Test
    void createNewStudent() throws Exception {
        verifyNoMoreInteractions(studentService);

        mockMvc.perform(get("/students/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/new"))
                .andExpect(model().attribute("student", instanceOf(Student.class)));
    }

    @Test
    public void deleteStudent() throws Exception {
        doNothing().when(studentService).delete(students.get(0).getId());

        mockMvc.perform(
                get("/students/{id}/delete", students.get(0).getId()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/students/allStudents"));

        verify(studentService, times(1)).delete(students.get(0).getId());
    }

    @Test
    void updateStudent() throws Exception {
        doNothing().when(studentService).update(anyLong(), any(Student.class));
        assertEquals(studentController.update(students.get(0), students.get(0).getId()), "redirect:/students/allStudents");
    }
}
