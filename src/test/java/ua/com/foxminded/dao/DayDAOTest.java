package ua.com.foxminded.dao;

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
public class DayDAOTest {

    private final DayDAO dayDAO;

    @Autowired
    public DayDAOTest(DayDAO dayDAO) {
        this.dayDAO = dayDAO;
    }

    @Test
    void createDay() {
        Day day = new Day(LocalDate.parse("2020-09-04"));
        dayDAO.create(day);
        Day createdDay = dayDAO.findById(day.getId());

        assertEquals(day.getDay(), createdDay.getDay());
    }

    @Test
    void deleteDay() {
        Day dayToBeDeleted = dayDAO.findById(1L);
        dayDAO.delete(dayToBeDeleted.getId());

        assertEquals(2, dayDAO.findAll().size());
    }

    @Test
    void updateDay() {
        Day newDay = new Day(1L, LocalDate.parse("2020-09-04"));

        dayDAO.update(newDay);
        Day updatedDay = dayDAO.findById(1L);

        assertEquals(newDay.getDay(), updatedDay.getDay());
    }

    @Test
    void findAllDays() {
        List<Day> days = dayDAO.findAll();

        assertEquals(3, days.size());
    }

    @Test
    void findDayById() {
        Day day = dayDAO.findById(1L);

        assertEquals(LocalDate.parse("2020-09-01"), day.getDay());
    }
}
