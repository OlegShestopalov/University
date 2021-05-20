package ua.com.foxminded.dao;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.domain.entity.Course;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/scripts/schema.sql", "/scripts/data.sql"})
@ActiveProfiles("test")
public class CourseRepositoryTest {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseRepositoryTest(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Test
    void createCourse() {
        Course course = new Course("TEST");
        courseRepository.create(course);
        Course createdCourse = courseRepository.findById(course.getId());

        assertEquals(course, Hibernate.unproxy(createdCourse));
    }

    @Test
    void deleteCourse() {
        Course courseToBeDeleted = courseRepository.findById(1L);
        courseRepository.delete(courseToBeDeleted.getId());

        assertEquals(5, courseRepository.findAll().size());
    }

    @Test
    void updateCourse() {
        Course newCourse = new Course(1L, "UpdatedCourse");
        courseRepository.update(newCourse);
        Course updatedCourse = courseRepository.findById(1L);

        assertEquals(newCourse, Hibernate.unproxy(updatedCourse));
    }

    @Test
    void findAllCourses() {
        List<Course> courses = courseRepository.findAll();

        assertEquals(6, courses.size());
    }

    @Test
    void findCourseById() {
        Course course = new Course(1L, "first");
        Course courseInDB = courseRepository.findById(1L);

        assertEquals(course, Hibernate.unproxy(courseInDB));
    }
}
