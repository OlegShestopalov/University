package ua.com.foxminded.domain.service;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.exception.AlreadyExistException;

import java.util.List;

public interface FacultyService {

    void create(final Faculty faculty) throws AlreadyExistException;

    void update(final Faculty faculty) throws NotFoundException;

    void delete(final Long id) throws NotFoundException;

    Faculty findById(final Long id) throws NotFoundException;

    Page<Faculty> findAll(final int pageNumber) throws NotFoundException;

    List<Faculty> findByName(final String name) throws NotFoundException;

    List<Faculty> findAll() throws NotFoundException;
}
