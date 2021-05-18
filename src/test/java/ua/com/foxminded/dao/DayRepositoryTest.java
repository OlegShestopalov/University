package ua.com.foxminded.dao;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.domain.entity.Day;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
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
