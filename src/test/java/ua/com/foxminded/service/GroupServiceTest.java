package ua.com.foxminded.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.dao.GroupRepository;
import ua.com.foxminded.domain.entity.Course;
import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.domain.service.impl.GroupServiceImpl;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupServiceImpl groupService;
    private Faculty faculty;
    private Course course;

    @Test
    void crateGroup() {
        Group group = new Group(1L, "test", faculty, course);

        groupService.create(group);

        verify(groupRepository, times(1)).save(group);
        verifyNoMoreInteractions(groupRepository);
    }

    @Test
    void deleteGroupById() {
        Group group = new Group(1L, "test", faculty, course);

        groupService.delete(1L);

        verify(groupRepository, times(1)).deleteById(group.getId());
    }

    @Test
    void whenAddNewGroupsItShouldReturnAllGroups() {
        Group one = new Group(1L, "test", faculty, course);
        Group two = new Group(2L, "test", faculty, course);
        Group three = new Group(3L, "test", faculty, course);

        when(groupRepository.findAll()).thenReturn(Stream.of(one, two, three).collect(Collectors.toList()));

        assertEquals(3, groupService.findAll().size());
        assertEquals(one, groupService.findAll().get(0));
        assertEquals(two, groupService.findAll().get(1));
        verifyNoMoreInteractions(groupRepository);
    }

    @Test
    void whenInputIdItShouldReturnStudentById() {
        when(groupRepository.getOne(anyLong())).thenReturn(new Group(1L, "test", faculty, course));

        assertEquals("test", groupService.findById(1L).getName());
        assertEquals(faculty, groupService.findById(1L).getFaculty());
        assertEquals(course, groupService.findById(1L).getCourse());
        verifyNoMoreInteractions(groupRepository);
    }
}
