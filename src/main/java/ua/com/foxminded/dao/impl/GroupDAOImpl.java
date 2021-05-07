package ua.com.foxminded.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.dao.GroupDAO;
import ua.com.foxminded.domain.entity.Group;

import java.util.List;

@Repository
@Transactional
public class GroupDAOImpl implements GroupDAO {

    private final SessionFactory sessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupDAOImpl.class);

    @Autowired
    public GroupDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Group group) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(group);
        LOGGER.info("Group was successfully saved. Group details: {}", group);
    }

    @Override
    public void delete(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Group group = (Group) session.load(Group.class, id);
        if (group != null) {
            session.delete(group);
        }
        LOGGER.info("Group was successfully deleted. Group details: {}", group);
    }

    @Override
    public void update(Group group) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(group);
        LOGGER.info("Group was successfully updated. Group details: {}", group);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Group> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Group> groups = session.createQuery("from Group").list();
        LOGGER.info("Groups were successfully found");

        return groups;
    }

    @Override
    public Group findById(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Group group = (Group) session.load(Group.class, id);
        LOGGER.info("Group was successfully found. Group details: {}", group);

        return group;
    }

//    @SuppressWarnings("unchecked")
    @Override
    public List<Group> findAllGroupsInFaculty(Long id) {
//        Session session = this.sessionFactory.getCurrentSession();
//        List<Group> groups = (List<Group>) session.load(Group.class, id);
//        LOGGER.info("Groups in faculty was successfully found");
//
//        return groups;
        return null;
    }
}
