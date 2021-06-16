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
import ua.com.foxminded.dao.StudentRepository;
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.domain.entity.Student;
import ua.com.foxminded.domain.service.impl.StudentServiceImpl;

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
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private Group group;

    @Test
    void shouldCreateNewStudentInDBWhenAddNewStudent() {
        Student student = new Student(1L, group, "test", "test", "Male", 20, "test@gmail.com");

        studentService.create(student);

        verify(studentRepository, times(1)).save(student);
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void shouldDeleteStudentFromDBWhenInputId() {
        Student student = new Student(1L, group, "test", "test", "Male", 20, "test@gmail.com");

        studentService.delete(1L);

        verify(studentRepository, times(1)).deleteById(student.getId());
    }

    @Test
    void shouldSaveUpdatedStudentWhenChangeDataAboutStudent() {
        Student student = new Student(1L, group, "test", "test", "Male", 20, "test@gmail.com");

        studentService.create(student);

        verify(studentRepository, times(1)).save(student);
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void shouldReturnPagesWithStudentsWhenFindAll() {
        int pageNumber = 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("name"));
        Student one = new Student(1L, group, "test", "test", "Male", 20, "test@gmail.com");
        Page<Student> students = new PageImpl<>(Collections.singletonList(one));

        when(studentRepository.findAll(pageable)).thenReturn(students);

        assertEquals(1, studentService.findAll(pageNumber).getTotalPages());
        assertEquals(1, studentService.findAll(pageNumber).getTotalElements());
        assertEquals(one, studentService.findAll(pageNumber).toList().get(0));
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void shouldReturnStudentByIdWhenInputId() {
        when(studentRepository.getOne(anyLong())).thenReturn(new Student(1L, group, "test", "test", "Male", 20, "test@gmail.com"));

        assertEquals("test", studentService.findById(1L).getName());
        assertEquals("test", studentService.findById(1L).getSurname());
        assertEquals("test@gmail.com", studentService.findById(1L).getEmail());
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void shouldReturnStudentsByNameWhenInputNameOrSurname() {
        List<Student> students = new ArrayList<>(Collections.singleton(new Student(1L, group, "test", "test", "Male", 20, "test@gmail.com")));

        when(studentRepository.findByNameOrSurnameOrGroup("test")).thenReturn(students);

        assertEquals(students, studentService.findByPersonalData("test"));
        assertEquals(1, studentService.findByPersonalData("test").size());
        verifyNoMoreInteractions(studentRepository);
    }
}
