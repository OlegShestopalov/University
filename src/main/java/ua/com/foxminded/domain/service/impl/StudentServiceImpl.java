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
        studentRepository.save(student);
    }

    @Override
    public void delete(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.getOne(id);
    }
}
