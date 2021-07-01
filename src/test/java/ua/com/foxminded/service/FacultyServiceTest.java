package ua.com.foxminded.service;

import javassist.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ua.com.foxminded.dao.FacultyRepository;
import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.domain.service.impl.FacultyServiceImpl;
import ua.com.foxminded.exception.AlreadyExistException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {

    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private FacultyServiceImpl facultyService;

    @Test
    void shouldCreateNewFacultyInDBWhenAddNewFaculty() {
        Faculty faculty = new Faculty(1L, "test");

        facultyRepository.save(faculty);

        verify(facultyRepository, times(1)).save(faculty);
    }

    @Test
    void shouldDeleteFacultyFromDBWhenInputId() throws NotFoundException {
        Faculty faculty = new Faculty(1L, "test");

        facultyRepository.deleteById(1L);

        verify(facultyRepository, times(1)).deleteById(faculty.getId());
    }

    @Test
    void shouldSaveUpdatedFacultyWhenChangeDataAboutFaculty() throws NotFoundException {
        Faculty faculty = new Faculty(1L, "test");

        facultyService.update(faculty);

        verify(facultyRepository, times(1)).save(faculty);
    }

    @Test
    void shouldReturnPagesWithFacultiesWhenFindAll() throws NotFoundException {
        int pageNumber = 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("name"));
        Faculty one = new Faculty(1L, "faculty1");
        Page<Faculty> faculties = new PageImpl<>(Collections.singletonList(one));

        when(facultyRepository.findAll(pageable)).thenReturn(faculties);

        assertEquals(1, facultyService.findAll(pageNumber).getTotalPages());
        assertEquals(1, facultyService.findAll(pageNumber).getTotalElements());
        assertEquals(one, facultyService.findAll(pageNumber).toList().get(0));
        verifyNoMoreInteractions(facultyRepository);
    }

    @Test
    void shouldReturnFacultyByIdWhenInputId() throws NotFoundException {
        when(facultyRepository.findById(anyLong())).thenReturn(java.util.Optional.of(new Faculty(1L, "test")));

        assertEquals("test", facultyService.findById(1L).getName());
        verifyNoMoreInteractions(facultyRepository);
    }

    @Test
    void shouldReturnListOfFacultiesWhenInputName() throws NotFoundException {
        List<Faculty> faculties = new ArrayList<>(Collections.singleton(new Faculty(1L, "test")));

        when(facultyRepository.findByName("test")).thenReturn(faculties);

        assertEquals(faculties, facultyService.findByName("test"));
        assertEquals(1, facultyService.findByName("test").size());
        verifyNoMoreInteractions(facultyRepository);
    }
}
