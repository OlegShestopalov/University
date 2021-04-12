package ua.com.foxminded.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.dao.FacultyDAO;
import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.exception.QueryNotExecuteException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Repository
public class FacultyDAOImpl implements FacultyDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyDAOImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_FACULTY = "INSERT INTO faculty VALUES (?)";
    private static final String DELETE_FACULTY = "DELETE FROM faculty WHERE id=?";
    private static final String UPDATE_FACULTY = "UPDATE faculty SET name=? WHERE id=?";
    private static final String FIND_FACULTY_BY_ID = "SELECT * FROM faculty WHERE id=?";
    private static final String FIND_ALL_FACULTIES = "SELECT * FROM faculty";
    private static final String FIND_FACULTIES_BY_SUBJECT_ID = "SELECT faculty.name FROM faculty " +
            "INNER JOIN subject_faculty ON faculty.id=subject_faculty.faculty_id " +
            "INNER JOIN subject ON subject.id=subject_faculty.subject_id " +
            "WHERE subject.id=?";
    private static final String FIND_FACULTIES_BY_TEACHER_ID = "SELECT faculty.name FROM faculty " +
            "INNER JOIN teacher_faculty ON faculty.id=teacher_faculty.faculty_id " +
            "INNER JOIN teacher ON teacher.id=teacher_faculty.teacher_id " +
            "WHERE teacher.id=?";

    public FacultyDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(final Faculty faculty) {
        LOGGER.debug("Running a method for add faculty. Faculty details: {}", faculty);
        try {
            jdbcTemplate.update(INSERT_FACULTY, faculty.getName());
        } catch (DataAccessException e) {
            String message = format("Couldn't add Faculty='%s'", faculty);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Faculty was successfully saved. Faculty details: {}", faculty.getId());
    }

    @Override
    public void removeFaculty(final Long id) {
        LOGGER.debug("Deleting a faculty with ID={}", id);
        try {
            jdbcTemplate.update(DELETE_FACULTY, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Faculty with ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Faculty was successfully deleted. Faculty details: {}", id);
    }

    @Override
    public void update(final Long id, final Faculty faculty) {
        LOGGER.debug("Changing a faculty with ID={}", id);
        try {
            jdbcTemplate.update(UPDATE_FACULTY, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Faculty with ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Faculty was successfully updated. Faculty details: {}", faculty);
    }

    @Override
    public Faculty findFacultyById(final Long id) {
        LOGGER.debug("Running a method to find faculty by ID={}", id);
        Faculty faculty = new Faculty();
        try {
            faculty = jdbcTemplate.queryForObject(FIND_FACULTY_BY_ID, new Object[]{id}, new BeanPropertyRowMapper<>(Faculty.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(faculty.toString());
            String message = format("Faculty with ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get Faculty with ID='%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.info("Faculty was found. Faculty details: {}", id);
        return faculty;
    }

    @Override
    public List<Faculty> findAllFaculties() {
        LOGGER.debug("Running a method to find all faculties");
        List<Faculty> faculties;
        try {
            faculties = jdbcTemplate.query(FIND_ALL_FACULTIES, new BeanPropertyRowMapper<>(Faculty.class));
        } catch (DataAccessException e) {
            String message = "Unable to get faculties";
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.info("Faculties were successfully found");
        return faculties;
    }

    @Override
    public List<Faculty> findAllFacultiesBySubjectId(final Long id) {
        LOGGER.debug("Running a method to find all faculties by subject ID={}", id);
        List<Faculty> faculties = new ArrayList<>();
        try {
            faculties = jdbcTemplate.query(FIND_FACULTIES_BY_SUBJECT_ID, new Object[]{id}, new BeanPropertyRowMapper<>(Faculty.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(faculties.toString());
            String message = format("Faculty by subject ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get Faculty by subject ID='%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Faculties were successfully found by subject ID={}", id);
        return faculties;
    }

    @Override
    public List<Faculty> findAllFacultiesByTeacherId(final Long id) {
        LOGGER.debug("Running a method to find all faculties by teacher ID={}", id);
        List<Faculty> faculties = new ArrayList<>();
        try {
            faculties = jdbcTemplate.query(FIND_FACULTIES_BY_TEACHER_ID, new Object[]{id}, new BeanPropertyRowMapper<>(Faculty.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(faculties.toString());
            String message = format("Faculty by teacher ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get Faculty by teacher ID='%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Faculties were successfully found by teacher ID={}", id);
        return faculties;
    }
}
