package ua.com.foxminded.dao;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.domain.entity.Teacher;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
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
        Teacher teacher = new Teacher(1L,"Teacher4", "Teacher4", "teacher4@gmail.com");
        teacherRepository.save(teacher);
        Teacher createdTeacher = teacherRepository.getOne(teacher.getId());

        assertEquals(teacher, createdTeacher);
    }

    @Test
    void deleteTeacherFromUniversity() {
        Teacher teacherToBeDeleted = teacherRepository.getOne(1L);
        teacherRepository.deleteById(teacherToBeDeleted.getId());

        assertEquals(2, teacherRepository.findAll().size());
    }

    @Test
    void updateTeacher() {
        Teacher newTeacher = new Teacher(1L, "UpdatedTeacher", "UpdatedTeacher", "UpdatedTeacher@gmail.com");
        teacherRepository.save(newTeacher);
        Teacher updatedTeacher = teacherRepository.getOne(1L);

        assertEquals(newTeacher, updatedTeacher);
    }

    @Test
    void findAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();

        assertEquals(3, teachers.size());
    }

    @Test
    void findTeacherById() {
        Teacher teacher = new Teacher(1L, "Teacher1", "Teacher1", "teacher1@gmail.com");
        Teacher teacherInDB = teacherRepository.getOne(1L);

        assertEquals(teacher, Hibernate.unproxy(teacherInDB));
    }

    @Test
    void findTeachersByPersonalData() {
        Teacher teacher = new Teacher(1L, "Teacher1", "Teacher1", "teacher1@gmail.com");
        List<Teacher> teachers = teacherRepository.findByPersonalData(teacher.getName());

        assertEquals(teacher, teachers.get(0));
    }
}
