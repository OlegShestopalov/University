package ua.com.foxminded.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.dao.SubjectRepository;
import ua.com.foxminded.domain.entity.Subject;

import java.util.List;

@Repository
@Transactional
public class SubjectRepositoryImpl implements SubjectRepository {

    private final SessionFactory sessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectRepositoryImpl.class);

    @Autowired
    public SubjectRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Subject subject) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.persist(subject);
        session.getTransaction().commit();
        session.close();
        LOGGER.info("Subject was successfully saved. Subject details: {}", subject);
    }

    @Override
    public void delete(Long id) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        Subject subject = (Subject) session.load(Subject.class, id);
        if (subject != null) {
            session.delete(subject);
        }
        session.getTransaction().commit();
        session.close();
        LOGGER.info("Subject was successfully deleted. Subject details: {}", subject);
    }

    @Override
    public void update(Subject subject) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.update(subject);
        session.getTransaction().commit();
        session.close();
        LOGGER.info("Subject was successfully updated. Subject details: {}", subject);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Subject> findAll() {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        List<Subject> subjects = session.createQuery("from Subject").list();
        session.getTransaction().commit();
        session.close();
        LOGGER.info("Subjects were successfully found");

        return subjects;
    }

    @Override
    public Subject findById(Long id) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        Subject subject = (Subject) session.load(Subject.class, id);
        session.getTransaction().commit();
        LOGGER.info("Subject was successfully found. Subject details: {}", subject);

        return subject;
    }

    @Override
    public List<Subject> findAllTeacherSubjects(Long id) {
        return null;
    }

    @Override
    public List<Subject> findAllFacultySubjects(Long id) {
        return null;
    }
}
