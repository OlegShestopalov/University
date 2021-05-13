package ua.com.foxminded.dao;

import ua.com.foxminded.domain.entity.Group;

import java.util.List;

public interface GroupRepository extends GenericRepository<Group> {

    List<Group> findAllGroupsInFaculty(final Long id);
}
