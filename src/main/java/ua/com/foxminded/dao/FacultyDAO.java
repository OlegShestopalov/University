package ua.com.foxminded.dao;

import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.exception.QueryNotExecuteException;

import java.util.List;

public interface FacultyDAO {

    void add(final Faculty faculty) throws QueryNotExecuteException;

    void removeFaculty(final Long id);

    void update(final Long id, final Faculty faculty);

    Faculty findFaculty(final Long id);

    List<Faculty> findAllFaculties();

    List<Faculty> findAllFacultiesBySubjectId(final Long id);

    List<Faculty> findAllFacultiesByTeacherId(final Long id);
}
