package ua.com.foxminded.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.dao.mapper.StudentMapper;
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.domain.entity.Student;
import ua.com.foxminded.exception.QueryNotExecuteException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Repository
public class StudentDAOImpl implements StudentDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentDAOImpl.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(final Student student) {
        LOGGER.debug("add() [{}]", student);
        String SQL = "INSERT INTO student VALUES(?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(SQL, student.getGroup(), student.getName(), student.getSurname(), student.getSex(), student.getAge());
        } catch (DataAccessException e) {
            String message = format("Unable to add Student '%s'", student);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Student successfully saved. Student details: {}", student);
    }

    @Override
    public void removeStudent(final Long id) {
        LOGGER.debug("removeStudent() [{}]", id);
        String SQL = "DELETE FROM student WHERE id=?";
        try {
            jdbcTemplate.update(SQL, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Student with ID '%s' not found.", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Student successfully deleted. Student details: {}", id);
    }

    @Override
    public void update(final Long id, final Student student) {
        LOGGER.debug("update() [{}]", id);
        String SQL = "UPDATE student SET group_id=?, name=?, surname=?, sex=?, age=? WHERE id=?";
        try {
            jdbcTemplate.update(SQL, student.getGroup(), student.getName(), student.getSurname(), student.getSex(), student.getAge(), id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Student with ID '%s' not found.", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Student successfully updated. Student details: {}", student);
    }

    @Override
    public List<Student> findAllStudents() {
        LOGGER.debug("findAllStudents()");
        String SQL = "SELECT s.id, g.id as group_id, s.name, s.surname, s.sex, s.age, s.email\n" +
                "FROM student s\n" +
                "INNER JOIN group1 g on g.id = s.group_id";
        List<Student> students;
        try {
            students = jdbcTemplate.query(SQL, new StudentMapper());
        } catch (DataAccessException e) {
            String message = "Unable to get students";
            throw new QueryNotExecuteException(message, e);
        }
        for (Student student : students) {
            LOGGER.debug("Student successfully found. Student details: {}", student);
        }
        return students;
    }

    @Override
    public Student findStudent(final Long id) {
        LOGGER.debug("findStudent() [{}]", id);
        String SQL = "SELECT * FROM student WHERE id=?";
        Student student = new Student();
        try {
            student = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new BeanPropertyRowMapper<>(Student.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(student.toString());
            String message = format("Student with ID '%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get student with ID '%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.info("Student successfully found. Student details: {}", id);
        return student;
    }

    @Override
    public Student findByName(final String name, final String surname) {
        LOGGER.debug("findByName() [{}], [{}]", name, surname);
        String SQL = "SELECT * FROM student WHERE name=?";
        Student student = new Student();
        try {
            student = jdbcTemplate.queryForObject(SQL, new BeanPropertyRowMapper<>(Student.class), name);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(student.toString());
            String message = format("Student with name '%s' and surname '%s' not found", name, surname);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get student with name '%s' and surname '%s'", name, surname);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.info("Student successfully found by name. Student details: {}", student);
        return student;
    }

    @Override
    public List<Student> findAllStudentsInGroup(final Long id) {
        LOGGER.debug("findAllStudentsInGroup() [{}]", id);
        String SQL = "SELECT * FROM student WHERE group_id=?";
        List<Student> students = new ArrayList<>();
        try {
            students = jdbcTemplate.query(SQL, new Object[]{id}, new BeanPropertyRowMapper<>(Student.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(students.toString());
            String message = format("Students with ID '%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get students with ID '%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        for (Student student : students) {
            LOGGER.debug("Student successfully found in the group. Student details: {}", student);
        }
        return students;
    }

    @Override
    public List<Student> findAllEmailsInGroup(final Long id) {
        LOGGER.debug("findAllEmailsInGroup() [{}]", id);
        String SQL = "SELECT email FROM student WHERE group_id=?";
        List<Student> students = new ArrayList<>();
        try {
            students = jdbcTemplate.query(SQL, new Object[]{id}, new BeanPropertyRowMapper<>(Student.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(students.toString());
            String message = format("Students with ID '%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get students with ID '%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        for (Student student : students) {
            LOGGER.debug("Email successfully found. Student details: {}", student.getEmail());
        }
        return students;
    }
}
