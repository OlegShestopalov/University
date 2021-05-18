package ua.com.foxminded.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.dao.TeacherRepository;
import ua.com.foxminded.domain.entity.Teacher;
import ua.com.foxminded.domain.service.TeacherService;

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
    public void create(Teacher teacher) {
        teacherRepository.create(teacher);
    }

    @Override
    public void delete(Long id) {
        teacherRepository.delete(id);
    }

    @Override
    public void update(Long id, Teacher teacher) {
        teacherRepository.update(teacher);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher findById(Long id) {
        return teacherRepository.findById(id);
    }

    @Override
    public List<Teacher> findTeachersBySubject(Long id) {
        return teacherRepository.findAllTeachersBySubjectId(id);
    }

    @Override
    public List<Teacher> findTeachersInFaculty(Long id) {
        return teacherRepository.findAllTeachersInFaculty(id);
    }
}
