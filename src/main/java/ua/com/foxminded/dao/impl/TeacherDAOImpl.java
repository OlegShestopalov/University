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
    private static final String INSERT_TEACHER = "INSERT INTO teacher VALUES (?, ?)";
    private static final String DELETE_TEACHER = "DELETE FROM teacher WHERE id=?";
    private static final String UPDATE_TEACHER = "UPDATE teacher SET name=?, surname=? WHERE id=?";
    private static final String FIND_TEACHER_BY_ID = "SELECT * FROM teacher WHERE id=?";
    private static final String FIND_ALL_TEACHERS = "SELECT * FROM teacher";
    private static final String FIND_TEACHERS_EMAILS = "SELECT email FROM teacher";
    private static final String FIND_ALL_TEACHERS_BY_SUBJECT_ID = "SELECT teacher.name, teacher.surname FROM teacher " +
            "INNER JOIN teacher_subject ON teacher.id=teacher_subject.teacher_id " +
            "INNER JOIN subject ON subject.id=teacher_subject.subject_id " +
            "WHERE subject.id=?";
    private static final String FIND_ALL_TEACHERS_IN_FACULTY = "SELECT teacher.name, teacher.surname FROM teacher " +
            "INNER JOIN teacher_faculty ON teacher.id=teacher_faculty.teacher_id " +
            "INNER JOIN faculty ON faculty.id=teacher_faculty.faculty_id " +
            "WHERE faculty.id=?";

    @Autowired
    public TeacherDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(final Teacher teacher) {
        LOGGER.debug("Running a method for add teacher. Teacher details: {}", teacher);
        try {
            jdbcTemplate.update(INSERT_TEACHER, teacher.getName(), teacher.getSurname());
        } catch (DataAccessException e) {
            String message = format("Unable to add Teacher '%s'", teacher);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Teacher was successfully added. Teacher details: {}", teacher);
    }

    @Override
    public void removeTeacher(final Long id) {
        LOGGER.debug("Deleting a teacher with ID={}", id);
        try {
            jdbcTemplate.update(DELETE_TEACHER, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Teacher with ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Teacher was successfully deleted. Teacher details: {}", id);
    }

    @Override
    public void update(final Long id, final Teacher teacher) {
        LOGGER.debug("Changing a teacher with ID={}", id);
        try {
            jdbcTemplate.update(UPDATE_TEACHER, teacher.getName(), teacher.getSurname(), id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Faculty with ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Teacher was successfully updated. Teacher details: {}", teacher);
    }

    @Override
    public Teacher findTeacherById(final Long id) {
        LOGGER.debug("Running a method to find teacher by ID={}", id);
        Teacher teacher = new Teacher();
        try {
            teacher = jdbcTemplate.queryForObject(FIND_TEACHER_BY_ID, new Object[]{id}, new BeanPropertyRowMapper<>(Teacher.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(teacher.toString());
            String message = format("Teacher with ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get teacher with ID='%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.info("Teacher was successfully found. Teacher details: {}", id);
        return teacher;
    }

    @Override
    public List<Teacher> findAllTeachers() {
        LOGGER.debug("Running a method to find all teachers");
        List<Teacher> teachers;
        try {
            teachers = jdbcTemplate.query(FIND_ALL_TEACHERS, new BeanPropertyRowMapper<>(Teacher.class));
        } catch (DataAccessException e) {
            String message = "Unable to get teachers";
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Teachers were successfully found");
        return teachers;
    }

    @Override
    public List<Teacher> findAllEmails() {
        LOGGER.debug("Running a method to find teachers emails");
        List<Teacher> teachers;
        try {
            teachers = jdbcTemplate.query(FIND_TEACHERS_EMAILS, new BeanPropertyRowMapper<>(Teacher.class));
        } catch (DataAccessException e) {
            String message = "Unable to get teachers emails";
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Emails were successfully found");
        return teachers;
    }

    @Override
    public List<Teacher> findAllTeachersBySubjectId(final Long id) {
        LOGGER.debug("Running a method to find all teachers by subject ID={}", id);
        List<Teacher> teachers = new ArrayList<>();
        try {
            teachers = jdbcTemplate.query(FIND_ALL_TEACHERS_BY_SUBJECT_ID, new Object[]{id}, new BeanPropertyRowMapper<>(Teacher.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(teachers.toString());
            String message = format("Teachers by subject ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get teachers by subject ID='%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.info("Teachers were successfully found by subject ID={}", id);
        return teachers;
    }

    @Override
    public List<Teacher> findAllTeachersInFaculty(final Long id) {
        LOGGER.debug("Running a method to find all teachers by faculty ID={}", id);
        List<Teacher> teachers = new ArrayList<>();
        try {
            teachers = jdbcTemplate.query(FIND_ALL_TEACHERS_IN_FACULTY, new Object[]{id}, new BeanPropertyRowMapper<>(Teacher.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(teachers.toString());
            String message = format("Teachers by faculty ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get teachers by faculty ID='%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        for (Teacher teacher : teachers) {
            LOGGER.info("Teachers were successfully found by faculty ID={}", id);
        }
        return teachers;
    }
}
