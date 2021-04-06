package ua.com.foxminded.domain.service;

import ua.com.foxminded.domain.entity.Teacher;

import java.util.List;

public interface TeacherService {

    void add(final Teacher teacher);

    void remove(final Long id);

    void update(final Long id, final Teacher teacher);

    Teacher find(final Long id);

    List<Teacher> findAll();

    List<Teacher> findEmails();

    List<Teacher> findTeachersBySubject(final Long id);

    List<Teacher> findTeachersInFaculty(final Long id);
}
