package ua.com.foxminded.dao;

import ua.com.foxminded.domain.entity.Group;

import java.util.List;

public interface GroupDAO extends GenericDAO<Group> {

    List<Group> findAllGroupsInFaculty(final Long id);
}
