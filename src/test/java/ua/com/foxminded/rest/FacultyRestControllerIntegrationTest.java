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
public class FacultyRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAllFaculties() throws Exception {
        this.mockMvc.perform(get("/api/v1/faculties"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":1,\"name\":\"Electronics\"},{\"id\":2,\"name\":\"Programming\"},{\"id\":3,\"name\":\"Psychology\"}]"));
    }

    @Test
    void findFacultyById() throws Exception {
        this.mockMvc.perform(get("/api/v1/faculties/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("{\"id\":1,\"name\":\"Electronics\"}"));
    }

    @Test
    void findFacultyByIdWhenTodoIsNotFound() throws Exception {
        this.mockMvc.perform(get("/api/v1/faculties/{id}", 5L))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findFacultiesByName() throws Exception {
        this.mockMvc.perform(get("/api/v1/faculties/name?name=Electronics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":1,\"name\":\"Electronics\"}]"));
    }

    @Test
    void findFacultiesByNameWhenTodoIsNotFound() throws Exception {
        this.mockMvc.perform(get("/api/v1/faculties/name?name=test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    void createNewFaculty() throws Exception {
        this.mockMvc.perform(post("/api/v1/faculties")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"test\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"id\":4,\"name\":\"test\"}"));
    }

    @Test
    void updateFaculty() throws Exception {
        this.mockMvc.perform(put("/api/v1/faculties")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"test123\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Faculty successfully updated"));
    }

    @Test
    void deleteFaculty() throws Exception {
        mockMvc.perform(delete("/api/v1/faculties/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFacultyWhenTodoIsNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/faculties/5"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void pageWithFaculties() throws Exception {
        mockMvc.perform(get("/api/v1/faculties/pages/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":1,\"name\":\"Electronics\"},{\"id\":2,\"name\":\"Programming\"},{\"id\":3,\"name\":\"Psychology\"}]"));
    }
}

