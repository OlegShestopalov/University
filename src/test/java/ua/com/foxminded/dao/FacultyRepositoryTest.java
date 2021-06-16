package ua.com.foxminded.dao;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.domain.entity.Faculty;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/scripts/schema.sql", "/scripts/data.sql"})
@ActiveProfiles("test")
public class FacultyRepositoryTest {

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyRepositoryTest(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Test
    void shouldCreateNewFacultyInDBWhenAddNewFaculty() {
        Faculty faculty = new Faculty(1L, "TEST");
        facultyRepository.save(faculty);
        Faculty createdFaculty = facultyRepository.getOne(faculty.getId());

        assertEquals(faculty, createdFaculty);
    }

    @Test
    void shouldDeleteFacultyFromDBWhenInputId() {
        Faculty facultyToBeDeleted = facultyRepository.getOne(1L);
        facultyRepository.deleteById(facultyToBeDeleted.getId());

        assertEquals(2, facultyRepository.findAll().size());
    }

    @Test
    void shouldSaveUpdatedFacultyWhenChangeDataAboutFaculty() {
        Faculty newFaculty = new Faculty(1L, "UpdatedFaculty");
        facultyRepository.save(newFaculty);
        Faculty updatedFaculty = facultyRepository.getOne(1L);

        assertEquals(newFaculty, updatedFaculty);
    }

    @Test
    void shouldReturnListOfFacultiesWhenFindAll() {
        List<Faculty> faculties = facultyRepository.findAll();

        assertEquals(3, faculties.size());
    }

    @Test
    void shouldReturnFacultyByIdWhenInputId() {
        Faculty faculty = new Faculty(1L, "Electronics");
        Faculty facultyInDB = facultyRepository.getOne(1L);

        assertEquals(faculty, Hibernate.unproxy(facultyInDB));
    }

    @Test
    void shouldReturnListOfFacultiesWhenInputName() {
        Faculty faculty = new Faculty(1L, "Electronics");
        List<Faculty> faculties = facultyRepository.findByName(faculty.getName());
        
        assertEquals(faculty, faculties.get(0));
    }
}
