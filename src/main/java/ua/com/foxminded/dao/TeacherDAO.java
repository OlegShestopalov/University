package ua.com.foxminded.dao;

import ua.com.foxminded.domain.entity.Teacher;

import java.util.List;

public interface TeacherDAO {

    public void add(final Teacher teacher);

    public void removeTeacher(final Long id);

    public void update(final Long id, final Teacher teacher);

    public Teacher findTeacher(final Long id);

    public List<Teacher> findAllTeachers();

    public List<Teacher> findAllEmails();

    public List<Teacher> findAllTeachersBySubjectId(final Long id);

    public List<Teacher> findAllTeachersInFaculty(final Long id);
}
