package ua.com.foxminded.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ua.com.foxminded.config.SpringConfig;
import ua.com.foxminded.domain.entity.Course;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfig.class, loader = AnnotationConfigContextLoader.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/scripts/schema.sql", "/scripts/data.sql"})
public class CourseDAOTest {

    private final CourseDAO courseDAO;

    @Autowired
    public CourseDAOTest(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Test
    void createCourse() {
        Course course = new Course(7L, "TEST");
        courseDAO.create(course);
        Course createdCourse = courseDAO.findById(course.getId());

        assertEquals(course.getName(), createdCourse.getName());
    }

    @Test
    void deleteCourse() {
        Course courseToBeDeleted = courseDAO.findById(1L);
        courseDAO.delete(courseToBeDeleted.getId());

        assertEquals(5, courseDAO.findAll().size());
    }

    @Test
    void updateCourse() {
        Course courseToBeUpdated = courseDAO.findById(1L);
        Course newCourse = new Course(1L, "UpdatedCourse");

        courseDAO.update(courseToBeUpdated.getId(), newCourse);
        Course updatedCourse = courseDAO.findById(1L);

        assertEquals(newCourse, updatedCourse);
    }

    @Test
    void findAllCourses() {
        List<Course> courses = courseDAO.findAll();

        assertEquals(6, courses.size());
    }

    @Test
    void findCourseById() {
        Course course = courseDAO.findById(1L);

        assertEquals("first", course.getName());
    }
}
