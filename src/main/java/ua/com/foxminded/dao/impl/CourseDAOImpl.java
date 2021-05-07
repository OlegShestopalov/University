package ua.com.foxminded.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.dao.CourseDAO;
import ua.com.foxminded.domain.entity.Course;

import java.util.List;

@Repository
@Transactional
public class CourseDAOImpl implements CourseDAO {

    private final SessionFactory sessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseDAOImpl.class);

    @Autowired
    public CourseDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Course course) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(course);
        LOGGER.info("Course was successfully saved. Course details: {}", course);
    }

    @Override
    public void delete(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Course course = (Course) session.load(Course.class, id);
        if (course != null) {
            session.delete(course);
        }
        LOGGER.info("Course was successfully deleted. Course details: {}", course);
    }

    @Override
    public void update(Course course) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(course);
        LOGGER.info("Course was successfully updated. Course details: {}", course);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Course> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Course> courses = session.createQuery("from Course").list();
        LOGGER.info("Courses were successfully found");

        return courses;
    }

    @Override
    public Course findById(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Course course = (Course) session.load(Course.class, id);
        LOGGER.info("Course was successfully found. Course details: {}", course);

        return course;
    }
}
