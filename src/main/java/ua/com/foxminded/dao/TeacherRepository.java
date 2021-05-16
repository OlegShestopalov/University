package ua.com.foxminded.dao;

import ua.com.foxminded.domain.entity.Teacher;

import java.util.List;

public interface TeacherRepository extends GenericRepository<Teacher> {

    public List<Teacher> findAllTeachersBySubjectId(final Long id);

    public List<Teacher> findAllTeachersInFaculty(final Long id);
}
