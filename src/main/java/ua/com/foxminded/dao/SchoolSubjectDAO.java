package ua.com.foxminded.dao;

import ua.com.foxminded.domain.entity.SchoolSubject;

import java.util.List;

public interface SchoolSubjectDAO extends GenericDAO<SchoolSubject> {

    public List<SchoolSubject> findAllTeacherSubjects(final Long id);

    public List<SchoolSubject> findAllFacultySubjects(final Long id);
}
