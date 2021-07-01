package ua.com.foxminded.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.com.foxminded.domain.entity.Course;
import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.domain.rest.GroupRestController;
import ua.com.foxminded.domain.service.GroupService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class GroupRestControllerTest {

    @MockBean
    private GroupService groupService;

    private MockMvc mockMvc;
    private final GroupRestController groupRestController;
    private Faculty faculty;
    private Course course;

    @Autowired
    public GroupRestControllerTest(GroupRestController groupRestController) {
        this.groupRestController = groupRestController;
    }

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(groupRestController)
                .build();
    }

    @Test
    public void shouldReturnListOfGroups() throws Exception {
        when(groupService.findAll()).thenReturn(Arrays.asList(
                new Group(1L, "test1", faculty, course),
                new Group(2L, "test2", faculty, course)));

        mockMvc.perform(get("/api/v1/groups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("test1", "test2")));
    }

    @Test
    public void shouldReturnGroupById() throws Exception {
        when(groupService.findById(anyLong())).thenReturn(
                new Group(1L, "test", faculty, course));

        mockMvc.perform(get("/api/v1/groups/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("test")));
    }

    @Test
    public void shouldReturnGroupByName() throws Exception {
        when(groupService.findByName("test")).thenReturn(Arrays.asList(
                new Group(1L, "test", faculty, course)));

        mockMvc.perform(get("/api/v1/groups/name?name=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("test")));

        verify(groupService, times(1)).findByName(anyString());
    }

    @Test
    public void shouldCreateNewGroup() throws Exception {
        mockMvc.perform(post("/api/v1/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"test\"}"))
                .andExpect(status().isOk());

        verify(groupService, times(1)).create(any(Group.class));
    }

    @Test
    public void shouldUpdateGroup() throws Exception {
        mockMvc.perform(put("/api/v1/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"test\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Faculty successfully updated"));

        verify(groupService, times(1)).update(any(Group.class));
    }

    @Test
    public void groupNameShouldBeValid() throws Exception {
        Group group = new Group("", faculty, course);

        mockMvc.perform(put("/api/v1/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(faculty)))
                .andExpect(status().isBadRequest());

        verify(groupService, times(0)).update(group);
    }

    @Test
    public void shouldDeleteGroup() throws Exception {
        mockMvc.perform(delete("/api/v1/groups/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andReturn();

        verify(groupService, times(1)).delete(anyLong());
    }

    @Test
    public void shouldReturnPageWithGroups() throws Exception {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group(1L, "test1", faculty, course));
        Page<Group> page = new PageImpl<>(groups);

        given(groupService.findAll(1)).willReturn(page);

        mockMvc.perform(get("/api/v1/groups/pages/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("test1")));
    }
}
