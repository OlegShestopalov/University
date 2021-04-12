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
import ua.com.foxminded.exception.QueryNotExecuteException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.lang.String.format;

@Repository
public class DayDAOImpl implements DayDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(DayDAOImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_DAY = "INSERT INTO day1 VALUES (?)";
    private static final String DELETE_DAY = "DELETE FROM day1 WHERE id=?";
    private static final String SELECT_DAYS = "SELECT day FROM day1 WHERE ?<=id<=?";

    public DayDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(final Day day) {
        LOGGER.debug("Running addDay method. Day details: {}", day);
        try {
            jdbcTemplate.update(INSERT_DAY, day.getDay());
        } catch (DataAccessException e) {
            String message = format("Unable to add Day='%s'", day);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Day was successfully saved. Day details: {}", day.getDay());
    }

    @Override
    public void removeDay(final Long id) {
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
    public List<Day> findTerm(Long startId, Long stopId) {
        LOGGER.debug("Running a method to find a period of days");
        List<Day> days;
        try {
            days = jdbcTemplate.query(SELECT_DAYS, new Object[]{startId, stopId}, new BeanPropertyRowMapper<>(Day.class));
            for (Day day : days) {
                LOGGER.debug("Days list {}", day);
            }
        } catch (DataAccessException e) {
            String message = format("Days with startID='%s' or stopID='%s' not found", startId, stopId);
            throw new QueryNotExecuteException(message, e);
        }
        return days;
    }
}
