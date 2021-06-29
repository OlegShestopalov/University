package ua.com.foxminded.domain.service;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import ua.com.foxminded.domain.entity.Student;
import ua.com.foxminded.exception.AlreadyExistException;

import java.util.List;

public interface StudentService {

    void create(final Student student) throws AlreadyExistException;

    void update(final Student student) throws NotFoundException;

    void delete(final Long id) throws NotFoundException;

    Student findById(final Long id) throws NotFoundException;

    Page<Student> findAll(final int pageNumber) throws NotFoundException;

    List<Student> findByPersonalData(final String name) throws NotFoundException;

    List<Student> findAll() throws NotFoundException;
}
