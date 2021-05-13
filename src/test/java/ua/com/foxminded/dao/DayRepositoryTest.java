package ua.com.foxminded.dao;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ua.com.foxminded.config.SpringConfig;
import ua.com.foxminded.domain.entity.Day;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfig.class, loader = AnnotationConfigContextLoader.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/scripts/schema.sql", "/scripts/data.sql"})
@ActiveProfiles("test")
public class DayRepositoryTest {

    private final DayRepository dayRepository;

    @Autowired
    public DayRepositoryTest(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    @Test
    void createDay() {
        Day day = new Day(LocalDate.parse("2020-09-04"));
        dayRepository.create(day);
        Day createdDay = dayRepository.findById(day.getId());

        assertEquals(day, Hibernate.unproxy(createdDay));
    }

    @Test
    void deleteDay() {
        Day dayToBeDeleted = dayRepository.findById(1L);
        dayRepository.delete(dayToBeDeleted.getId());

        assertEquals(2, dayRepository.findAll().size());
    }

    @Test
    void updateDay() {
        Day newDay = new Day(1L, LocalDate.parse("2020-09-04"));
        dayRepository.update(newDay);
        Day updatedDay = dayRepository.findById(1L);

        assertEquals(newDay, Hibernate.unproxy(updatedDay));
    }

    @Test
    void findAllDays() {
        List<Day> days = dayRepository.findAll();

        assertEquals(3, days.size());
    }

    @Test
    void findDayById() {
        Day day = new Day(1L, LocalDate.parse("2020-09-01"));
        Day dayInDB = dayRepository.findById(1L);

        assertEquals(day, Hibernate.unproxy(dayInDB));
    }
}
