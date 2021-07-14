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
public class StudentRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAllStudents() throws Exception {
        this.mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[" +
                        "{\"id\":1,\"group\":{\"id\":1,\"name\":\"AAAA\",\"faculty\":{\"id\":1,\"name\":\"Electronics\"},\"course\":{\"id\":1,\"name\":\"first\"}},\"name\":\"Student1\",\"surname\":\"Student1\",\"sex\":\"Male\",\"age\":20,\"email\":\"student1@gmail.com\"}," +
                        "{\"id\":2,\"group\":{\"id\":2,\"name\":\"BBBB\",\"faculty\":{\"id\":2,\"name\":\"Programming\"},\"course\":{\"id\":2,\"name\":\"second\"}},\"name\":\"Student2\",\"surname\":\"Student2\",\"sex\":\"Male\",\"age\":21,\"email\":\"student2@gmail.com\"}," +
                        "{\"id\":3,\"group\":{\"id\":3,\"name\":\"CCCC\",\"faculty\":{\"id\":3,\"name\":\"Psychology\"},\"course\":{\"id\":3,\"name\":\"third\"}},\"name\":\"Student3\",\"surname\":\"Student3\",\"sex\":\"Male\",\"age\":22,\"email\":\"student3@gmail.com\"}]"));
    }

    @Test
    void findStudentById() throws Exception {
        this.mockMvc.perform(get("/api/v1/students/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("{\"id\":1,\"group\":{\"id\":1,\"name\":\"AAAA\",\"faculty\":{\"id\":1,\"name\":\"Electronics\"},\"course\":{\"id\":1,\"name\":\"first\"}},\"name\":\"Student1\",\"surname\":\"Student1\",\"sex\":\"Male\",\"age\":20,\"email\":\"student1@gmail.com\"}"));
    }

    @Test
    void findStudentByIdWhenTodoIsNotFound() throws Exception {
        this.mockMvc.perform(get("/api/v1/students/{id}", 5L))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findStudentsByName() throws Exception {
        this.mockMvc.perform(get("/api/v1/students/name?name=Student1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":1,\"group\":{\"id\":1,\"name\":\"AAAA\",\"faculty\":{\"id\":1,\"name\":\"Electronics\"},\"course\":{\"id\":1,\"name\":\"first\"}},\"name\":\"Student1\",\"surname\":\"Student1\",\"sex\":\"Male\",\"age\":20,\"email\":\"student1@gmail.com\"}]"));
    }

    @Test
    void findStudentsByNameWhenTodoIsNotFound() throws Exception {
        this.mockMvc.perform(get("/api/v1/students/name?name=test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    void createNewStudent() throws Exception {
        this.mockMvc.perform(post("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"group\":{\"id\":1,\"name\":\"AAAA\",\"faculty\":{\"id\":1,\"name\":\"Electronics\"},\"course\":{\"id\":1,\"name\":\"first\"}},\"name\":\"Student4\",\"surname\":\"Student4\",\"sex\":\"Male\",\"age\":\"20\",\"email\":\"student4@gmail.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Student successfully saved"));
    }

    @Test
    void updateStudent() throws Exception {
        this.mockMvc.perform(put("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"group\":{\"id\":1,\"name\":\"AAAA\",\"faculty\":{\"id\":1,\"name\":\"Electronics\"},\"course\":{\"id\":1,\"name\":\"first\"}},\"name\":\"Student123\",\"surname\":\"Student123\",\"sex\":\"Male\",\"age\":\"20\",\"email\":\"student123@gmail.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Student successfully updated"));
    }

    @Test
    void deleteStudent() throws Exception {
        mockMvc.perform(delete("/api/v1/students/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteStudentWhenTodoIsNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/students/5"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void pageWithStudents() throws Exception {
        mockMvc.perform(get("/api/v1/students/pages/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[" +
                        "{\"id\":1,\"group\":{\"id\":1,\"name\":\"AAAA\",\"faculty\":{\"id\":1,\"name\":\"Electronics\"},\"course\":{\"id\":1,\"name\":\"first\"}},\"name\":\"Student1\",\"surname\":\"Student1\",\"sex\":\"Male\",\"age\":20,\"email\":\"student1@gmail.com\"}," +
                        "{\"id\":2,\"group\":{\"id\":2,\"name\":\"BBBB\",\"faculty\":{\"id\":2,\"name\":\"Programming\"},\"course\":{\"id\":2,\"name\":\"second\"}},\"name\":\"Student2\",\"surname\":\"Student2\",\"sex\":\"Male\",\"age\":21,\"email\":\"student2@gmail.com\"}," +
                        "{\"id\":3,\"group\":{\"id\":3,\"name\":\"CCCC\",\"faculty\":{\"id\":3,\"name\":\"Psychology\"},\"course\":{\"id\":3,\"name\":\"third\"}},\"name\":\"Student3\",\"surname\":\"Student3\",\"sex\":\"Male\",\"age\":22,\"email\":\"student3@gmail.com\"}]"));
    }
}
