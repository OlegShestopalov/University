package ua.com.foxminded.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.dao.StudentRepository;
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.domain.entity.Student;
import ua.com.foxminded.domain.service.impl.StudentServiceImpl;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;
    private Group group;

    @Test
    void crateStudent() {
        Student student = new Student(1L, group, "test", "test", "Male", 20, "test@gmail.com");

        studentService.create(student);

        verify(studentRepository, times(1)).create(student);
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void deleteStudentById() {
        Student student = new Student(1L, group, "test", "test", "Male", 20, "test@gmail.com");
        studentService.delete(1L);

        verify(studentRepository, times(1)).delete(student.getId());
    }

    @Test
    void updateStudent() {
        Student student = new Student(1L, group, "test", "test", "Male", 20, "test@gmail.com");

        studentService.update(1L, student);

        verify(studentRepository, times(1)).update(student);
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void whenAddNewStudentsItShouldReturnAllStudents() {
        Student one = new Student(1L, group, "test", "test", "Male", 20, "test@gmail.com");
        Student two = new Student(1L, group, "test", "test", "Male", 20, "test@gmail.com");
        Student three = new Student(1L, group, "test", "test", "Male", 20, "test@gmail.com");

        when(studentRepository.findAll()).thenReturn(Stream.of(one, two, three).collect(Collectors.toList()));

        assertEquals(3, studentService.findAll().size());
        assertEquals(one, studentService.findAll().get(0));
        assertEquals(two, studentService.findAll().get(1));
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void whenInputIdItShouldReturnStudentById() {
        when(studentRepository.findById(anyLong())).thenReturn(new Student(1L, group, "test", "test", "Male", 20, "test@gmail.com"));

        assertEquals("test", studentService.findById(1L).getName());
        assertEquals("test", studentService.findById(1L).getSurname());
        assertEquals("test@gmail.com", studentService.findById(1L).getEmail());
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void whenInputNameItShouldReturnStudentByName() {
        when(studentRepository.findByName(anyString())).thenReturn(new Student(1L, group, "test", "test", "Male", 20, "test@gmail.com"));

        assertEquals("test", studentService.findByName("test").getSurname());
        assertEquals("Male", studentService.findByName("test").getSex());
        assertEquals(20, studentService.findByName("test").getAge());
        assertEquals("test@gmail.com", studentService.findByName("test").getEmail());
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void findStudentsInGroup() {
        Student one = new Student(1L, group, "test", "test", "Male", 20, "test@gmail.com");
        Student two = new Student(1L, group, "test", "test", "Male", 20, "test@gmail.com");
        Student three = new Student(1L, group, "test", "test", "Male", 20, "test@gmail.com");

        when(studentRepository.findAllStudentsInGroup(anyLong())).thenReturn(Stream.of(one, two, three).collect(Collectors.toList()));

        assertEquals(3, studentService.findStudentsInGroup(anyLong()).size());
        assertEquals(one, studentService.findStudentsInGroup(anyLong()).get(0));
        assertEquals(two, studentService.findStudentsInGroup(anyLong()).get(1));
        assertEquals(three, studentService.findStudentsInGroup(anyLong()).get(2));
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void findEmailsInGroup() {
        Student one = new Student(1L, group, "test", "test", "Male", 20, "test1@gmail.com");
        Student two = new Student(1L, group, "test", "test", "Male", 20, "test2@gmail.com");
        Student three = new Student(1L, group, "test", "test", "Male", 20, "test3@gmail.com");

        when(studentRepository.findAllEmailsInGroup(anyLong())).thenReturn(Stream.of(one, two, three).collect(Collectors.toList()));

        assertEquals(3, studentService.findEmailsInGroup(anyLong()).size());
        assertEquals(one.getEmail(), studentService.findEmailsInGroup(anyLong()).get(0).getEmail());
        assertEquals(two.getEmail(), studentService.findEmailsInGroup(anyLong()).get(1).getEmail());
        assertEquals(three.getEmail(), studentService.findEmailsInGroup(anyLong()).get(2).getEmail());
        verifyNoMoreInteractions(studentRepository);
    }
}
