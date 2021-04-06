package ua.com.foxminded.domain.service;

import ua.com.foxminded.domain.entity.Faculty;

import java.util.List;

public interface FacultyService {

    void calculateFacultyFullness(final Long id);

    void add(final Faculty faculty);

    void remove(final Long id);

    void update(final Long id, final Faculty faculty);

    Faculty find(final Long id);

    List<Faculty> findFacultiesBySubjectId(final Long id);

    List<Faculty> findFacultiesByTeacherId(final Long id);
}
