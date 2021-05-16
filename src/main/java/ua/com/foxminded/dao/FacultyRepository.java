package ua.com.foxminded.dao;

import ua.com.foxminded.domain.entity.Faculty;

import java.util.List;

public interface FacultyRepository extends GenericRepository<Faculty> {

    List<Faculty> findAllFacultiesBySubjectId(final Long id);

    List<Faculty> findAllFacultiesByTeacherId(final Long id);
}
