package ua.com.foxminded.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.dao.DayDAO;
import ua.com.foxminded.domain.entity.Day;

import java.util.List;

@Repository
@Transactional
public class DayDAOImpl implements DayDAO {

    private final SessionFactory sessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(DayDAOImpl.class);

    @Autowired
    public DayDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Day day) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(day);
        LOGGER.info("Day was successfully saved. Day details: {}", day);
    }

    @Override
    public void delete(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Day day = (Day) session.load(Day.class, id);
        if (day != null) {
            session.delete(day);
        }
        LOGGER.info("Day was successfully deleted. Day details: {}", day);
    }

    @Override
    public void update(Day day) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(day);
        LOGGER.info("Day was successfully updated. Day details: {}", day);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Day> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Day> days = session.createQuery("from Day").list();
        LOGGER.info("Days were successfully found");

        return days;
    }

    @Override
    public Day findById(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Day day = (Day) session.load(Day.class, id);
        LOGGER.info("Day was successfully found. Day details: {}", day);

        return day;
    }
}
