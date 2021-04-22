package ua.com.foxminded.dao;

import ua.com.foxminded.domain.entity.Teacher;

import java.util.List;

public interface TeacherDAO extends GenericDAO<Teacher> {

    public List<Teacher> findAllTeachersBySubjectId(final Long id);

    public List<Teacher> findAllTeachersInFaculty(final Long id);
}
