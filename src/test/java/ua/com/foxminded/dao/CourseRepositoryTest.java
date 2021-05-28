package ua.com.foxminded.dao;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.domain.entity.Course;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
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
        Course course = new Course(1L, "TEST");
        courseRepository.save(course);
        Course createdCourse = courseRepository.getOne(course.getId());

        assertEquals(course, createdCourse);
    }

    @Test
    void deleteCourse() {
        Course courseToBeDeleted = courseRepository.getOne(1L);
        courseRepository.deleteById(courseToBeDeleted.getId());

        assertEquals(5, courseRepository.findAll().size());
    }

    @Test
    void updateCourse() {
        Course newCourse = new Course(1L, "UpdatedCourse");
        courseRepository.save(newCourse);
        Course updatedCourse = courseRepository.getOne(1L);

        assertEquals(newCourse, updatedCourse);
    }

    @Test
    void findAllCourses() {
        List<Course> courses = courseRepository.findAll();

        assertEquals(6, courses.size());
    }

    @Test
    void findCourseById() {
        Course course = new Course(1L, "first");
        Course courseInDB = courseRepository.getOne(1L);

        assertEquals(course, Hibernate.unproxy(courseInDB));
    }
}
