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
        facultyRepository.create(faculty);
        Faculty createdFaculty = facultyRepository.findById(faculty.getId());

        assertEquals(faculty, Hibernate.unproxy(createdFaculty));
    }

    @Test
    void deleteFaculty() {
        Faculty facultyToBeDeleted = facultyRepository.findById(1L);
        facultyRepository.delete(facultyToBeDeleted.getId());

        assertEquals(2, facultyRepository.findAll().size());
    }

    @Test
    void updateFaculty() {
        Faculty newFaculty = new Faculty(1L, "UpdatedFaculty");
        facultyRepository.update(newFaculty);
        Faculty updatedFaculty = facultyRepository.findById(1L);

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
        Faculty facultyInDB = facultyRepository.findById(1L);

        assertEquals(faculty, Hibernate.unproxy(facultyInDB));
    }

    @Test
    void findAllFacultiesBySubjectId() {
//        List<Faculty> faculties = facultyDAO.findAllFacultiesBySubjectId(1L);
//
//        assertEquals(1, faculties.size());
    }

    @Test
    void findAllFacultiesByTeacherId() {
//        List<Faculty> faculties = facultyDAO.findAllFacultiesByTeacherId(1L);
//
//        assertEquals(1, faculties.size());
    }
}
