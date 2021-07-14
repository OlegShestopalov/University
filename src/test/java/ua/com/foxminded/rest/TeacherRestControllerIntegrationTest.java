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
public class TeacherRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAllTeachers() throws Exception {
        this.mockMvc.perform(get("/api/v1/teachers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[" +
                        "{\"id\":1,\"name\":\"Teacher1\",\"surname\":\"Teacher1\",\"email\":\"teacher1@gmail.com\"}," +
                        "{\"id\":2,\"name\":\"Teacher2\",\"surname\":\"Teacher2\",\"email\":\"teacher2@gmail.com\"}," +
                        "{\"id\":3,\"name\":\"Teacher3\",\"surname\":\"Teacher3\",\"email\":\"teacher3@gmail.com\"}]"));
    }

    @Test
    void findTeacherById() throws Exception {
        this.mockMvc.perform(get("/api/v1/teachers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("{\"id\":1,\"name\":\"Teacher1\",\"surname\":\"Teacher1\",\"email\":\"teacher1@gmail.com\"}"));
    }

    @Test
    void findTeacherByIdWhenTodoIsNotFound() throws Exception {
        this.mockMvc.perform(get("/api/v1/teachers/{id}", 5L))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findTeachersByName() throws Exception {
        this.mockMvc.perform(get("/api/v1/teachers/name?name=Teacher1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":1,\"name\":\"Teacher1\",\"surname\":\"Teacher1\",\"email\":\"teacher1@gmail.com\"}]"));
    }

    @Test
    void findTeachersByNameWhenTodoIsNotFound() throws Exception {
        this.mockMvc.perform(get("/api/v1/teachers/name?name=test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    void createNewTeacher() throws Exception {
        this.mockMvc.perform(post("/api/v1/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Teacher4\",\"surname\":\"Teacher4\",\"email\":\"teacher4@gmail.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Teacher successfully saved"));
    }

    @Test
    void updateTeacher() throws Exception {
        this.mockMvc.perform(put("/api/v1/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Teacher123\",\"surname\":\"Teacher123\",\"email\":\"teacher123@gmail.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Teacher successfully updated"));
    }

    @Test
    void deleteTeacher() throws Exception {
        mockMvc.perform(delete("/api/v1/teachers/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTeacherWhenTodoIsNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/teachers/5"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void pageWithTeachers() throws Exception {
        mockMvc.perform(get("/api/v1/teachers/pages/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[" +
                        "{\"id\":1,\"name\":\"Teacher1\",\"surname\":\"Teacher1\",\"email\":\"teacher1@gmail.com\"}," +
                        "{\"id\":2,\"name\":\"Teacher2\",\"surname\":\"Teacher2\",\"email\":\"teacher2@gmail.com\"}," +
                        "{\"id\":3,\"name\":\"Teacher3\",\"surname\":\"Teacher3\",\"email\":\"teacher3@gmail.com\"}]"));
    }
}
