package ua.com.foxminded.dao;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.domain.entity.Audience;
import ua.com.foxminded.domain.entity.Day;
import ua.com.foxminded.domain.entity.Lesson;
import ua.com.foxminded.domain.entity.ScheduleItem;
import ua.com.foxminded.domain.entity.Subject;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/scripts/schema.sql", "/scripts/data.sql"})
@ActiveProfiles("test")
public class ScheduleItemRepositoryTest {

    private final ScheduleItemRepository scheduleItemRepository;
    private final LessonRepository lessonRepository;
    private final SubjectRepository subjectRepository;
    private final AudienceRepository audienceRepository;
    private final DayRepository dayRepository;

    @Autowired
    public ScheduleItemRepositoryTest(ScheduleItemRepository scheduleItemRepository, LessonRepository lessonRepository, SubjectRepository subjectRepository, AudienceRepository audienceRepository, DayRepository dayRepository) {
        this.scheduleItemRepository = scheduleItemRepository;
        this.lessonRepository = lessonRepository;
        this.subjectRepository = subjectRepository;
        this.audienceRepository = audienceRepository;
        this.dayRepository = dayRepository;
    }

    @Test
    void shouldCreateNewScheduleItemInDBWhenAddNewScheduleItem() {
        Lesson lesson = lessonRepository.getOne(1L);
        Subject subject = subjectRepository.getOne(1L);
        Audience audience = audienceRepository.getOne(1L);
        Day day = dayRepository.getOne(1L);
        ScheduleItem scheduleItem = new ScheduleItem(lesson, subject, audience, day);
        scheduleItemRepository.save(scheduleItem);
        ScheduleItem createdScheduleItem = scheduleItemRepository.getOne(scheduleItem.getId());

        assertEquals(scheduleItem, createdScheduleItem);
    }

    @Test
    void shouldDeleteScheduleItemFromDBWhenInputId() {
        ScheduleItem scheduleItemToBeDeleted = scheduleItemRepository.getOne(1L);
        scheduleItemRepository.deleteById(scheduleItemToBeDeleted.getId());

        assertEquals(2, scheduleItemRepository.findAll().size());
    }

    @Test
    void shouldSaveUpdatedScheduleItemWhenChangeDataAboutScheduleItem() {
        Lesson lesson = new Lesson(1L, "first");
        Subject subject = new Subject(1L, "Subject1", "Subject1");
        Audience audience = new Audience(1L, 1, 50);
        Day day = new Day(1L, LocalDate.parse("2020-09-01"));
        ScheduleItem newScheduleItem = new ScheduleItem(1L, lesson, subject, audience, day);
        scheduleItemRepository.save(newScheduleItem);
        ScheduleItem updatedScheduleItem = scheduleItemRepository.getOne(1L);

        assertNotNull(updatedScheduleItem);
        assertEquals(newScheduleItem, updatedScheduleItem);
    }

    @Test
    void shouldReturnAllScheduleItemsWhenFindAll() {
        List<ScheduleItem> scheduleItems = scheduleItemRepository.findAll();

        assertEquals(3, scheduleItems.size());
    }

    @Test
    void shouldReturnScheduleItemByIdWhenInputId() {
        Lesson lesson = new Lesson(1L, "first");
        Subject subject = new Subject(1L, "Subject1", "Subject1");
        Audience audience = new Audience(1L, 1, 50);
        Day day = new Day(1L, LocalDate.parse("2020-09-01"));
        ScheduleItem scheduleItem = new ScheduleItem(1L, lesson, subject, audience, day);
        ScheduleItem scheduleItemInDB = scheduleItemRepository.getOne(1L);

        assertEquals(scheduleItem, Hibernate.unproxy(scheduleItemInDB));
    }

    @Test
    void shouldReturnPagesWithScheduleItemsByDayWhenInputDay() {
        int pageNumber = 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("day"));
        Page<ScheduleItem> scheduleItemInDB = scheduleItemRepository.findByDayDay(pageable, LocalDate.parse("2020-09-01"));

        assertEquals(1, Hibernate.unproxy(scheduleItemInDB.getNumberOfElements()));
    }

    @Test
    void shouldReturnPagesWithScheduleItemsByGroupsNameWhenInputName() {
        int pageNumber = 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("day"));
        Page<ScheduleItem> scheduleItemInDB = scheduleItemRepository.findByGroupsName(pageable, "AAAA");

        assertEquals(1, Hibernate.unproxy(scheduleItemInDB.getNumberOfElements()));
    }

    @Test
    void shouldReturnPagesWithScheduleItemsByTeachersNameWhenInputName() {
        int pageNumber = 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("day"));
        Page<ScheduleItem> scheduleItemInDB = scheduleItemRepository.findByTeachersName(pageable, "Teacher1");

        assertEquals(1, Hibernate.unproxy(scheduleItemInDB.getNumberOfElements()));
    }

    @Test
    void shouldReturnPagesWithScheduleItemsByDayWhenInputDayOrGroupsName() {
        int pageNumber = 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("day"));
        Page<ScheduleItem> scheduleItemInDB = scheduleItemRepository.findByGroupsNameAndDayDay(pageable, "AAAA", LocalDate.parse("2020-09-01"));

        assertEquals(1, Hibernate.unproxy(scheduleItemInDB.getNumberOfElements()));
    }

    @Test
    void shouldReturnPagesWithScheduleItemsByDayWhenInputDayOrTeacherName() {
        int pageNumber = 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("day"));
        Page<ScheduleItem> scheduleItemInDB = scheduleItemRepository.findByTeachersNameAndDayDay(pageable, "Teacher1", LocalDate.parse("2020-09-01"));

        assertEquals(1, Hibernate.unproxy(scheduleItemInDB.getNumberOfElements()));
    }
}
