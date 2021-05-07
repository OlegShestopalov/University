package ua.com.foxminded.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.domain.entity.Student;

import java.util.List;

@Repository
@Transactional
public class StudentDAOImpl implements StudentDAO {

    private final SessionFactory sessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentDAOImpl.class);

    @Autowired
    public StudentDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Student student) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(student);
        LOGGER.info("Student was successfully saved. Student details: {}", student);
    }

    @Override
    public void delete(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Student student = (Student) session.load(Student.class, id);
        if (student != null) {
            session.delete(student);
        }
        LOGGER.info("Student was successfully deleted. Student details: {}", student);
    }

    @Override
    public void update(Student student) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(student);
        LOGGER.info("Student was successfully updated. Student details: {}", student);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Student> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Student> students = session.createQuery("from Student").list();
        LOGGER.info("Students were successfully found");

        return students;
    }

    @Override
    public Student findById(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Student student = (Student) session.load(Student.class, id);
        LOGGER.info("Student was successfully found. Student details: {}", student);

        return student;
    }

    @Override
    public Student findByName(String name) {
        Session session = this.sessionFactory.getCurrentSession();
        Student student = (Student) session.load(Student.class, name);
        LOGGER.info("Student was successfully found. Student details: {}", student);

        return student;
    }

    //    @SuppressWarnings("unchecked")
    @Override
    public List<Student> findAllStudentsInGroup(Long id) {
        return null;
    }

    //    @SuppressWarnings("unchecked")
    @Override
    public List<Student> findAllEmailsInGroup(Long id) {
        return null;
    }
}
