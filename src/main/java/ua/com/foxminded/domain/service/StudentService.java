package ua.com.foxminded.domain.service;

import org.springframework.data.domain.Page;
import ua.com.foxminded.domain.entity.Student;

import java.util.List;

public interface StudentService {

    void create(final Student student);

    void delete(final Long id);

    Student findById(final Long id);

    Page<Student> findAll(final int pageNumber);

    List<Student> findByPersonalData(final String name);
}
