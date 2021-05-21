package ua.com.foxminded.dao;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.domain.entity.Faculty;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/scripts/schema.sql", "/scripts/data.sql"})
@ActiveProfiles("test")
public class FacultyRepositoryTest {

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyRepositoryTest(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Test
    void createFaculty() {
        Faculty faculty = new Faculty("TEST");

        facultyRepository.save(faculty);
        Faculty createdFaculty = facultyRepository.getOne(faculty.getId());

        assertEquals(faculty, Hibernate.unproxy(createdFaculty));
    }

    @Test
    void deleteFaculty() {
        Faculty facultyToBeDeleted = facultyRepository.getOne(1L);

        facultyRepository.deleteById(facultyToBeDeleted.getId());

        assertEquals(2, facultyRepository.findAll().size());
    }

    @Test
    void updateFaculty() {
        Faculty newFaculty = new Faculty(1L, "UpdatedFaculty");

        facultyRepository.save(newFaculty);
        Faculty updatedFaculty = facultyRepository.getOne(1L);

        assertEquals(newFaculty, Hibernate.unproxy(updatedFaculty));
    }

    @Test
    void findAllFaculties() {
        List<Faculty> faculties = facultyRepository.findAll();

        assertEquals(3, faculties.size());
    }

    @Test
    void findFacultyById() {
        Faculty faculty = new Faculty(1L, "Electronics");

        Faculty facultyInDB = facultyRepository.getOne(1L);

        assertEquals(faculty, Hibernate.unproxy(facultyInDB));
    }
}
