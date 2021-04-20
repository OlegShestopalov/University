package ua.com.foxminded.domain.service;

import ua.com.foxminded.domain.entity.Faculty;

import java.util.List;

public interface FacultyService {

    void create(final Faculty faculty);

    void delete(final Long id);

    void update(final Long id, final Faculty faculty);

    List<Faculty> findAll();

    Faculty findById(final Long id);

    void calculateFacultyFullness(final Long id);

    List<Faculty> findFacultiesBySubjectId(final Long id);

    List<Faculty> findFacultiesByTeacherId(final Long id);
}
