package ua.com.foxminded.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.dao.DayRepository;
import ua.com.foxminded.domain.entity.Day;

import java.util.List;

@Repository
public class DayRepositoryImpl implements DayRepository {

    private final SessionFactory sessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(DayRepositoryImpl.class);

    @Autowired
    public DayRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Day day) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.persist(day);
        session.getTransaction().commit();
        session.close();
        LOGGER.info("Day was successfully saved. Day details: {}", day);
    }

    @Override
    public void delete(Long id) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        Day day = (Day) session.load(Day.class, id);
        if (day != null) {
            session.delete(day);
        }
        session.getTransaction().commit();
        session.close();
        LOGGER.info("Day was successfully deleted. Day details: {}", day);
    }

    @Override
    public void update(Day day) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.update(day);
        session.getTransaction().commit();
        session.close();
        LOGGER.info("Day was successfully updated. Day details: {}", day);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Day> findAll() {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        List<Day> days = session.createQuery("from Day").list();
        session.getTransaction().commit();
        session.close();
        LOGGER.info("Days were successfully found");

        return days;
    }

    @Override
    public Day findById(Long id) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        Day day = (Day) session.load(Day.class, id);
        session.getTransaction().commit();
        LOGGER.info("Day was successfully found. Day details: {}", day);

        return day;
    }
}
