package ua.com.foxminded.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ua.com.foxminded.config.SpringConfig;
import ua.com.foxminded.domain.entity.Course;
import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.domain.entity.Group;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfig.class, loader = AnnotationConfigContextLoader.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/scripts/schema.sql", "/scripts/data.sql"})
@ActiveProfiles("test")
public class GroupDAOTest {

    private final GroupDAO groupDAO;
    private final FacultyDAO facultyDAO;
    private final CourseDAO courseDAO;

    @Autowired
    public GroupDAOTest(GroupDAO groupDAO, FacultyDAO facultyDAO, CourseDAO courseDAO) {
        this.groupDAO = groupDAO;
        this.facultyDAO = facultyDAO;
        this.courseDAO = courseDAO;
    }

    @Test
    void createGroup() {
        Faculty faculty = facultyDAO.findById(1L);
        Course course = courseDAO.findById(1L);
        Group group = new Group("TEST", faculty, course);
        groupDAO.create(group);
        Group createdGroup = groupDAO.findById(group.getId());

        assertEquals(group.getId(), createdGroup.getId());
        assertEquals(group.getName(), createdGroup.getName());
        assertEquals(group.getFaculty(), createdGroup.getFaculty());
        assertEquals(group.getCourse(), createdGroup.getCourse());
    }

    @Test
    void deleteGroup() {
        Group groupToBeDeleted = groupDAO.findById(1L);
        groupDAO.delete(groupToBeDeleted.getId());

        assertEquals(2, groupDAO.findAll().size());
    }

    @Test
    void updateGroup() {
        Faculty faculty = facultyDAO.findById(1L);
        Course course = courseDAO.findById(1L);
        Group newGroup = new Group(1L,"TEST", faculty, course);
        groupDAO.update(newGroup);
        Group updatedGroup = groupDAO.findById(1L);

        assertEquals(newGroup.getId(), updatedGroup.getId());
        assertEquals(newGroup.getName(), updatedGroup.getName());
        assertEquals(newGroup.getFaculty(), updatedGroup.getFaculty());
        assertEquals(newGroup.getCourse(), updatedGroup.getCourse());
    }

    @Test
    void findAllGroups() {
        List<Group> groups = groupDAO.findAll();

        assertEquals(3, groups.size());
    }

    @Test
    void findGroupById() {
        Group group = groupDAO.findById(1L);

        assertEquals("AAAA", group.getName());
    }

    @Test
    void findAllGroupsInFaculty() {
//        List<Group> groups = groupDAO.findAllGroupsInFaculty(1L);
//
//        assertEquals(1, groups.size());
//        assertEquals("AAAA", groups.get(0).getName());
    }
}
