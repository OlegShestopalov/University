package ua.com.foxminded.dao;

import ua.com.foxminded.domain.entity.Faculty;

import java.util.List;

public interface FacultyDAO extends GenericDAO<Faculty> {

    List<Faculty> findAllFacultiesBySubjectId(final Long id);

    List<Faculty> findAllFacultiesByTeacherId(final Long id);
}
