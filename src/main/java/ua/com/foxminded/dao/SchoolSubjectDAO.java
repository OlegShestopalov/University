package ua.com.foxminded.dao;

import ua.com.foxminded.domain.entity.SchoolSubject;

import java.util.List;

public interface SchoolSubjectDAO {

    public void add(final SchoolSubject schoolSubject);

    public void removeSubject(final Long id);

    public void update(final Long id, final SchoolSubject schoolSubject);

    public SchoolSubject findSubject(final Long id);

    public List<SchoolSubject> findAllTeacherSubjects(final Long id);

    public List<SchoolSubject> findAllFacultySubjects(final Long id);
}
