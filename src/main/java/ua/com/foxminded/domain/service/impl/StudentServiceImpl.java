package ua.com.foxminded.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.DayDAO;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.domain.entity.Student;
import ua.com.foxminded.domain.service.StudentService;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDAO studentDAO;
    private final DayDAO dayDAO;

    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO, DayDAO dayDAO) {
        this.studentDAO = studentDAO;

        this.dayDAO = dayDAO;
    }

    @Override
    public void create(Student student) {
        studentDAO.create(student);
    }

    @Override
    public void delete(Long id) {
        studentDAO.delete(id);
    }

    @Override
    public void update(Long id, Student student) {
        studentDAO.update(student);
    }

    @Override
    public List<Student> findAll() {
        return studentDAO.findAll();
    }

    @Override
    public Student findById(Long id) {
        return studentDAO.findById(id);
    }

    @Override
    public Student findByName(String name) {
        return studentDAO.findByName(name);
    }

    @Override
    public List<Student> findStudentsInGroup(Long id) {
        return studentDAO.findAllStudentsInGroup(id);
    }

    @Override
    public List<Student> findEmailsInGroup(Long id) {
        return studentDAO.findAllEmailsInGroup(id);
    }
}
