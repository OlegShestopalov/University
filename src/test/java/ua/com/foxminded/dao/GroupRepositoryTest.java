package ua.com.foxminded.dao;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.domain.entity.Course;
import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.domain.entity.Group;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/scripts/schema.sql", "/scripts/data.sql"})
@ActiveProfiles("test")
public class GroupRepositoryTest {

    private final GroupRepository groupRepository;
    private final FacultyRepository facultyRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public GroupRepositoryTest(GroupRepository groupRepository, FacultyRepository facultyRepository, CourseRepository courseRepository) {
        this.groupRepository = groupRepository;
        this.facultyRepository = facultyRepository;
        this.courseRepository = courseRepository;
    }

    @Test
    void createGroup() {
        Faculty faculty = facultyRepository.getOne(1L);
        Course course = courseRepository.findById(1L);
        Group group = new Group("TEST", faculty, course);
        groupRepository.create(group);
        Group createdGroup = groupRepository.findById(group.getId());

        assertEquals(group, Hibernate.unproxy(createdGroup));
    }

    @Test
    void deleteGroup() {
        Group groupToBeDeleted = groupRepository.findById(1L);
        groupRepository.delete(groupToBeDeleted.getId());

        assertEquals(2, groupRepository.findAll().size());
    }

    @Test
    void updateGroup() {
        Faculty faculty = facultyRepository.getOne(1L);
        Course course = courseRepository.findById(1L);
        Group newGroup = new Group(1L, "TEST", faculty, course);
        groupRepository.update(newGroup);
        Group updatedGroup = groupRepository.findById(1L);

        assertEquals(newGroup, Hibernate.unproxy(updatedGroup));
    }

    @Test
    void findAllGroups() {
        List<Group> groups = groupRepository.findAll();

        assertEquals(3, groups.size());
    }

    @Test
    void findGroupById() {
        Faculty faculty = facultyRepository.getOne(1L);
        Course course = courseRepository.findById(1L);
        Group group = new Group(1L, "AAAA", faculty, course);
        Group groupInDB = groupRepository.findById(1L);

        assertEquals(group, Hibernate.unproxy(groupInDB));
    }
}
