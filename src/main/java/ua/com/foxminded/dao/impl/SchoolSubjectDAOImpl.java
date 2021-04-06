package ua.com.foxminded.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.dao.SchoolSubjectDAO;
import ua.com.foxminded.domain.entity.SchoolSubject;
import ua.com.foxminded.exception.QueryNotExecuteException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Repository
public class SchoolSubjectDAOImpl implements SchoolSubjectDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolSubjectDAOImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public SchoolSubjectDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(final SchoolSubject schoolSubject) {
        LOGGER.debug("add() [{}]", schoolSubject);
        String SQL = "INSERT INTO subject VALUES(?, ?)";
        try {
            jdbcTemplate.update(SQL, schoolSubject.getName(), schoolSubject.getDescription());
        } catch (DataAccessException e) {
            String message = format("Unable to add Subject '%s'", schoolSubject);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Subject successfully saved. Subject details: {}", schoolSubject);
    }

    @Override
    public void removeSubject(final Long id) {
        LOGGER.debug("removeSubject() [{}]", id);
        String SQL = "DELETE FROM subject WHERE id=?";
        try {
            jdbcTemplate.update(SQL, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Subject with ID '%s' not found.", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Subject successfully deleted. Subject details: {}", id);
    }

    @Override
    public void update(final Long id, final SchoolSubject schoolSubject) {
        LOGGER.debug("updateSubject() [{}]", id);
        String SQL = "UPDATE subject SET name=?, description=? WHERE id=?";
        try {
            jdbcTemplate.update(SQL, schoolSubject.getName(), schoolSubject.getDescription(), id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Subject with ID '%s' not found.", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Subject successfully updated. Subject details: {}", schoolSubject);
    }

    @Override
    public SchoolSubject findSubject(final Long id) {
        LOGGER.debug("findSubject() [{}]", id);
        String SQL = "SELECT subject.name, subject.description FROM subject WHERE id=?";
        SchoolSubject subject = new SchoolSubject();
        try {
            subject = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new BeanPropertyRowMapper<>(SchoolSubject.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(subject.toString());
            String message = format("Subject with ID '%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get Subject with Id '%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.info("Subject successfully found. Subject details: {}", id);
        return subject;
    }

    @Override
    public List<SchoolSubject> findAllTeacherSubjects(final Long id) {
        LOGGER.debug("findAllTeacherSubjects() [{}]", id);
        String SQL = "SELECT subject.name FROM subject " +
                "INNER JOIN teacher_subject ON subject.id=teacher_subject.subject_id " +
                "INNER JOIN teacher ON teacher.id=teacher_subject.teacher_id " +
                "WHERE teacher.id=?";
        List<SchoolSubject> subjects = new ArrayList<>();
        try {
            subjects = jdbcTemplate.query(SQL, new Object[]{id}, new BeanPropertyRowMapper<>(SchoolSubject.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(subjects.toString());
            String message = format("Teacher's '%s' subjects not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get teacher's subjects '%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        for (SchoolSubject subject : subjects) {
            LOGGER.debug("Subject successfully found. Subject details: {}", id);
        }
        return subjects;
    }

    @Override
    public List<SchoolSubject> findAllFacultySubjects(final Long id) {
        LOGGER.debug("findAllFacultySubjects() [{}]", id);
        String SQL = "SELECT subject.name FROM subject " +
                "INNER JOIN subject_faculty ON subject.id=subject_faculty.subject_id " +
                "INNER JOIN faculty ON faculty.id=subject_faculty.faculty_id " +
                "WHERE faculty.id=?";
        List<SchoolSubject> subjects = new ArrayList<>();
        try {
            subjects = jdbcTemplate.query(SQL, new Object[]{id}, new BeanPropertyRowMapper<>(SchoolSubject.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(subjects.toString());
            String message = format("Faculty's '%s' subjects not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get facculty's '%s' subjects", id);
            throw new QueryNotExecuteException(message, e);
        }
        for (SchoolSubject subject : subjects) {
            LOGGER.debug("Subject successfully found. Subject details: {}", id);
        }
        return subjects;
    }
}
