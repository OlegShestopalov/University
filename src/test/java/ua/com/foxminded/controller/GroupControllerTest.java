package ua.com.foxminded.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.com.foxminded.domain.controller.GroupController;
import ua.com.foxminded.domain.entity.Course;
import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.domain.service.GroupService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
public class GroupControllerTest {

    private List<Group> groups;
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;

    @Autowired
    private GroupController groupController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(groupController)
                .build();
    }

    @BeforeEach
    void init() {
        Faculty faculty = new Faculty();
        Course course = new Course();
        this.groups = new ArrayList<>();
        this.groups.add(new Group(1L, "group1", faculty, course));
        this.groups.add(new Group(2L, "group2", faculty, course));
        this.groups.add(new Group(3L, "group3", faculty, course));
    }

    @Test
    void findAllGroups() throws Exception {
        int pageNumber = 1;
        Faculty faculty = new Faculty();
        Course course = new Course();
        Group one = new Group(1L, "test", faculty, course);
        Page<Group> groups = new PageImpl<>(Collections.singletonList(one));

        when(groupService.findAll(pageNumber)).thenReturn(groups);

        mockMvc.perform(get("/groups/allGroups"))
                .andExpect(status().isOk())
                .andExpect(view().name("groups/allGroups"))
                .andExpect(model().attribute("groups", hasSize(1)));
    }
    @Test
    void findGroupsByName() throws Exception {
        when(groupService.findByName(any())).thenReturn(groups);

        mockMvc.perform(get("/groups/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("groups/groupsByName"))
                .andExpect(model().attribute("groups", hasSize(3)));

        verify(groupService, atLeastOnce()).findByName(any());
        verifyNoMoreInteractions(groupService);
    }

    @Test
    void findGroupById() throws Exception {
        when(groupService.findById(1L)).thenReturn(groups.get(0));

        mockMvc.perform(get("/groups/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("groups/group"))
                .andExpect(model().attribute("group", instanceOf(Group.class)));

        verify(groupService, times(1)).findById(1L);
        verifyNoMoreInteractions(groupService);
    }

    @Test
    void editGroup() throws Exception {
        when(groupService.findById(1L)).thenReturn(groups.get(0));

        mockMvc.perform(get("/groups/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("groups/edit"))
                .andExpect(model().attribute("group", instanceOf(Group.class)));
    }

    @Test
    void createNewGroup() throws Exception {
        verifyNoMoreInteractions(groupService);

        mockMvc.perform(get("/groups/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("groups/new"))
                .andExpect(model().attribute("group", instanceOf(Group.class)));
    }

    @Test
    public void deleteGroup() throws Exception {
        doNothing().when(groupService).delete(groups.get(0).getId());

        mockMvc.perform(
                get("/groups/{id}/delete", groups.get(0).getId()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/groups/allGroups"));

        verify(groupService, times(1)).delete(groups.get(0).getId());
    }

    @Test
    void updateGroup() throws Exception {
        doNothing().when(groupService).create(any(Group.class));

        mockMvc.perform(post("/groups/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("groups/edit"))
                .andExpect(model().attribute("group", instanceOf(Group.class)));
    }
}
