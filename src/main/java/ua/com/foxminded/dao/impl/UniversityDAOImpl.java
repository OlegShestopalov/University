package ua.com.foxminded.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.dao.UniversityDAO;
import ua.com.foxminded.domain.entity.Audience;
import ua.com.foxminded.domain.entity.Group;

import java.util.List;

@Repository
public class UniversityDAOImpl implements UniversityDAO {

    private final SessionFactory sessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityDAOImpl.class);

    @Autowired
    public UniversityDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Audience> findAllAudiences() {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        List<Audience> audiences = session.createQuery("from Audience").list();
        session.getTransaction().commit();
        session.close();
        LOGGER.info("Audiences were successfully found");

        return audiences;
    }
}
