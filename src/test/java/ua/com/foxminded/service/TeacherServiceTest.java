package ua.com.foxminded.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.dao.TeacherRepository;
import ua.com.foxminded.domain.entity.Teacher;
import ua.com.foxminded.domain.service.impl.TeacherServiceImpl;

import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    void crateTeacher() {
        Teacher teacher = new Teacher(1L, "test", "test", "test@gmail.com");

        teacherService.create(teacher);

        verify(teacherRepository, times(1)).save(teacher);
        verifyNoMoreInteractions(teacherRepository);
    }

    @Test
    void deleteTeacherById() {
        Teacher teacher = new Teacher(1L, "test", "test", "test@gmail.com");
        teacherService.delete(1L);

        verify(teacherRepository, times(1)).deleteById(teacher.getId());
    }

    @Test
    void updateTeacher() {
        Teacher teacher = new Teacher(1L, "test", "test", "test@gmail.com");

        teacherService.create(teacher);

        verify(teacherRepository, times(1)).save(teacher);
        verifyNoMoreInteractions(teacherRepository);
    }

    @Test
    void whenAddNewTeachersItShouldReturnAllTeachers() {
        Teacher one = new Teacher(1L, "teacher1", "teacher1", "teacher1@gmail.com");
        Teacher two = new Teacher(2L, "teacher2", "teacher2", "teacher2@gmail.com");
        Teacher three = new Teacher(3L, "teacher3", "teacher3", "teacher3@gmail.com");

        when(teacherRepository.findAll()).thenReturn(Stream.of(one, two, three).collect(Collectors.toList()));

        assertEquals(3, teacherService.findAll().size());
        assertEquals(one, teacherService.findAll().get(0));
        assertEquals(two, teacherService.findAll().get(1));
        verifyNoMoreInteractions(teacherRepository);
    }

    @Test
    void whenInputIdItShouldReturnTeacherById() {
        when(teacherRepository.getOne(anyLong())).thenReturn(new Teacher(1L, "test", "test", "test@gmail.com"));

        assertEquals("test", teacherService.findById(1L).getName());
        assertEquals("test", teacherService.findById(1L).getSurname());
        assertEquals("test@gmail.com", teacherService.findById(1L).getEmail());
        verifyNoMoreInteractions(teacherRepository);
    }
}
