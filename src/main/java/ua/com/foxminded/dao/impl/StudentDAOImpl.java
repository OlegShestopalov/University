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
    private final StudentMapper studentMapper;
    private static final String INSERT_STUDENT = "INSERT INTO student VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_STUDENT = "DELETE FROM student WHERE id=?";
    private static final String UPDATE_STUDENT = "UPDATE student SET group_id=?, name=?, surname=?, sex=?, age=?, email=? WHERE id=?";
    private static final String FIND_STUDENT_BY_ID = "SELECT * FROM student WHERE id=?";
    private static final String FIND_ALL_STUDENTS = "SELECT * FROM student ORDER BY id";
    private static final String FIND_STUDENT_BY_NAME = "SELECT * FROM student WHERE name=?";
    private static final String FIND_ALL_STUDENTS_IN_GROUP = "SELECT * FROM student WHERE group_id=?";
    private static final String FIND_ALL_EMAIL_IN_GROUP = "SELECT email FROM student WHERE group_id=?";

    @Autowired
    public StudentDAOImpl(JdbcTemplate jdbcTemplate, StudentMapper studentMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentMapper = studentMapper;
    }

    @Override
    public void create(Student student) {
        LOGGER.debug("Running a method for add student. Student details: {}", student);
        try {
            jdbcTemplate.update(INSERT_STUDENT, student.getId(), student.getGroup().getId(), student.getName(), student.getSurname(), student.getSex(), student.getAge(), student.getEmail());
        } catch (DataAccessException e) {
            String message = format("Unable to add Student='%s'", student);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Student was successfully saved. Student details: {}", student);
    }

    @Override
    public void delete(Long id) {
        LOGGER.debug("Deleting a student with ID={}", id);
        try {
            jdbcTemplate.update(DELETE_STUDENT, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Student with ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Student was successfully deleted. Student details: {}", id);
    }

    @Override
    public void update(Long id, Student student) {
        LOGGER.debug("Changing a student with ID={}", id);
        try {
            jdbcTemplate.update(UPDATE_STUDENT, student.getGroup().getId(), student.getName(), student.getSurname(), student.getSex(), student.getAge(), student.getEmail(), id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Student with ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Student was successfully updated. Student details: {}", student);
    }

    @Override
    public List<Student> findAll() {
        LOGGER.debug("Running a method to find all students");
        List<Student> students;
        try {
            students = jdbcTemplate.query(FIND_ALL_STUDENTS, studentMapper);
        } catch (DataAccessException e) {
            String message = "Unable to get students";
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Students were successfully found");
        return students;
    }

    @Override
    public Student findById(Long id) {
        LOGGER.debug("Running a method to find student by ID={}", id);
        Student student = new Student();
        try {
            student = jdbcTemplate.queryForObject(FIND_STUDENT_BY_ID, studentMapper, id);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(student.toString());
            String message = format("Student with ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get student with ID='%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.info("Student was successfully found. Student details: {}", id);
        return student;
    }

    @Override
    public Student findByName(String name) {
        LOGGER.debug("Running a method to find student by name={}", name);
        Student student = new Student();
        try {
            student = jdbcTemplate.queryForObject(FIND_STUDENT_BY_NAME, new BeanPropertyRowMapper<>(Student.class), name);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(student.toString());
            String message = format("Student with name='%s' not found", name);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get student with name='%s'", name);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.info("Student was successfully found by name. Student details: {}", student);
        return student;
    }

    @Override
    public List<Student> findAllStudentsInGroup(Long id) {
        LOGGER.debug("Running a method to find all students by group ID={}", id);
        List<Student> students = new ArrayList<>();
        try {
            students = jdbcTemplate.query(FIND_ALL_STUDENTS_IN_GROUP, new Object[]{id}, new BeanPropertyRowMapper<>(Student.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(students.toString());
            String message = format("Students with ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get students with ID='%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Students were successfully found by group ID={}", id);
        return students;
    }

    @Override
    public List<Student> findAllEmailsInGroup(Long id) {
        LOGGER.debug("Running a method to find all emails by group ID={}", id);
        List<Student> students = new ArrayList<>();
        try {
            students = jdbcTemplate.query(FIND_ALL_EMAIL_IN_GROUP, new Object[]{id}, new BeanPropertyRowMapper<>(Student.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(students.toString());
            String message = format("Students with ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get students with ID='%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Emails were successfully found by group ID={}", id);
        return students;
    }
}
