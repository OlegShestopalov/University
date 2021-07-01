package ua.com.foxminded.domain.service;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.exception.AlreadyExistException;

import java.util.List;

public interface GroupService {

    void create(final Group group) throws AlreadyExistException;

    void update(final Group group) throws NotFoundException;

    void delete(final Long id) throws NotFoundException;

    Group findById(final Long id) throws NotFoundException;

    Page<Group> findAll(final int pageNumber) throws NotFoundException;

    List<Group> findByName(final String name) throws NotFoundException;

    List<Group> findAll() throws NotFoundException;
}
