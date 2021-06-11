package ua.com.foxminded.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.com.foxminded.domain.controller.FacultyController;
import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.domain.entity.Student;
import ua.com.foxminded.domain.service.FacultyService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
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
public class FacultyControllerTest {

    private List<Faculty> faculties;
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @Autowired
    private FacultyController facultyController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(facultyController)
                .build();
    }

    @BeforeEach
    void init() {
        this.faculties = new ArrayList<>();
        this.faculties.add(new Faculty(1L, "User1"));
        this.faculties.add(new Faculty(2L, "User2"));
        this.faculties.add(new Faculty(3L, "User3"));
    }

    @Test
    void findAllFaculties() throws Exception {
        int pageNumber = 1;
        Faculty one = new Faculty(1L, "faculty1");
        Page<Faculty> faculties = new PageImpl<>(Collections.singletonList(one));

        when(facultyService.findAll(pageNumber)).thenReturn(faculties);

        mockMvc.perform(get("/faculties/allFaculties"))
                .andExpect(status().isOk())
                .andExpect(view().name("faculties/allFaculties"))
                .andExpect(model().attribute("faculties", hasSize(1)));
    }

    @Test
    void findFacultiesByName() throws Exception {
        when(facultyService.findByName(any())).thenReturn(faculties);

        mockMvc.perform(get("/faculties/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("faculties/facultiesByName"))
                .andExpect(model().attribute("faculties", hasSize(3)));

        verify(facultyService, atLeastOnce()).findByName(any());
        verifyNoMoreInteractions(facultyService);
    }

    @Test
    void findFacultyById() throws Exception {
        when(facultyService.findById(1L)).thenReturn(faculties.get(0));

        mockMvc.perform(get("/faculties/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("faculties/faculty"))
                .andExpect(model().attribute("faculty", instanceOf(Faculty.class)));

        verify(facultyService, times(1)).findById(1L);
        verifyNoMoreInteractions(facultyService);
    }

    @Test
    void editFaculty() throws Exception {
        when(facultyService.findById(1L)).thenReturn(faculties.get(0));

        mockMvc.perform(get("/faculties/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("faculties/edit"))
                .andExpect(model().attribute("faculty", instanceOf(Faculty.class)));
    }

    @Test
    void createNewFaculty() throws Exception {
        verifyNoMoreInteractions(facultyService);

        mockMvc.perform(get("/faculties/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("faculties/new"))
                .andExpect(model().attribute("faculty", instanceOf(Faculty.class)));
    }

    @Test
    public void deleteFaculty() throws Exception {
        doNothing().when(facultyService).delete(faculties.get(0).getId());

        mockMvc.perform(
                get("/faculties/{id}/delete", faculties.get(0).getId()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/faculties/allFaculties"));

        verify(facultyService, times(1)).delete(faculties.get(0).getId());
    }

    @Test
    void updateFaculty() throws Exception {
        doNothing().when(facultyService).create(any(Faculty.class));

        mockMvc.perform(post("/faculties/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("faculties/edit"))
                .andExpect(model().attribute("faculty", instanceOf(Faculty.class)));
    }
}
