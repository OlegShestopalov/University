package ua.com.foxminded.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ua.com.foxminded.config.SpringConfig;
import ua.com.foxminded.domain.entity.Teacher;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfig.class, loader = AnnotationConfigContextLoader.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/scripts/schema.sql", "/scripts/data.sql"})
@ActiveProfiles("test")
public class TeacherDAOTest {

    private final TeacherDAO teacherDAO;

    @Autowired
    public TeacherDAOTest(TeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    @Test
    void createTeacher() {
        Teacher teacher = new Teacher("Teacher4", "Teacher4", "teacher4@gmail.com");
        teacherDAO.create(teacher);
        Teacher createdTeacher = teacherDAO.findById(teacher.getId());

        assertEquals(teacher.getName(), createdTeacher.getName());
    }

    @Test
    void deleteTeacherFromUniversity() {
        Teacher teacherToBeDeleted = teacherDAO.findById(1L);
        teacherDAO.delete(teacherToBeDeleted.getId());

        assertEquals(2, teacherDAO.findAll().size());
    }

    @Test
    void updateTeacher() {
        Teacher teacherToBeUpdated = teacherDAO.findById(1L);
        Teacher newTeacher = new Teacher(1L, "UpdatedTeacher", "UpdatedTeacher", "UpdatedTeacher@gmail.com");

        teacherDAO.update(newTeacher);
        Teacher updatedTeacher = teacherDAO.findById(1L);

        assertEquals(newTeacher.getId(), updatedTeacher.getId());
        assertEquals(newTeacher.getName(), updatedTeacher.getName());
        assertEquals(newTeacher.getSurname(), updatedTeacher.getSurname());
        assertEquals(newTeacher.getEmail(), updatedTeacher.getEmail());
    }

    @Test
    void findAllTeachers() {
        List<Teacher> teachers = teacherDAO.findAll();

        assertEquals(3, teachers.size());
    }

    @Test
    void findTeacherById() {
        Teacher teacher = teacherDAO.findById(1L);

        assertEquals("Teacher1", teacher.getName());
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
