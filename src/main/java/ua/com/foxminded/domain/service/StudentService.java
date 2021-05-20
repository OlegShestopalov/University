package ua.com.foxminded.domain.service;

import ua.com.foxminded.domain.entity.Student;

import java.util.List;

public interface StudentService {

    void create(final Student student);

    void delete(final Long id);

    List<Student> findAll();

    Student findById(final Long id);
}
