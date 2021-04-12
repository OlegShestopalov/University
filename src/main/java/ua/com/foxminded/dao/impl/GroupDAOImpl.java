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
    private static final String INSERT_GROUP = "INSERT INTO group1 VALUES(?, ?, ?)";
    private static final String DELETE_GROUP = "DELETE FROM group1 WHERE id=?";
    private static final String UPDATE_GROUP = "UPDATE group1 SET name=?, faculty_id=?, course_id=? WHERE id=?";
    private static final String FIND_GROUP_BY_ID = "SELECT * FROM group1 WHERE id=?";
    private static final String FIND_ALL_GROUPS = "SELECT * FROM group1";
    private static final String FIND_ALL_GROUPS_IN_FACULTY = "SELECT group1.name FROM group1 WHERE faculty_id=?";

    @Autowired
    public GroupDAOImpl(JdbcTemplate jdbcTemplate, GroupMapper groupMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.groupMapper = groupMapper;
    }

    @Override
    public void add(final Group group) {
        LOGGER.debug("Running a method for add group. Group details: {}", group);
        try {
            jdbcTemplate.update(INSERT_GROUP, group.getName(), group.getFaculty(), group.getCourse());
        } catch (DataAccessException e) {
            String message = format("Unable to add Group='%s'", group);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Group was successfully saved. Group details: {}", group);
    }

    @Override
    public void removeGroup(final Long id) {
        LOGGER.debug("Deleting a group with ID={}", id);
        try {
            jdbcTemplate.update(DELETE_GROUP, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Group with ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Group was successfully deleted. Group details: {}", id);
    }

    @Override
    public void updateGroup(final Long id) {
        LOGGER.debug("Changing a group with ID={}", id);
        try {
            jdbcTemplate.update(UPDATE_GROUP, id);
        } catch (EmptyResultDataAccessException e) {
            String message = format("Group with ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        }
        LOGGER.info("Group was successfully updated. Group details: {}", id);
    }

    @Override
    public Group findById(Long id) {
        LOGGER.debug("Running a method to find group by ID={}", id);
        return jdbcTemplate.queryForObject(FIND_GROUP_BY_ID, new Object[]{id}, groupMapper);
    }

    @Override
    public List<Group> findGroups() {
        LOGGER.debug("Running a method to find all groups");
        List<Group> groups;
        try {
            groups = jdbcTemplate.query(FIND_ALL_GROUPS, new BeanPropertyRowMapper<>(Group.class));
        } catch (DataAccessException e) {
            String message = "Unable to get Groups";
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Groups were successfully found");
        return groups;
    }

    @Override
    public List<Group> findAllGroupsInFaculty(final Long id) {
        LOGGER.debug("Running a method to find all groups by faculty id={}", id);
        List<Group> groups = new ArrayList<>();
        try {
            groups = jdbcTemplate.query(FIND_ALL_GROUPS_IN_FACULTY, new Object[]{id}, new BeanPropertyRowMapper<>(Group.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(groups.toString());
            String message = format("Groups by faculty ID='%s' not found", id);
            throw new EntityNotFoundException(message);
        } catch (DataAccessException e) {
            String message = format("Unable to get Groups by faculty ID='%s'", id);
            throw new QueryNotExecuteException(message, e);
        }
        LOGGER.debug("Groups were successfully found by faculty ID={}", id);
        return groups;
    }
}
