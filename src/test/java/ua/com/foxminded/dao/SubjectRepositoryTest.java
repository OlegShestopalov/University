package ua.com.foxminded.dao;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.domain.entity.Subject;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/scripts/schema.sql", "/scripts/data.sql"})
@ActiveProfiles("test")
public class SubjectRepositoryTest {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectRepositoryTest(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Test
    void shouldCreateNewSubjectInDBWhenAddNewSubject() {
        Subject subject = new Subject(1L, "TEST", "TEST");
        subjectRepository.save(subject);
        Subject createdSubject = subjectRepository.getOne(subject.getId());

        assertEquals(subject, createdSubject);
    }

    @Test
    void shouldDeleteSubjectFromDBWhenInputId() {
        Subject subjectToBeDeleted = subjectRepository.getOne(1L);
        subjectRepository.deleteById(subjectToBeDeleted.getId());

        assertEquals(2, subjectRepository.findAll().size());
    }

    @Test
    void shouldSaveUpdatedSubjectWhenChangeDataAboutSubject() {
        Subject newSubject = new Subject(1L, "UpdatedSubject", "UpdatedSubject");
        subjectRepository.save(newSubject);
        Subject updatedSubject = subjectRepository.getOne(1L);

        assertEquals(newSubject, updatedSubject);
    }

    @Test
    void shouldReturnListSubjectsWhenFindAll() {
        List<Subject> subjects = subjectRepository.findAll();

        assertEquals(3, subjects.size());
    }

    @Test
    void shouldReturnSubjectByIdWhenInputId() {
        Subject subject = new Subject(1L, "Subject1", "Subject1");
        Subject subjectInDB = subjectRepository.getOne(1L);

        assertEquals(subject, Hibernate.unproxy(subjectInDB));
    }
}
