package ua.com.foxminded.domain.service;

import ua.com.foxminded.domain.entity.Student;

import java.util.List;

public interface StudentService {

    void add(final Student student);

    void remove(final Long id);

    void update(final Long id, final Student student);

    List<Student> findAll();

    Student find(final Long id);

    Student findByName(final String name, final String surname);

    List<Student> findStudentsInGroup(final Long id);

    List<Student> findEmailsInGroup(final Long id);
}
