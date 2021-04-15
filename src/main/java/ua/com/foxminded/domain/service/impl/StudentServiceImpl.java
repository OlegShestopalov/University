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
    public void add(Student student) {
        studentDAO.add(student);
    }

    @Override
    public void remove(Long id) {
        studentDAO.removeStudent(id);
    }

    @Override
    public void update(Long id, Student student) {
        studentDAO.update(id, student);
    }

    @Override
    public List<Student> findAll() {
        return studentDAO.findAllStudents();
    }

    @Override
    public Student findById(Long id) {
        return studentDAO.findStudentById(id);
    }

    @Override
    public Student findByName(String name, String surname) {
        return studentDAO.findByName(name, surname);
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
