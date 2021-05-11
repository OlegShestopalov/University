package ua.com.foxminded.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.dao.FacultyDAO;
import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.domain.service.impl.FacultyServiceImpl;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {

    @Mock
    private FacultyDAO facultyDAO;

    @InjectMocks
    private FacultyServiceImpl facultyService;

    @Test
    void crateFaculty() {
        Faculty faculty = new Faculty(1L, "test");

        facultyService.create(faculty);

        verify(facultyDAO, times(1)).create(faculty);
        verifyNoMoreInteractions(facultyDAO);
    }

    @Test
    void deleteFacultyById() {
        Faculty faculty = new Faculty(1L, "test");
        facultyService.delete(1L);

        verify(facultyDAO, times(1)).delete(faculty.getId());
    }

    @Test
    void updateFaculty() {
        Faculty faculty = new Faculty(1L, "test");

        facultyService.update(1L, faculty);

        verify(facultyDAO, times(1)).update(faculty);
        verifyNoMoreInteractions(facultyDAO);
    }

    @Test
    void whenAddNewFacultiesItShouldReturnAllFaculties() {
        Faculty one = new Faculty(1L, "faculty1");
        Faculty two = new Faculty(2L, "faculty2");
        Faculty three = new Faculty(3L, "faculty3");

        when(facultyDAO.findAll()).thenReturn(Stream.of(one, two, three).collect(Collectors.toList()));

        assertEquals(3, facultyService.findAll().size());
        assertEquals(one, facultyService.findAll().get(0));
        assertEquals(two, facultyService.findAll().get(1));
        verifyNoMoreInteractions(facultyDAO);
    }

    @Test
    void whenInputIdItShouldReturnFacultyById() {
        when(facultyDAO.findById(anyLong())).thenReturn(new Faculty(1L, "test"));

        assertEquals("test", facultyService.findById(1L).getName());
        verifyNoMoreInteractions(facultyDAO);
    }
}
