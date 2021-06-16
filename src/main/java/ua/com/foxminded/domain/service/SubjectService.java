package ua.com.foxminded.domain.service;

import ua.com.foxminded.domain.entity.Subject;

import java.util.List;

public interface SubjectService {

    void create(final Subject subject);

    void delete(final Long id);

    List<Subject> findAll();

    Subject findById(final Long id);
}
