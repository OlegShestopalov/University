package ua.com.foxminded.dao;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.domain.entity.Lesson;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/scripts/schema.sql", "/scripts/data.sql"})
@ActiveProfiles("test")
public class LessonRepositoryTest {

    private final LessonRepository lessonRepository;

    @Autowired
    public LessonRepositoryTest(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Test
    void createLesson() {
        Lesson lesson = new Lesson(1L, "TEST");
        lessonRepository.save(lesson);
        Lesson createdLesson = lessonRepository.getOne(lesson.getId());

        assertEquals(lesson, createdLesson);
    }

    @Test
    void deleteLesson() {
        Lesson lessonToBeDeleted = lessonRepository.getOne(1L);
        lessonRepository.deleteById(lessonToBeDeleted.getId());

        assertEquals(5, lessonRepository.findAll().size());
    }

    @Test
    void updateLesson() {
        Lesson newLesson = new Lesson(1L, "UpdatedLesson");
        lessonRepository.save(newLesson);
        Lesson updatedLesson = lessonRepository.getOne(1L);

        assertEquals(newLesson, updatedLesson);
    }

    @Test
    void findAllLessons() {
        List<Lesson> lessons = lessonRepository.findAll();

        assertEquals(6, lessons.size());
    }

    @Test
    void findLessonById() {
        Lesson lesson = new Lesson(1L, "first");
        Lesson lessonInDB = lessonRepository.getOne(1L);

        assertEquals(lesson, Hibernate.unproxy(lessonInDB));
    }
}
