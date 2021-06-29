package ua.com.foxminded.domain.service;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import ua.com.foxminded.domain.entity.Teacher;
import ua.com.foxminded.exception.AlreadyExistException;

import java.util.List;

public interface TeacherService {

    void create(final Teacher teacher) throws AlreadyExistException;

    void update(final Teacher teacher) throws NotFoundException;

    void delete(final Long id) throws NotFoundException;

    Teacher findById(final Long id) throws NotFoundException;

    Page<Teacher> findAll(final int pageNumber) throws NotFoundException;

    List<Teacher> findByPersonalData(final String name) throws NotFoundException;

    List<Teacher> findAll() throws NotFoundException;
}
