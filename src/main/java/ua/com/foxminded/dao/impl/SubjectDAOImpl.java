package ua.com.foxminded.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.dao.SubjectDAO;
import ua.com.foxminded.dao.mapper.SubjectMapper;
import ua.com.foxminded.domain.entity.Subject;
import ua.com.foxminded.exception.QueryNotExecuteException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Repository
public class SubjectDAOImpl implements SubjectDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectDAOImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private final SubjectMapper subjectMapper;
    private static final String INSERT_SUBJECT = "INSERT INTO subject VALUES(?, ?, ?)";
    private static final String DELETE_SUBJECT = "DELETE FROM subject WHERE id=?";
    private static final String UPDATE_SUBJECT = "UPDATE subject SET name=?, description=? WHERE id=?";
    private static final String FIND_ALL_SUBJECTS = "SELECT subject.id, subject.name, subject.description FROM subject";
    private static final String FIND_SUBJECT_BY_ID = "SELECT subject.id, subject.name, subject.description FROM subject WHERE id=?";
    private static final String FIND_ALL_TEACHER_SUBJECTS = "SELECT subject.id, subject.name, subject.description FROM subject " +
            "INNER JOIN teacher_subject ON subject.id=teacher_subject.subject_id " +
            "INNER JOIN teacher ON teacher.id=teacher_subject.teacher_id " +
            "WHERE teacher.id=?";
    private static final String FIND_ALL_FACULTY_SUBJECTS = "SELECT subject.id, subject.name, subject.description FROM subject " +
            "INNER JOIN subject_faculty ON subject.id=subject_faculty.subject_id " +
            "INNER JOIN faculty ON faculty.id=subject_faculty.faculty_id " +
            "WHERE faculty.id=?";

    public SubjectDAOImpl(JdbcTemplate jdbcTemplate, SubjectMapper subjectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.subjectMapper = subjectMapper;
    }

    @Override
    public void create(Subject subject) {
        LOGGER.debug("Running a method for add subject. Subject details: {}", subject);
        try {
            jdbcTemplate.update(INSERT_SUBJECT, subject.getId(), subject.getName(), subject.getDescription());
        } catch (DataAccessException e) {
            String message = format("Unable to add Subject='%s'", subject);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Subject was successfully saved. Subject details: {}", subject);
    }

    @Override
    public void delete(Long id) {
        LOGGER.debug("Deleting a subject with ID={}", id);
        try {
            jdbcTemplate.update(DELETE_SUBJECT, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Subject with ID='%s' not found.", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Subject was successfully deleted. Subject details: {}", id);
    }

    @Override
    public void update(Long id, Subject subject) {
        LOGGER.debug("Changing a subject with ID={}", id);
        try {
            jdbcTemplate.update(UPDATE_SUBJECT, subject.getName(), subject.getDescription(), id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Subject with ID='%s' not found.", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Subject was successfully updated. Subject details: {}", subject);
    }

    @Override
    public List<Subject> findAll() {
        LOGGER.debug("Running a method to find all teachers");
        List<Subject> subjects;
        try {
            subjects = jdbcTemplate.query(FIND_ALL_SUBJECTS, subjectMapper);
        } catch (DataAccessException e) {
            String message = "Unable to get teachers";
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Teachers were successfully found");
        return subjects;
    }

    @Override
    public Subject findById(Long id) {
        LOGGER.debug("Running a method to find subject by ID={}", id);
        Subject subject = new Subject();
        try {
            subject = jdbcTemplate.queryForObject(FIND_SUBJECT_BY_ID, subjectMapper, id);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(subject.toString());
            String message = format("Subject with ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get Subject with ID='%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.info("Subject was successfully found. Subject details: {}", id);
        return subject;
    }

    @Override
    public List<Subject> findAllTeacherSubjects(Long id) {
        LOGGER.debug("Running a method to find all subjects by teacher ID={}", id);
        List<Subject> subjects = new ArrayList<>();
        try {
            subjects = jdbcTemplate.query(FIND_ALL_TEACHER_SUBJECTS, subjectMapper, id);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(subjects.toString());
            String message = format("Teacher's subjects with ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get teacher's subjects with ID='%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Subjects were successfully found. Subject details: {}", id);
        return subjects;
    }

    @Override
    public List<Subject> findAllFacultySubjects(Long id) {
        LOGGER.debug("Running a method to find all subjects by faculty ID={}", id);
        List<Subject> subjects = new ArrayList<>();
        try {
            subjects = jdbcTemplate.query(FIND_ALL_FACULTY_SUBJECTS, subjectMapper, id);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(subjects.toString());
            String message = format("Faculty's subjects with ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get faculty's subjects with ID='%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        for (Subject subject : subjects) {
            LOGGER.debug("Subjects were successfully found. Subject details: {}", id);
        }
        return subjects;
    }
}
