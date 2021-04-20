package ua.com.foxminded.domain.service;

import ua.com.foxminded.domain.entity.Student;

import java.util.List;

public interface StudentService {

    void create(final Student student);

    void delete(final Long id);

    void update(final Long id, final Student student);

    List<Student> findAll();

    Student findById(final Long id);

    Student findByName(final String name);

    List<Student> findStudentsInGroup(final Long id);

    List<Student> findEmailsInGroup(final Long id);
}
