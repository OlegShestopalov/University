package ua.com.foxminded.dao;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ua.com.foxminded.config.SpringConfig;
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.domain.entity.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfig.class, loader = AnnotationConfigContextLoader.class)
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
    void createStudent() {
        Group group = groupRepository.findById(1L);
        Student student = new Student(group, "Student4", "Student4", "Male", 20, "student4@gmail.com");
        studentRepository.create(student);
        Student createdStudent = studentRepository.findById(student.getId());

        assertEquals(student, Hibernate.unproxy(createdStudent));
    }

    @Test
    void deleteStudent() {
        Student studentToBeDeleted = studentRepository.findById(1L);
        studentRepository.delete(studentToBeDeleted.getId());

        assertEquals(2, studentRepository.findAll().size());
    }

    @Test
    void updateStudent() {
        Group group = groupRepository.findById(1L);
        Student newStudent = new Student(1L, group, "UpdatedStudent", "UpdatedStudent", "Male", 25, "UpdatedStudent@gmail.com");
        studentRepository.update(newStudent);
        Student updatedStudent = studentRepository.findById(1L);

        assertEquals(newStudent, Hibernate.unproxy(updatedStudent));
    }

    @Test
    void findAllStudents() {
        List<Student> students = studentRepository.findAll();

        assertEquals(3, students.size());
    }

    @Test
    void findStudentById() {
        Group group = groupRepository.findById(1L);
        Student student = new Student(1L, group, "Student1", "Student1", "Male", 20, "student1@gmail.com");
        Student studentInDb = studentRepository.findById(1L);

        assertEquals(student, Hibernate.unproxy(studentInDb));
    }

    @Test
    void findStudentByName() {
//        Student student = studentDAO.findByName("Student3");
//
//        assertEquals("Student3", student.getName());
//        assertTrue(true, student.getName());
    }

    @Test
    void findAllStudentsInGroup() {
//        List<Student> students = studentDAO.findAllStudentsInGroup(1L);
//
//        assertEquals(1, students.size());
//        assertEquals("Student1", students.get(0).getName());
    }

    @Test
    void findAllEmailsInGroup() {
//        List<Student> catalogEmails = studentDAO.findAllEmailsInGroup(1L);
//
//        assertEquals(1, catalogEmails.size());
    }
}
