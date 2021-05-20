package ua.com.foxminded.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.dao.CourseRepository;
import ua.com.foxminded.domain.entity.Course;

import java.util.List;

@Repository
public class CourseRepositoryImpl implements CourseRepository {

    private final SessionFactory sessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseRepositoryImpl.class);

    @Autowired
    public CourseRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Course course) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.persist(course);
        session.getTransaction().commit();
        session.close();
        LOGGER.info("Course was successfully saved. Course details: {}", course);
    }

    @Override
    public void delete(Long id) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        Course course = (Course) session.load(Course.class, id);
        if (course != null) {
            session.delete(course);
        }
        session.getTransaction().commit();
        session.close();
        LOGGER.info("Course was successfully deleted. Course details: {}", course);
    }

    @Override
    public void update(Course course) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.update(course);
        session.getTransaction().commit();
        session.close();
        LOGGER.info("Course was successfully updated. Course details: {}", course);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Course> findAll() {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        List<Course> courses = session.createQuery("from Course").list();
        session.getTransaction().commit();
        session.close();
        LOGGER.info("Courses were successfully found");

        return courses;
    }

    @Override
    public Course findById(Long id) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        Course course = (Course) session.load(Course.class, id);
        session.getTransaction().commit();
        LOGGER.info("Course was successfully found. Course details: {}", course);


        return course;
    }
}
