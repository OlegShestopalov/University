package ua.com.foxminded.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.dao.TeacherDAO;
import ua.com.foxminded.domain.entity.Teacher;

import java.util.List;

@Repository
@Transactional
public class TeacherDAOImpl implements TeacherDAO {

    private final SessionFactory sessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherDAOImpl.class);

    @Autowired
    public TeacherDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Teacher teacher) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(teacher);
        LOGGER.info("Teacher was successfully saved. Teacher details: {}", teacher);
    }

    @Override
    public void delete(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Teacher teacher = (Teacher) session.load(Teacher.class, id);
        if (teacher != null) {
            session.delete(teacher);
        }
        LOGGER.info("Teacher was successfully deleted. Teacher details: {}", teacher);
    }

    @Override
    public void update(Teacher teacher) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(teacher);
        LOGGER.info("Teacher was successfully updated. Teacher details: {}", teacher);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Teacher> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Teacher> teachers = session.createQuery("from Teacher").list();
        LOGGER.info("Teachers were successfully found");

        return teachers;
    }

    @Override
    public Teacher findById(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Teacher teacher = (Teacher) session.load(Teacher.class, id);
        LOGGER.info("Teacher was successfully found. Teacher details: {}", teacher);

        return teacher;
    }

    @Override
    public List<Teacher> findAllTeachersBySubjectId(Long id) {
        return null;
    }

    @Override
    public List<Teacher> findAllTeachersInFaculty(Long id) {
        return null;
    }
}
