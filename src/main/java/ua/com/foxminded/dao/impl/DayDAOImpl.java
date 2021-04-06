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

    public DayDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(final Day day) {
        LOGGER.debug("add() [{}]", day);
        String SQL = "INSERT INTO day1 VALUES ?";
        try {
            jdbcTemplate.update(SQL, day.getDay());
        } catch (DataAccessException e) {
            String message = format("Unable to add Day '%s'", day);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Day successfully saved. Day details: {}", day.getDay());
    }

    @Override
    public void removeDay(final Long id) {
        LOGGER.debug("remove() [{}]", id);
        String SQL = "DELETE FROM day1 WHERE id=?";
        try {
            jdbcTemplate.update(SQL, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Day with ID '%s' not found.", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Day successfully deleted. Day details: {}", id);
    }

    @Override
    public List<Day> findTerm(Long startId, Long stopId) {
        LOGGER.debug("findTerm()");
        String SQL = "SELECT day FROM day1 WHERE ?<=id<=?";
        try {
            List<Day> days = jdbcTemplate.query(SQL, new Object[]{startId, stopId}, new BeanPropertyRowMapper<>(Day.class));
            for (Day day : days) {
                LOGGER.debug("Days list {}", day);
            }
            return days;
        } catch (DataAccessException e) {
            String message = format("Days with startId '%s' or stopId '%s' not found", startId, stopId);
            throw new QueryNotExecuteException(message, e);
        }
    }
}
