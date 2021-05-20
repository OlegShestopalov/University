package ua.com.foxminded.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.dao.DayRepository;
import ua.com.foxminded.dao.StudentRepository;
import ua.com.foxminded.domain.entity.Student;
import ua.com.foxminded.domain.service.StudentService;

import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final DayRepository dayRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, DayRepository dayRepository) {
        this.studentRepository = studentRepository;
        this.dayRepository = dayRepository;
    }

    @Override
    public void create(Student student) {
        studentRepository.create(student);
    }

    @Override
    public void delete(Long id) {
        studentRepository.delete(id);
    }

    @Override
    public void update(Long id, Student student) {
        studentRepository.update(student);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student findByName(String name) {
        return studentRepository.findByName(name);
    }

    @Override
    public List<Student> findStudentsInGroup(Long id) {
        return studentRepository.findAllStudentsInGroup(id);
    }

    @Override
    public List<Student> findEmailsInGroup(Long id) {
        return studentRepository.findAllEmailsInGroup(id);
    }
}
