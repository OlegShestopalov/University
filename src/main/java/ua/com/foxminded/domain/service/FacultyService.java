package ua.com.foxminded.domain.service;

import ua.com.foxminded.domain.entity.Faculty;

import java.util.List;

public interface FacultyService {

    void create(final Faculty faculty);

    void delete(final Long id);

    List<Faculty> findAll();

    Faculty findById(final Long id);
}
