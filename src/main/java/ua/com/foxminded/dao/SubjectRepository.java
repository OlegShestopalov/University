package ua.com.foxminded.dao;

import ua.com.foxminded.domain.entity.Subject;

import java.util.List;

public interface SubjectRepository extends GenericRepository<Subject> {

    public List<Subject> findAllTeacherSubjects(final Long id);

    public List<Subject> findAllFacultySubjects(final Long id);
}
