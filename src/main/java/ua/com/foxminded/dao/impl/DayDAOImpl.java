package ua.com.foxminded.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.dao.DayDAO;
import ua.com.foxminded.domain.entity.Day;
import ua.com.foxminded.domain.entity.Teacher;
import ua.com.foxminded.exception.QueryNotExecuteException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.lang.String.format;

@Repository
public class DayDAOImpl implements DayDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(DayDAOImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_DAY = "INSERT INTO day1 VALUES (?, ?)";
    private static final String DELETE_DAY = "DELETE FROM day1 WHERE id=?";
    private static final String UPDATE_DAY = "UPDATE day1 SET day=? WHERE id=?";
    private static final String FIND_ALL_DAYS = "SELECT * FROM day1 ORDER BY id";
    private static final String FIND_DAY_BY_ID = "SELECT * FROM day1 WHERE id=?";

    public DayDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Day day) {
        LOGGER.debug("Running addDay method. Day details: {}", day);
        try {
            jdbcTemplate.update(INSERT_DAY,day.getId(), day.getDay());
        } catch (DataAccessException e) {
            String message = format("Unable to add Day='%s'", day);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Day was successfully saved. Day details: {}", day.getDay());
    }

    @Override
    public void delete(Long id) {
        LOGGER.debug("Deleting day with ID={}", id);
        try {
            jdbcTemplate.update(DELETE_DAY, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Day with ID='%s' not found.", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Day was successfully deleted. Day details: {}", id);
    }

    @Override
    public void update(Long id, Day day) {
        LOGGER.debug("Changing a day with ID={}", id);
        try {
            jdbcTemplate.update(UPDATE_DAY, day.getDay(), id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Day with ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Day was successfully updated. Day details: {}", day);
    }

    @Override
    public List<Day> findAll() {
        LOGGER.debug("Running a method to find all days");
        List<Day> days;
        try {
            days = jdbcTemplate.query(FIND_ALL_DAYS, new BeanPropertyRowMapper<>(Day.class));
        } catch (DataAccessException e) {
            String message = "Unable to get days";
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Days were successfully found");
        return days;
    }

    @Override
    public Day findById(Long id) {
        LOGGER.debug("Running a method to find day by ID={}", id);
        Day day = new Day();
        try {
            day = jdbcTemplate.queryForObject(FIND_DAY_BY_ID, new BeanPropertyRowMapper<>(Day.class), id);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(day.toString());
            String message = format("Day with ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get day with ID='%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.info("Day was successfully found. Day details: {}", id);
        return day;
    }
}
