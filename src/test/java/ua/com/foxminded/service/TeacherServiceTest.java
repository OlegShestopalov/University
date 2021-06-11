package ua.com.foxminded.service;

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
import ua.com.foxminded.dao.TeacherRepository;
import ua.com.foxminded.domain.entity.Teacher;
import ua.com.foxminded.domain.service.impl.TeacherServiceImpl;

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
public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Test
    void CreateTeacher_WhenAddNewTeacher_ThenCreatedNewTeacherInDB() {
        Teacher teacher = new Teacher(1L, "test", "test", "test@gmail.com");

        teacherService.create(teacher);

        verify(teacherRepository, times(1)).save(teacher);
        verifyNoMoreInteractions(teacherRepository);
    }

    @Test
    void DeleteTeacherById_WhenInputId_ThenDeletedTeacherFromDB() {
        Teacher teacher = new Teacher(1L, "test", "test", "test@gmail.com");

        teacherService.delete(1L);

        verify(teacherRepository, times(1)).deleteById(teacher.getId());
    }

    @Test
    void UpdateTeacher_WhenChangeDataAboutTeacher_ThenSaveUpdatedTeacher() {
        Teacher teacher = new Teacher(1L, "test", "test", "test@gmail.com");

        teacherService.create(teacher);

        verify(teacherRepository, times(1)).save(teacher);
        verifyNoMoreInteractions(teacherRepository);
    }

    @Test
    void FindAllTeachersByPages_WhenFindAll_ThenReturnedPagesWithTeachers() {
        int pageNumber = 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("name"));
        Teacher one = new Teacher(1L, "teacher1", "teacher1", "teacher1@gmail.com");
        Page<Teacher> teachers = new PageImpl<>(Collections.singletonList(one));

        when(teacherRepository.findAll(pageable)).thenReturn(teachers);

        assertEquals(1, teacherService.findAll(pageNumber).getTotalPages());
        assertEquals(1, teacherService.findAll(pageNumber).getTotalElements());
        assertEquals(one, teacherService.findAll(pageNumber).toList().get(0));
        verifyNoMoreInteractions(teacherRepository);
    }

    @Test
    void FindTeacherById_WhenInputId_ThenReturnedTeacherById() {
        when(teacherRepository.getOne(anyLong())).thenReturn(new Teacher(1L, "test", "test", "test@gmail.com"));

        assertEquals("test", teacherService.findById(1L).getName());
        assertEquals("test", teacherService.findById(1L).getSurname());
        assertEquals("test@gmail.com", teacherService.findById(1L).getEmail());
        verifyNoMoreInteractions(teacherRepository);
    }

    @Test
    void FindTeachersByPersonalData_WhenInputNameOrSurname_ThenReturnedStudentsByName() {
        List<Teacher> teachers = new ArrayList<>(Collections.singleton(new Teacher(1L, "test", "test", "test@gmail.com")));

        when(teacherRepository.findByPersonalData("test")).thenReturn(teachers);

        assertEquals(teachers, teacherService.findByPersonalData("test"));
        assertEquals(1, teacherService.findByPersonalData("test").size());
        verifyNoMoreInteractions(teacherRepository);
    }
}
