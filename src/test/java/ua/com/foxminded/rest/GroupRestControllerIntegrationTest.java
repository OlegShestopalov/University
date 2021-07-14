package ua.com.foxminded.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/scripts/schema.sql", "/scripts/data.sql"})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GroupRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAllGroups() throws Exception {
        this.mockMvc.perform(get("/api/v1/groups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[" +
                        "{\"id\":1,\"name\":\"AAAA\",\"faculty\":{\"id\":1,\"name\":\"Electronics\"},\"course\":{\"id\":1,\"name\":\"first\"}}," +
                        "{\"id\":2,\"name\":\"BBBB\",\"faculty\":{\"id\":2,\"name\":\"Programming\"},\"course\":{\"id\":2,\"name\":\"second\"}}," +
                        "{\"id\":3,\"name\":\"CCCC\",\"faculty\":{\"id\":3,\"name\":\"Psychology\"},\"course\":{\"id\":3,\"name\":\"third\"}}]"));
    }

    @Test
    void findGroupById() throws Exception {
        this.mockMvc.perform(get("/api/v1/groups/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("{\"id\":1,\"name\":\"AAAA\",\"faculty\":{\"id\":1,\"name\":\"Electronics\"},\"course\":{\"id\":1,\"name\":\"first\"}}"));
    }

    @Test
    void findGroupByIdWhenTodoIsNotFound() throws Exception {
        this.mockMvc.perform(get("/api/v1/groups/{id}", 5L))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findGroupsByName() throws Exception {
        this.mockMvc.perform(get("/api/v1/groups/name?name=AAAA"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[" +
                        "{\"id\":1,\"name\":\"AAAA\",\"faculty\":{\"id\":1,\"name\":\"Electronics\"},\"course\":{\"id\":1,\"name\":\"first\"}}]"));
    }

    @Test
    void findGroupsByNameWhenTodoIsNotFound() throws Exception {
        this.mockMvc.perform(get("/api/v1/groups/name?name=test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    void createNewGroup() throws Exception {
        this.mockMvc.perform(post("/api/v1/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"test\",\"faculty\":{\"id\":1,\"name\":\"Electronics\"},\"course\":{\"id\":1,\"name\":\"first\"}}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Group successfully saved"));
    }

    @Test
    void updateGroup() throws Exception {
        this.mockMvc.perform(put("/api/v1/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"test123\",\"faculty\":{\"id\":1,\"name\":\"Electronics\"},\"course\":{\"id\":1,\"name\":\"first\"}}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Faculty successfully updated"));
    }

    @Test
    void deleteGroup() throws Exception {
        mockMvc.perform(delete("/api/v1/groups/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteGroupWhenTodoIsNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/groups/5"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void pageWithGroups() throws Exception {
        mockMvc.perform(get("/api/v1/groups/pages/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[" +
                        "{\"id\":1,\"name\":\"AAAA\",\"faculty\":{\"id\":1,\"name\":\"Electronics\"},\"course\":{\"id\":1,\"name\":\"first\"}}," +
                        "{\"id\":2,\"name\":\"BBBB\",\"faculty\":{\"id\":2,\"name\":\"Programming\"},\"course\":{\"id\":2,\"name\":\"second\"}}," +
                        "{\"id\":3,\"name\":\"CCCC\",\"faculty\":{\"id\":3,\"name\":\"Psychology\"},\"course\":{\"id\":3,\"name\":\"third\"}}]"));
    }
}
