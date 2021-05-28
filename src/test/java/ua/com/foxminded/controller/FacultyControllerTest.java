package ua.com.foxminded.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.com.foxminded.domain.controller.FacultyController;
import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.domain.service.FacultyService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

public class FacultyControllerTest {

    private List<Faculty> faculties;
    private MockMvc mockMvc;

    @Mock
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
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
        when(facultyService.findAll()).thenReturn(faculties);

        mockMvc.perform(get("/faculties/allFaculties"))
                .andExpect(status().isOk())
                .andExpect(view().name("faculties/allFaculties"))
                .andExpect(model().attribute("faculties", hasSize(3)));

        verify(facultyService, atLeastOnce()).findAll();
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
//        doNothing().when(facultyService).create(any(Faculty.class));
//        assertEquals(facultyController.update(faculties.get(0)), "redirect:/faculties/allFaculties");
    }
}
