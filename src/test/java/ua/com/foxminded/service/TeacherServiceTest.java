package ua.com.foxminded.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.dao.TeacherDAO;
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
    private TeacherDAO teacherDAO;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Test
    void crateTeacher() {
        Teacher teacher = new Teacher(1L, "test", "test", "test@gmail.com");

        teacherService.create(teacher);

        verify(teacherDAO, times(1)).create(teacher);
        verifyNoMoreInteractions(teacherDAO);
    }

    @Test
    void deleteTeacherById() {
        Teacher teacher = new Teacher(1L, "test", "test", "test@gmail.com");
        teacherService.delete(1L);

        verify(teacherDAO, times(1)).delete(teacher.getId());
    }

    @Test
    void updateTeacher() {
        Teacher teacher = new Teacher(1L, "test", "test", "test@gmail.com");

        teacherService.update(1L, teacher);

        verify(teacherDAO, times(1)).update(teacher);
        verifyNoMoreInteractions(teacherDAO);
    }

    @Test
    void whenAddNewTeachersItShouldReturnAllTeachers() {
        Teacher one = new Teacher(1L, "teacher1", "teacher1", "teacher1@gmail.com");
        Teacher two = new Teacher(2L, "teacher2", "teacher2", "teacher2@gmail.com");
        Teacher three = new Teacher(3L, "teacher3", "teacher3", "teacher3@gmail.com");

        when(teacherDAO.findAll()).thenReturn(Stream.of(one, two, three).collect(Collectors.toList()));

        assertEquals(3, teacherService.findAll().size());
        assertEquals(one, teacherService.findAll().get(0));
        assertEquals(two, teacherService.findAll().get(1));
        verifyNoMoreInteractions(teacherDAO);
    }

    @Test
    void whenInputIdItShouldReturnTeacherById() {
        when(teacherDAO.findById(anyLong())).thenReturn(new Teacher(1L, "test", "test", "test@gmail.com"));

        assertEquals("test", teacherService.findById(1L).getName());
        assertEquals("test", teacherService.findById(1L).getSurname());
        assertEquals("test@gmail.com", teacherService.findById(1L).getEmail());
        verifyNoMoreInteractions(teacherDAO);
    }

    @Test
    void findTeachersBySubject() {
        Teacher one = new Teacher(1L, "teacher1", "teacher1", "teacher1@gmail.com");
        Teacher two = new Teacher(2L, "teacher2", "teacher2", "teacher2@gmail.com");
        Teacher three = new Teacher(3L, "teacher3", "teacher3", "teacher3@gmail.com");

        when(teacherDAO.findAllTeachersBySubjectId(anyLong())).thenReturn(Stream.of(one, two, three).collect(Collectors.toList()));

        assertEquals(3, teacherService.findTeachersBySubject(anyLong()).size());
        assertEquals(one, teacherService.findTeachersBySubject(anyLong()).get(0));
        assertEquals(two, teacherService.findTeachersBySubject(anyLong()).get(1));
        verifyNoMoreInteractions(teacherDAO);
    }

    @Test
    void findTeachersInFaculty() {
        Teacher one = new Teacher(1L, "teacher1", "teacher1", "teacher1@gmail.com");
        Teacher two = new Teacher(2L, "teacher2", "teacher2", "teacher2@gmail.com");
        Teacher three = new Teacher(3L, "teacher3", "teacher3", "teacher3@gmail.com");

        when(teacherDAO.findAllTeachersInFaculty(anyLong())).thenReturn(Stream.of(one, two, three).collect(Collectors.toList()));

        assertEquals(3, teacherService.findTeachersInFaculty(anyLong()).size());
        assertEquals(one, teacherService.findTeachersInFaculty(anyLong()).get(0));
        assertEquals(two, teacherService.findTeachersInFaculty(anyLong()).get(1));
        assertEquals(three, teacherService.findTeachersInFaculty(anyLong()).get(2));
        verifyNoMoreInteractions(teacherDAO);
    }
}
