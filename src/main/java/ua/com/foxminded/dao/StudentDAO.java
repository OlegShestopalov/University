package ua.com.foxminded.dao;

import ua.com.foxminded.domain.entity.Student;

import java.util.List;

public interface StudentDAO {

    public void add(final Student student);

    public void removeStudent(final Long id);

    public void update(final Long id, final Student student);

    public List<Student> findAllStudents();

    public Student findStudent(final Long id);

    public Student findByName(final String name, final String surname);

    public List<Student> findAllStudentsInGroup(final Long id);

    public List<Student> findAllEmailsInGroup(final Long id);


}
