package ua.com.foxminded.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.dao.UniversityDAO;
import ua.com.foxminded.domain.entity.Audience;
import ua.com.foxminded.exception.QueryNotExecuteException;

import java.util.List;

@Repository
public class UniversityDAOImpl implements UniversityDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityDAOImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private static final String FIND_ALL_AUDIENCES = "SELECT * FROM audience";

    @Autowired
    public UniversityDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Audience> findAllAudiences() {
        LOGGER.debug("Running a method to find all audiences");
        List<Audience> audiences;
        try {
            audiences = jdbcTemplate.query(FIND_ALL_AUDIENCES, new BeanPropertyRowMapper<>(Audience.class));
        } catch (DataAccessException e) {
            String message = "Unable to get audiences";
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.info("Audiences were successfully found");
        return audiences;
    }
}
