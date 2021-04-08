package ua.com.foxminded.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.dao.TeacherDAO;
import ua.com.foxminded.dao.mapper.TeacherMapper;
import ua.com.foxminded.domain.entity.Teacher;
import ua.com.foxminded.exception.QueryNotExecuteException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Repository
public class TeacherDAOImpl implements TeacherDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherDAOImpl.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TeacherDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(final Teacher teacher) {
        LOGGER.debug("add() [{}]", teacher);
        String SQL = "INSERT INTO teacher VALUES (?, ?)";
        try {
            jdbcTemplate.update(SQL, teacher.getName(), teacher.getSurname());
        } catch (DataAccessException e) {
            String message = format("Unable to add Teacher '%s'", teacher);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Teacher successfully added. Teacher details: {}", teacher);
    }

    @Override
    public void removeTeacher(final Long id) {
        LOGGER.debug("removeTeacher() [{}]", id);
        String SQL = "DELETE FROM teacher WHERE id=?";
        try {
            jdbcTemplate.update(SQL, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Teacher with ID '%s' not found.", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Teacher successfully deleted. Teacher details: {}", id);
    }

    @Override
    public void update(final Long id, final Teacher teacher) {
        LOGGER.debug("updateTeacher() [{}]", id);
        String SQL = "UPDATE teacher SET name=?, surname=? WHERE id=?";
        try {
            jdbcTemplate.update(SQL, teacher.getName(), teacher.getSurname(), id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Faculty with ID '%s' not found.", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Teacher successfully updated. Teacher details: {}", teacher);
    }

    @Override
    public Teacher findTeacher(final Long id) {
        LOGGER.debug("findTeacher() [{}]", id);
        String SQL = "SELECT * FROM teacher WHERE id=?";
        Teacher teacher = new Teacher();
        try {
            teacher = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new BeanPropertyRowMapper<>(Teacher.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(teacher.toString());
            String message = format("Teacher with ID '%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get teacher with ID '%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.info("Teacher successfully found. Teacher details: {}", id);
        return teacher;
    }

    @Override
    public List<Teacher> findAllTeachers() {
        LOGGER.debug("findAllTeachers()");
        String SQL = "SELECT * FROM teacher";
//        return jdbcTemplate.query(SQL, new TeacherMapper());
        List<Teacher> teachers;
        try {
            teachers = jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Teacher.class));
        } catch (DataAccessException e) {
            String message = "Unable to get teachers";
            throw new QueryNotExecuteException(message, e);
        }
        for (Teacher teacher : teachers) {
            LOGGER.debug("Teacher successfully found. Teacher details: {}", teacher);
        }
        return teachers;
    }

    @Override
    public List<Teacher> findAllEmails() {
        LOGGER.debug("findAllEmails()");
        String SQL = "SELECT email FROM teacher";
        List<Teacher> teachers;
        try {
            teachers = jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Teacher.class));
        } catch (DataAccessException e) {
            String message = "Unable to get teachers emails";
            throw new QueryNotExecuteException(message, e);
        }
        for (Teacher teacher : teachers) {
            LOGGER.debug("Email successfully found. Teacher details: {}", teacher.getEmail());
        }
        return teachers;
    }

    @Override
    public List<Teacher> findAllTeachersBySubjectId(final Long id) {
        LOGGER.debug("findAllTeachersBySubjectId() [{}]", id);
        String SQL = "SELECT teacher.name, teacher.surname FROM teacher " +
                "INNER JOIN teacher_subject ON teacher.id=teacher_subject.teacher_id " +
                "INNER JOIN subject ON subject.id=teacher_subject.subject_id " +
                "WHERE subject.id=?";
        List<Teacher> teachers = new ArrayList<>();
        try {
            teachers = jdbcTemplate.query(SQL, new Object[]{id}, new BeanPropertyRowMapper<>(Teacher.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(teachers.toString());
            String message = format("Teachers by subjectId '%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get teachers by subjectId '%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        for (Teacher teacher : teachers) {
            LOGGER.info("Teacher successfully found. Teacher details: {}", id);
        }
        return teachers;
    }

    @Override
    public List<Teacher> findAllTeachersInFaculty(final Long id) {
        LOGGER.debug("findAllTeachersInFaculty() [{}]", id);
        String SQL = "SELECT teacher.name, teacher.surname FROM teacher " +
                "INNER JOIN teacher_faculty ON teacher.id=teacher_faculty.teacher_id " +
                "INNER JOIN faculty ON faculty.id=teacher_faculty.faculty_id " +
                "WHERE faculty.id=?";
        List<Teacher> teachers = new ArrayList<>();
        try {
            teachers = jdbcTemplate.query(SQL, new Object[]{id}, new BeanPropertyRowMapper<>(Teacher.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(teachers.toString());
            String message = format("Teachers by facultyId '%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get teachers by facultyId '%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        for (Teacher teacher : teachers) {
            LOGGER.info("Teacher successfully found. Teacher details: {}", id);
        }
        return teachers;
    }
}
