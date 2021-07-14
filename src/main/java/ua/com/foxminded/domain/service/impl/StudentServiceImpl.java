package ua.com.foxminded.domain.service.impl;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.dao.StudentRepository;
import ua.com.foxminded.domain.entity.Student;
import ua.com.foxminded.domain.service.StudentService;
import ua.com.foxminded.exception.AlreadyExistException;

import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void create(Student student) throws AlreadyExistException {
        if (!studentRepository.findByNameOrSurnameOrGroup(student.getName()).isEmpty()) {
            throw new AlreadyExistException("Students with the same name already exists");
        }
        studentRepository.save(student);
    }

    @Override
    public void update(Student student) throws NotFoundException {
        studentRepository.save(student);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Student student = studentRepository.findById(id).get();
        if (student == null) {
            throw new NotFoundException("Student could not be found");
        }
        studentRepository.deleteById(id);
    }

    @Override
    public Student findById(Long id) throws NotFoundException {
        Student student = studentRepository.findById(id).get();
        if (student == null) {
            throw new NotFoundException("Student could not be found");
        }
        return student;
    }

    @Override
    public Page<Student> findAll(int pageNumber) throws NotFoundException {
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("name"));
        Page<Student> students = studentRepository.findAll(pageable);
        if (students == null) {
            throw new NotFoundException("Page with teachers could not be found");
        }
        return students;
    }

    @Override
    public List<Student> findByPersonalData(String name) throws NotFoundException {
        List<Student> students = studentRepository.findByNameOrSurnameOrGroup(name);
        if (students == null) {
            throw new NotFoundException("Teachers could not be found");
        }
        return students;
    }

    @Override
    public List<Student> findAll() throws NotFoundException {
        List<Student> students = studentRepository.findAll();
        if (students == null) {
            throw new NotFoundException("Teachers could not be  found");
        }
        return students;
    }
}
