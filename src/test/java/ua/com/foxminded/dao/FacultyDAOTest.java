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
import ua.com.foxminded.domain.entity.Faculty;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfig.class, loader = AnnotationConfigContextLoader.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/scripts/schema.sql", "/scripts/data.sql"})
@ActiveProfiles("test")
public class FacultyDAOTest {

    private final FacultyDAO facultyDAO;

    @Autowired
    public FacultyDAOTest(FacultyDAO facultyDAO) {
        this.facultyDAO = facultyDAO;
    }

    @Test
    void createFaculty() {
        Faculty faculty = new Faculty("TEST");
        facultyDAO.create(faculty);
        Faculty createdFaculty = facultyDAO.findById(faculty.getId());

        assertEquals(faculty.getName(), createdFaculty.getName());
    }

    @Test
    void daleteFaculty() {
        Faculty facultyToBeDeleted = facultyDAO.findById(1L);
        facultyDAO.delete(facultyToBeDeleted.getId());

        assertEquals(2, facultyDAO.findAll().size());
    }

    @Test
    void updateFaculty() {
        Faculty facultyToBeUpdated = facultyDAO.findById(1L);
        Faculty newFaculty = new Faculty(1L, "UpdatedFaculty");

        facultyDAO.update(newFaculty);
        Faculty updatedFaculty = facultyDAO.findById(1L);

        assertEquals(newFaculty.getId(), updatedFaculty.getId());
        assertEquals(newFaculty.getName(), updatedFaculty.getName());
    }

    @Test
    void findAllFaculties() {
        List<Faculty> faculties = facultyDAO.findAll();

        assertEquals(3, faculties.size());
    }

    @Test
    void findFacultyById() {
        Faculty faculty = facultyDAO.findById(1L);

        assertEquals("Electronics", faculty.getName());
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
