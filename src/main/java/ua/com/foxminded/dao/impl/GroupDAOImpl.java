package ua.com.foxminded.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.dao.GroupDAO;
import ua.com.foxminded.dao.mapper.GroupMapper;
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.exception.QueryNotExecuteException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Repository
public class GroupDAOImpl implements GroupDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupDAOImpl.class);
    private final GroupMapper groupMapper;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupDAOImpl(JdbcTemplate jdbcTemplate, GroupMapper groupMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.groupMapper = groupMapper;
    }

    @Override
    public void add(final Group group) {
        LOGGER.debug("add() [{}]", group);
        String SQL = "INSERT INTO group1 VALUES(?, ?, ?)";
        try {
            jdbcTemplate.update(SQL, group.getName(), group.getFaculty(), group.getCourse());
        } catch (DataAccessException e) {
            String message = format("Unable to add Group '%s'", group);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Group successfully saved. Group details: {}", group);
    }

    @Override
    public void removeGroup(final Long id) {
        LOGGER.debug("removeGroup() [{}]", id);
        String SQL = "DELETE FROM group1 WHERE id=?";
        try {
            jdbcTemplate.update(SQL, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Group with ID '%s' not found", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Group successfully deleted. Group details: {}", id);
    }

    @Override
    public void updateGroup(final Long id) {
        LOGGER.debug("updateGroup() [{}]", id);
        String SQL = "UPDATE group1 SET name=?, faculty_id=?, course_id=? WHERE id=?";
        try {
            jdbcTemplate.update(SQL, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Group with ID '%s' not found", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Group successfully updated. Group details: {}", id);
    }

    @Override
    public List<Group> findGroups() {
        LOGGER.debug("findGroups()");
        String SQL = "SELECT * FROM group1";
        List<Group> groups;
        try {
            groups = jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Group.class));
        } catch (DataAccessException e) {
            String message = "Unable to get Groups";
            throw new QueryNotExecuteException(message, e);
        }
        for (Group group : groups) {
            LOGGER.debug("Groups successfully found. Group details: {}", group);
        }
        return groups;
    }

    @Override
    public Group findById(Long id) {
        LOGGER.debug("findById()");
        String SQL = "SELECT * FROM group1 WHERE id=?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{id}, groupMapper);
    }

    @Override
    public List<Group> findAllGroupsInFaculty(final Long id) {
        LOGGER.debug("findAllGroupsInFaculty()");
        String SQL = "SELECT group1.name FROM group1 WHERE faculty_id=?";
        List<Group> groups = new ArrayList<>();
        try {
            groups = jdbcTemplate.query(SQL, new Object[]{id}, new BeanPropertyRowMapper<>(Group.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(groups.toString());
            String message = format("Groups by facultyId '%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get Groups by facultyId '%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        for (Group group : groups) {
            LOGGER.debug("Groups successfully found. Group details: {}", group);
        }
        return groups;
    }
}
