package ua.com.foxminded.domain.service;

import ua.com.foxminded.domain.entity.Group;

import java.util.List;

public interface GroupService {

    void create(final Group group);

    void delete(final Long id);

    Group findById(final Long id);

    List<Group> findAll();
}
