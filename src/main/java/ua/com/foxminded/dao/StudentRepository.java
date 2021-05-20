package ua.com.foxminded.dao;

import ua.com.foxminded.domain.entity.Student;

import java.util.List;

public interface StudentRepository extends GenericRepository<Student> {

    public Student findByName(final String name);

    public List<Student> findAllStudentsInGroup(final Long id);

    public List<Student> findAllEmailsInGroup(final Long id);


}