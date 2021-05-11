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
import ua.com.foxminded.domain.entity.Subject;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfig.class, loader = AnnotationConfigContextLoader.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/scripts/schema.sql", "/scripts/data.sql"})
@ActiveProfiles("test")
public class SubjectDAOTest {

    private final SubjectDAO subjectDAO;

    @Autowired
    public SubjectDAOTest(SubjectDAO subjectDAO) {
        this.subjectDAO = subjectDAO;
    }

    @Test
    void createSubject() {
        Subject subject = new Subject("TEST", "TEST");
        subjectDAO.create(subject);
        Subject createdSubject = subjectDAO.findById(subject.getId());

        assertEquals(subject.getName(), createdSubject.getName());
    }

    @Test
    void deleteSubject() {
        Subject subjectToBeDeleted = subjectDAO.findById(1L);
        subjectDAO.delete(subjectToBeDeleted.getId());

        assertEquals(2, subjectDAO.findAll().size());
    }

    @Test
    void updateSubject() {
        Subject newSubject = new Subject(1L, "UpdatedSubject", "UpdatedSubject");

        subjectDAO.update(newSubject);
        Subject updatedSubject = subjectDAO.findById(1L);

        assertEquals(newSubject.getId(), updatedSubject.getId());
        assertEquals(newSubject.getName(), updatedSubject.getName());
        assertEquals(newSubject.getDescription(), updatedSubject.getDescription());
    }

    @Test
    void findAllSubjects() {
        List<Subject> subjects = subjectDAO.findAll();

        assertEquals(3, subjects.size());
    }

    @Test
    void findSubjectById() {
        Subject subject = subjectDAO.findById(1L);

        assertEquals("Subject1", subject.getName());
    }

    @Test
    void findAllTeacherSubjects() {
//        List<Subject> subjects = subjectDAO.findAllTeacherSubjects(1L);
//
//        assertEquals(1, subjects.size());
    }

    @Test
    void findAllFacultySubjects() {
//        List<Subject> subjects = subjectDAO.findAllFacultySubjects(1L);
//
//        assertEquals(1, subjects.size());
    }
}
