package ua.com.foxminded.dao;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.domain.entity.Day;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
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
        dayRepository.save(day);
        Day createdDay = dayRepository.getOne(day.getId());

        assertEquals(day, createdDay);
    }

    @Test
    void deleteDay() {
        Day dayToBeDeleted = dayRepository.getOne(1L);
        dayRepository.deleteById(dayToBeDeleted.getId());

        assertEquals(2, dayRepository.findAll().size());
    }

    @Test
    void updateDay() {
        Day newDay = new Day(1L, LocalDate.parse("2020-09-04"));
        dayRepository.save(newDay);
        Day updatedDay = dayRepository.getOne(1L);

        assertEquals(newDay, updatedDay);
    }

    @Test
    void findAllDays() {
        List<Day> days = dayRepository.findAll();

        assertEquals(3, days.size());
    }

    @Test
    void findDayById() {
        Day day = new Day(1L, LocalDate.parse("2020-09-01"));
        Day dayInDB = dayRepository.getOne(1L);

        assertEquals(day, Hibernate.unproxy(dayInDB));
    }
}
