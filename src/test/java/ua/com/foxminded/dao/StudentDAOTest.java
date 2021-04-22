package ua.com.foxminded.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ua.com.foxminded.config.SpringConfig;
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.domain.entity.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfig.class, loader = AnnotationConfigContextLoader.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/scripts/schema.sql", "/scripts/data.sql"})
public class StudentDAOTest {

    private final StudentDAO studentDAO;
    private final GroupDAO groupDAO;

    @Autowired
    StudentDAOTest(StudentDAO studentDAO, GroupDAO groupDAO) {
        this.studentDAO = studentDAO;
        this.groupDAO = groupDAO;
    }

    @Test
    void createStudent() {
        Group group = groupDAO.findById(1L);
        Student student = new Student(4L, group, "Student4", "Student4", "Male", 20, "student4@gmail.com");
        studentDAO.create(student);
        Student createdStudent = studentDAO.findById(student.getId());

        assertEquals(student, createdStudent);
    }

    @Test
    void deleteStudent() {
        Student studentToBeDeleted = studentDAO.findById(1L);
        studentDAO.delete(studentToBeDeleted.getId());

        assertEquals(2, studentDAO.findAll().size());
    }

    @Test
    void updateStudent() {
        Student studentToBeUpdated = studentDAO.findById(1L);
        Group group = groupDAO.findById(1L);
        Student newStudent = new Student(1L, group, "UpdatedStudent", "UpdatedStudent", "Male", 25, "UpdatedStudent@gmail.com");
        studentDAO.update(studentToBeUpdated.getId(), newStudent);
        Student updatedStudent = studentDAO.findById(1L);

        assertEquals(newStudent, updatedStudent);
    }

    @Test
    void findAllStudents() {
        List<Student> students = studentDAO.findAll();

        assertEquals(3, students.size());
    }

    @Test
    void findStudentById() {
        Student student = studentDAO.findById(1L);

        assertEquals("Student1", student.getName());
    }

    @Test
    void findStudentByName() {
        Student student = studentDAO.findByName("Student3");

        assertEquals("Student3", student.getName());
        assertTrue(true, student.getName());
    }

    @Test
    void findAllStudentsInGroup() {
        List<Student> students = studentDAO.findAllStudentsInGroup(1L);

        assertEquals(1, students.size());
        assertEquals("Student1", students.get(0).getName());
    }

    @Test
    void findAllEmailsInGroup() {
        List<Student> catalogEmails = studentDAO.findAllEmailsInGroup(1L);

        assertEquals(1, catalogEmails.size());
    }
}
