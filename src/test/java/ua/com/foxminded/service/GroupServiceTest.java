package ua.com.foxminded.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ua.com.foxminded.dao.GroupRepository;
import ua.com.foxminded.domain.entity.Course;
import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.domain.entity.Student;
import ua.com.foxminded.domain.service.impl.GroupServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    void CreateGroup_WhenAddNewGroup_ThenCreatedNewGroupInDB() {
        Group group = new Group(1L, "test", faculty, course);

        groupService.create(group);

        verify(groupRepository, times(1)).save(group);
        verifyNoMoreInteractions(groupRepository);
    }

    @Test
    void DeleteGroupById_WhenInputId_ThenDeletedGroupFromDB() {
        Group group = new Group(1L, "test", faculty, course);

        groupService.delete(1L);

        verify(groupRepository, times(1)).deleteById(group.getId());
    }

    @Test
    void UpdateGroup_WhenChangeDataAboutGroup_ThenSaveUpdatedGroup() {
        Group group = new Group(1L, "test", faculty, course);

        groupService.create(group);

        verify(groupRepository, times(1)).save(group);
        verifyNoMoreInteractions(groupRepository);
    }

    @Test
    void FindAllGroupsByPages_WhenFindAll_ThenReturnedPagesWithGroups() {
        int pageNumber = 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("name"));
        Group one = new Group(1L, "test", faculty, course);
        Page<Group> groups = new PageImpl<>(Collections.singletonList(one));

        when(groupRepository.findAll(pageable)).thenReturn(groups);

        assertEquals(1, groupService.findAll(pageNumber).getTotalPages());
        assertEquals(1, groupService.findAll(pageNumber).getTotalElements());
        assertEquals(one, groupService.findAll(pageNumber).toList().get(0));
        verifyNoMoreInteractions(groupRepository);
    }

    @Test
    void FindGroupById_WhenInputId_ThenReturnedGroupById() {
        when(groupRepository.getOne(anyLong())).thenReturn(new Group(1L, "test", faculty, course));

        assertEquals("test", groupService.findById(1L).getName());
        assertEquals(faculty, groupService.findById(1L).getFaculty());
        assertEquals(course, groupService.findById(1L).getCourse());
        verifyNoMoreInteractions(groupRepository);
    }

    @Test
    void FindGroupsByName_WhenInputName_ThenReturnedGroupsByName() {
        List<Group> groups = new ArrayList<>(Collections.singleton(new Group(1L, "test", faculty, course)));

        when(groupRepository.findByName("test")).thenReturn(groups);

        assertEquals(groups, groupService.findByName("test"));
        assertEquals(1, groupService.findByName("test").size());
        verifyNoMoreInteractions(groupRepository);
    }
}
