package ua.com.foxminded.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.dao.FacultyRepository;
import ua.com.foxminded.domain.entity.Faculty;

import java.util.List;

@Repository
public class FacultyRepositoryImpl implements FacultyRepository {

    private final SessionFactory sessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyRepositoryImpl.class);

    @Autowired
    public FacultyRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Faculty> findAllFacultiesBySubjectId(Long id) {
        return null;
    }

    @Override
    public List<Faculty> findAllFacultiesByTeacherId(Long id) {
        return null;
    }

    @Override
    public void create(Faculty faculty) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.persist(faculty);
        session.getTransaction().commit();
        session.close();
        LOGGER.info("Faculty was successfully saved. Faculty details: {}", faculty);
    }

    @Override
    public void delete(Long id) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        Faculty faculty = (Faculty) session.load(Faculty.class, id);
        if (faculty != null) {
            session.delete(faculty);
        }
        session.getTransaction().commit();
        session.close();
        LOGGER.info("Faculty was successfully deleted. Faculty details: {}", faculty);
    }

    @Override
    public void update(Faculty faculty) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.update(faculty);
        session.getTransaction().commit();
        session.close();
        LOGGER.info("Faculty was successfully updated. Faculty details: {}", faculty);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Faculty> findAll() {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        List<Faculty> faculties = session.createQuery("from Faculty").list();
        session.getTransaction().commit();
        session.close();
        LOGGER.info("Faculties were successfully found");

        return faculties;
    }

    @Override
    public Faculty findById(Long id) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        Faculty faculty = (Faculty) session.load(Faculty.class, id);
        session.getTransaction().commit();
        LOGGER.info("Faculty was successfully found. Faculty details: {}", faculty);

        return faculty;
    }
}
