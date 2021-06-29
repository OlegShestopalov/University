package ua.com.foxminded.domain.service.impl;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.dao.TeacherRepository;
import ua.com.foxminded.domain.entity.Teacher;
import ua.com.foxminded.domain.service.TeacherService;
import ua.com.foxminded.exception.AlreadyExistException;

import java.util.List;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public void create(Teacher teacher) throws AlreadyExistException {
        if (teacherRepository.findByNameOrSurname(teacher.getName()) != null) {
            throw new AlreadyExistException("Teacher with the same name already exists");
        }
        teacherRepository.save(teacher);
    }

    @Override
    public void update(Teacher teacher) throws NotFoundException {
        if (teacherRepository.findByNameOrSurname(teacher.getName()) == null) {
            throw new NotFoundException("Teacher with that name does not exists");
        }
        teacherRepository.save(teacher);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Teacher teacher = teacherRepository.findById(id).get();
        if (teacher == null) {
            throw new NotFoundException("Teacher could not be found");
        }
        teacherRepository.deleteById(id);
    }

    @Override
    public Page<Teacher> findAll(int pageNumber) throws NotFoundException {
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("name"));
        Page<Teacher> teachers = teacherRepository.findAll(pageable);
        if (teachers == null) {
            throw new NotFoundException("Page with teachers could not be found");
        }
        return teachers;
    }

    @Override
    public Teacher findById(Long id) throws NotFoundException {
        Teacher teacher = teacherRepository.findById(id).get();
        if (teacher == null) {
            throw new NotFoundException("Teacher could not be found");
        }
        return teacher;
    }

    @Override
    public List<Teacher> findByPersonalData(String name) throws NotFoundException {
        List<Teacher> teachers = teacherRepository.findByNameOrSurname(name);
        if (teachers == null) {
            throw new NotFoundException("Teachers could not be found");
        }
        return teachers;

    }

    @Override
    public List<Teacher> findAll() throws NotFoundException {
        List<Teacher> teachers = teacherRepository.findAll();
        if (teachers == null) {
            throw new NotFoundException("Teachers could not be  found");
        }
        return teachers;
    }
}
