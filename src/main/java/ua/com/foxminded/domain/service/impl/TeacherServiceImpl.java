package ua.com.foxminded.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.DayDAO;
import ua.com.foxminded.dao.TeacherDAO;
import ua.com.foxminded.domain.entity.Teacher;
import ua.com.foxminded.domain.service.TeacherService;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherDAO teacherDAO;
    private final DayDAO dayDAO;

    @Autowired
    public TeacherServiceImpl(TeacherDAO teacherDAO, DayDAO dayDAO) {
        this.teacherDAO = teacherDAO;
        this.dayDAO = dayDAO;
    }

    @Override
    public void add(Teacher teacher) {
        teacherDAO.add(teacher);
    }

    @Override
    public void remove(Long id) {
        teacherDAO.removeTeacher(id);
    }

    @Override
    public void update(Long id, Teacher teacher) {
        teacherDAO.update(id, teacher);
    }

    @Override
    public Teacher find(Long id) {
        return teacherDAO.findTeacherById(id);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherDAO.findAllTeachers();
    }

    @Override
    public List<Teacher> findEmails() {
        return teacherDAO.findAllEmails();
    }

    @Override
    public List<Teacher> findTeachersBySubject(Long id) {
        return teacherDAO.findAllTeachersBySubjectId(id);
    }

    @Override
    public List<Teacher> findTeachersInFaculty(Long id) {
        return teacherDAO.findAllTeachersInFaculty(id);
    }
}
