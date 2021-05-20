package ua.com.foxminded.domain.service;

import ua.com.foxminded.domain.entity.Teacher;

import java.util.List;

public interface TeacherService {

    void create(final Teacher teacher);

    void delete(final Long id);

    Teacher findById(final Long id);

    List<Teacher> findAll();
}
