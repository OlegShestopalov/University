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
import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.domain.rest.FacultyRestController;
import ua.com.foxminded.domain.service.FacultyService;

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
public class FacultyRestControllerTest {

    @MockBean
    private FacultyService facultyService;

    private MockMvc mockMvc;
    private final FacultyRestController facultyRestController;

    @Autowired
    public FacultyRestControllerTest(FacultyRestController facultyRestController) {
        this.facultyRestController = facultyRestController;
    }

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(facultyRestController)
                .build();
    }

    @Test
    public void shouldReturnListOfFaculties() throws Exception {
        when(facultyService.findAll()).thenReturn(Arrays.asList(
                new Faculty(1L, "test1"),
                new Faculty(2L, "test2")));

        mockMvc.perform(get("/api/v1/faculties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("test1", "test2")));
    }

    @Test
    public void shouldReturnFacultyById() throws Exception {
        when(facultyService.findById(anyLong())).thenReturn(
                new Faculty(1L, "test"));

        mockMvc.perform(get("/api/v1/faculties/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("test")));
    }

    @Test
    public void shouldReturnFacultyByName() throws Exception {
        when(facultyService.findByName("test")).thenReturn(Arrays.asList(
                new Faculty(1L, "test")));

        mockMvc.perform(get("/api/v1/faculties/name?name=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("test")));

        verify(facultyService, times(1)).findByName(anyString());
    }

    @Test
    public void shouldCreateNewFaculty() throws Exception {
        mockMvc.perform(post("/api/v1/faculties")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"test\"}"))
                .andExpect(status().isOk());

        verify(facultyService, times(1)).create(any(Faculty.class));
    }

    @Test
    public void shouldUpdateFaculty() throws Exception {
        mockMvc.perform(put("/api/v1/faculties")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"test\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Faculty successfully updated"));

        verify(facultyService, times(1)).update(any(Faculty.class));
    }

    @Test
    public void facultyNameShouldBeValid() throws Exception {
        Faculty faculty = new Faculty("");

        mockMvc.perform(put("/api/v1/faculties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(faculty)))
                .andExpect(status().isBadRequest());

        verify(facultyService, times(0)).update(faculty);
    }

    @Test
    public void shouldDeleteFaculty() throws Exception {
        mockMvc.perform(delete("/api/v1/faculties/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andReturn();

        verify(facultyService, times(1)).delete(anyLong());
    }

    @Test
    public void shouldReturnPageWithFaculties() throws Exception {
        List<Faculty> faculties = new ArrayList<>();
        faculties.add(new Faculty(1L, "test1"));
        Page<Faculty> page = new PageImpl<>(faculties);

        given(facultyService.findAll(1)).willReturn(page);

        mockMvc.perform(get("/api/v1/faculties/pages/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("test1")));
    }
}
