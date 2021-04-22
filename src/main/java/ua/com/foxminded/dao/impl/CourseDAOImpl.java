package ua.com.foxminded.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.dao.CourseDAO;
import ua.com.foxminded.dao.mapper.CourseMapper;
import ua.com.foxminded.dao.mapper.StudentMapper;
import ua.com.foxminded.domain.entity.Course;
import ua.com.foxminded.domain.entity.Student;
import ua.com.foxminded.exception.QueryNotExecuteException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.lang.String.format;

@Repository
public class CourseDAOImpl implements CourseDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentDAOImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private final CourseMapper courseMapper;
    private static final String INSERT_COURSE = "INSERT INTO course VALUES(?, ?)";
    private static final String DELETE_COURSE = "DELETE FROM course WHERE id=?";
    private static final String UPDATE_COURSE = "UPDATE course SET name=? WHERE id=?";
    private static final String FIND_ALL_COURSES = "SELECT * FROM course ORDER BY id";
    private static final String FIND_COURSE_BY_ID = "SELECT * FROM course WHERE id=?";

    public CourseDAOImpl(JdbcTemplate jdbcTemplate, CourseMapper courseMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.courseMapper = courseMapper;
    }

    @Override
    public void create(Course course) {
        LOGGER.debug("Running a method for add course. Course details: {}", course);
        try {
            jdbcTemplate.update(INSERT_COURSE, course.getId(), course.getName());
        } catch (DataAccessException e) {
            String message = format("Unable to add Course='%s'", course);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Course was successfully saved. Course details: {}", course);
    }

    @Override
    public void delete(Long id) {
        LOGGER.debug("Deleting a course with ID={}", id);
        try {
            jdbcTemplate.update(DELETE_COURSE, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Course with ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Course was successfully deleted. Course details: {}", id);
    }

    @Override
    public void update(Long id, Course course) {
        LOGGER.debug("Changing a course with ID={}", id);
        try {
            jdbcTemplate.update(UPDATE_COURSE, course.getName(), id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Course with ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Course was successfully updated. Course details: {}", course);
    }

    @Override
    public List<Course> findAll() {
        LOGGER.debug("Running a method to find all courses");
        List<Course> courses;
        try {
            courses = jdbcTemplate.query(FIND_ALL_COURSES, courseMapper);
        } catch (DataAccessException e) {
            String message = "Unable to get courses";
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Courses were successfully found");
        return courses;
    }

    @Override
    public Course findById(Long id) {
        LOGGER.debug("Running a method to find course by ID={}", id);
        Course course = new Course();
        try {
            course = jdbcTemplate.queryForObject(FIND_COURSE_BY_ID, courseMapper, id);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(course.toString());
            String message = format("Course with ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get course with ID='%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.info("Course was successfully found. Course details: {}", id);
        return course;
    }
}
