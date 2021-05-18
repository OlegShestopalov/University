package ua.com.foxminded.dao;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.domain.entity.Teacher;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/scripts/schema.sql", "/scripts/data.sql"})
@ActiveProfiles("test")
public class TeacherRepositoryTest {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherRepositoryTest(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Test
    void createTeacher() {
        Teacher teacher = new Teacher("Teacher4", "Teacher4", "teacher4@gmail.com");
        teacherRepository.create(teacher);
        Teacher createdTeacher = teacherRepository.findById(teacher.getId());

        assertEquals(teacher, Hibernate.unproxy(createdTeacher));
    }

    @Test
    void deleteTeacherFromUniversity() {
        Teacher teacherToBeDeleted = teacherRepository.findById(1L);
        teacherRepository.delete(teacherToBeDeleted.getId());

        assertEquals(2, teacherRepository.findAll().size());
    }

    @Test
    void updateTeacher() {
        Teacher newTeacher = new Teacher(1L, "UpdatedTeacher", "UpdatedTeacher", "UpdatedTeacher@gmail.com");

        teacherRepository.update(newTeacher);
        Teacher updatedTeacher = teacherRepository.findById(1L);

        assertEquals(newTeacher, Hibernate.unproxy(updatedTeacher));
    }

    @Test
    void findAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();

        assertEquals(3, teachers.size());
    }

    @Test
    void findTeacherById() {
        Teacher teacher = new Teacher(1L, "Teacher1", "Teacher1", "teacher1@gmail.com");
        Teacher teacherInDB = teacherRepository.findById(1L);

        assertEquals(teacher, Hibernate.unproxy(teacherInDB));
    }

    @Test
    void findAllTeachersBySubjectId() {
//        List<Teacher> teachers = teacherDAO.findAllTeachersBySubjectId(1L);
//
//        assertEquals(1, teachers.size());
    }

    @Test
    void findAllTeachersInFaculty() {
//        List<Teacher> teachers = teacherDAO.findAllTeachersInFaculty(1L);
//
//        assertEquals(1, teachers.size());
//        assertEquals("Teacher1", teachers.get(0).getName());
    }
}
