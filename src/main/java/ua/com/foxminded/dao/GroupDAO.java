package ua.com.foxminded.dao;

import ua.com.foxminded.domain.entity.Group;

import java.util.List;

public interface GroupDAO {

    public void add(final Group group);

    public void removeGroup(final Long id);

    public void updateGroup(final Long id);

    public List<Group> findGroups();

    Group findById(final Long id);

    public List<Group> findAllGroupsInFaculty(final Long id);
}
