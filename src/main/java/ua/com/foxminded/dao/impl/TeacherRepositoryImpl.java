package ua.com.foxminded.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.dao.TeacherRepository;
import ua.com.foxminded.domain.entity.Teacher;

import java.util.List;

@Repository
@Transactional
public class TeacherRepositoryImpl implements TeacherRepository {

    private final SessionFactory sessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherRepositoryImpl.class);

    @Autowired
    public TeacherRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Teacher teacher) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.persist(teacher);
        session.getTransaction().commit();
        session.close();
        LOGGER.info("Teacher was successfully saved. Teacher details: {}", teacher);
    }

    @Override
    public void delete(Long id) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        Teacher teacher = (Teacher) session.load(Teacher.class, id);
        if (teacher != null) {
            session.delete(teacher);
        }
        session.getTransaction().commit();
        session.close();
        LOGGER.info("Teacher was successfully deleted. Teacher details: {}", teacher);
    }

    @Override
    public void update(Teacher teacher) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.update(teacher);
        session.getTransaction().commit();
        session.close();
        LOGGER.info("Teacher was successfully updated. Teacher details: {}", teacher);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Teacher> findAll() {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        List<Teacher> teachers = session.createQuery("from Teacher").list();
        session.getTransaction().commit();
        session.close();
        LOGGER.info("Teachers were successfully found");


        return teachers;
    }

    @Override
    public Teacher findById(Long id) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        Teacher teacher = (Teacher) session.load(Teacher.class, id);
        session.getTransaction().commit();
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
