package ua.com.foxminded.dao;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.domain.entity.Subject;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/scripts/schema.sql", "/scripts/data.sql"})
@ActiveProfiles("test")
public class SubjectRepositoryTest {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectRepositoryTest(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Test
    void createSubject() {
        Subject subject = new Subject("TEST", "TEST");
        subjectRepository.create(subject);
        Subject createdSubject = subjectRepository.findById(subject.getId());

        assertEquals(subject, Hibernate.unproxy(createdSubject));
    }

    @Test
    void deleteSubject() {
        Subject subjectToBeDeleted = subjectRepository.findById(1L);
        subjectRepository.delete(subjectToBeDeleted.getId());

        assertEquals(2, subjectRepository.findAll().size());
    }

    @Test
    void updateSubject() {
        Subject newSubject = new Subject(1L, "UpdatedSubject", "UpdatedSubject");

        subjectRepository.update(newSubject);
        Subject updatedSubject = subjectRepository.findById(1L);

        assertEquals(newSubject, Hibernate.unproxy(updatedSubject));
    }

    @Test
    void findAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();

        assertEquals(3, subjects.size());
    }

    @Test
    void findSubjectById() {
        Subject subject = new Subject(1L, "Subject1", "Subject1");
        Subject subjectInDB = subjectRepository.findById(1L);

        assertEquals(subject, Hibernate.unproxy(subjectInDB));
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
