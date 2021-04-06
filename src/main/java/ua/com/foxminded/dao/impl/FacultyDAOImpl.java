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

    public FacultyDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(final Faculty faculty) {
        LOGGER.debug("add() [{}]", faculty);
        String SQL = "INSERT INTO faculty VALUES (?)";
        try {
            jdbcTemplate.update(SQL, faculty.getName());
        } catch (DataAccessException e) {
            String message = format("Couldn't add Faculty '%s'", faculty);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Faculty successfully saved. Faculty details: {}", faculty.getId());
    }

    @Override
    public void removeFaculty(final Long id) {
        LOGGER.debug("remove() [{}]", id);
        String SQL = "DELETE FROM faculty WHERE id=?";
        try {
            jdbcTemplate.update(SQL, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Faculty with ID '%s' not found.", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Faculty successfully deleted. Faculty details: {}", id);
    }

    @Override
    public void update(final Long id, final Faculty faculty) {
        LOGGER.debug("update() [{}]", id);
        String SQL = "UPDATE faculty SET name=? WHERE id=?";
        try {
            jdbcTemplate.update(SQL, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Faculty with ID '%s' not found.", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Faculty successfully updated. Faculty details: {}", faculty);
    }

    @Override
    public Faculty findFaculty(final Long id) {
        LOGGER.debug("find() [{}]", id);
        String SQL = "SELECT * FROM faculty WHERE id=?";
        Faculty faculty = new Faculty();
        try {
            faculty = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new BeanPropertyRowMapper<>(Faculty.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(faculty.toString());
            String message = format("Faculty with id '%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get Faculty with ID '%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.info("Faculty found. Faculty details: {}", id);
        return faculty;
    }

    @Override
    public List<Faculty> findAllFaculties() {
        LOGGER.debug("findAllFaculties()");
        String SQL = "SELECT * FROM faculty";
        List<Faculty> faculties;
        try {
            faculties = jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Faculty.class));
        } catch (DataAccessException e) {
            String message = "Unable to get faculties";
            throw new QueryNotExecuteException(message, e);
        }
        for (Faculty faculty : faculties) {
            LOGGER.info("Faculty successfully found. Faculty details: {}", faculty);
        }
        return faculties;
    }

    @Override
    public List<Faculty> findAllFacultiesBySubjectId(final Long id) {
        LOGGER.debug("findAllFacultiesBySubjectId() [{}]", id);
        String SQL = "SELECT faculty.name FROM faculty " +
                "INNER JOIN subject_faculty ON faculty.id=subject_faculty.faculty_id " +
                "INNER JOIN subject ON subject.id=subject_faculty.subject_id " +
                "WHERE subject.id=?";
        List<Faculty> faculties = new ArrayList<>();
        try {
            faculties = jdbcTemplate.query(SQL, new Object[]{id}, new BeanPropertyRowMapper<>(Faculty.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(faculties.toString());
            String message = format("Faculty by subjectId '%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get Faculty by subjectId '%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        for (Faculty faculty : faculties) {
            LOGGER.debug("List of faculties studying the subject {}", faculty);
        }
        return faculties;
    }

    @Override
    public List<Faculty> findAllFacultiesByTeacherId(final Long id) {
        LOGGER.debug("findAllFacultiesByTeacherId() [{}]", id);
        String SQL = "SELECT faculty.name FROM faculty " +
                "INNER JOIN teacher_faculty ON faculty.id=teacher_faculty.faculty_id " +
                "INNER JOIN teacher ON teacher.id=teacher_faculty.teacher_id " +
                "WHERE teacher.id=?";
        List<Faculty> faculties = new ArrayList<>();
        try {
            faculties = jdbcTemplate.query(SQL, new Object[]{id}, new BeanPropertyRowMapper<>(Faculty.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(faculties.toString());
            String message = format("Faculty by teacherId '%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get Faculty by teacherId '%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        for (Faculty faculty : faculties) {
            LOGGER.debug("List of faculties where the teacher is teaching {}", faculty);
        }
        return faculties;
    }
}
