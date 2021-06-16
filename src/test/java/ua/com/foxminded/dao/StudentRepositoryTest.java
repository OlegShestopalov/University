package ua.com.foxminded.dao;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.domain.entity.Course;
import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.domain.entity.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/scripts/schema.sql", "/scripts/data.sql"})
@ActiveProfiles("test")
public class StudentRepositoryTest {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    @Autowired
    StudentRepositoryTest(StudentRepository studentRepository, GroupRepository groupRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    @Test
    void shouldCreateNewStudentInDBWhenAddNewStudent() {
        Group group = groupRepository.getOne(1L);
        Student student = new Student(group, "Student4", "Student4", "Male", 20, "student4@gmail.com");
        studentRepository.save(student);
        Student createdStudent = studentRepository.getOne(student.getId());

        assertEquals(student, createdStudent);
    }

    @Test
    void shouldDeleteStudentFromDBWhenInputId() {
        Student studentToBeDeleted = studentRepository.getOne(1L);
        studentRepository.deleteById(studentToBeDeleted.getId());

        assertEquals(2, studentRepository.findAll().size());
    }

    @Test
    void shouldSaveUpdatedStudentWhenChangeDataAboutStudent() {
        Faculty faculty = new Faculty(1L, "Electronics");
        Course course = new Course(1L, "first");
        Group group = new Group(1L, "AAAA", faculty, course);
        Student newStudent = new Student(1L, group, "UpdatedStudent", "UpdatedStudent", "Male", 25, "UpdatedStudent@gmail.com");
        studentRepository.save(newStudent);
        Student updatedStudent = studentRepository.getOne(1L);

        assertNotNull(updatedStudent);
        assertEquals(newStudent, updatedStudent);
    }

    @Test
    void shouldReturnListOfStudentsWhenFindAll() {
        List<Student> students = studentRepository.findAll();

        assertEquals(3, students.size());
    }

    @Test
    void shouldReturnStudentByIdWhenInputId() {
        Faculty faculty = new Faculty(1L, "Electronics");
        Course course = new Course(1L, "first");
        Group group = new Group(1L, "AAAA", faculty, course);
        Student student = new Student(1L, group, "Student1", "Student1", "Male", 20, "student1@gmail.com");
        Student studentInDb = studentRepository.getOne(student.getId());

        assertEquals(student, Hibernate.unproxy(studentInDb));
    }

    @Test
    void shouldReturnStudentsByNameWhenInputNameOrSurname() {
        Faculty faculty = new Faculty(1L, "Electronics");
        Course course = new Course(1L, "first");
        Group group = new Group(1L, "AAAA", faculty, course);
        Student student = new Student(1L, group, "Student1", "Student1", "Male", 20, "student1@gmail.com");
        List<Student> students = studentRepository.findByNameOrSurnameOrGroup(student.getName());

        assertEquals(student, students.get(0));
    }
}
