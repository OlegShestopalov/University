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
public class ScheduleItemRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAllScheduleItems() throws Exception {
        this.mockMvc.perform(get("/api/v1/scheduleItems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[" +
                        "{\"id\":1,\"lesson\":{\"id\":1,\"name\":\"first\"},\"subject\":{\"id\":1,\"name\":\"Subject1\",\"description\":\"Subject1\"},\"audience\":{\"id\":1,\"number\":1,\"desk\":50},\"day\":{\"id\":1,\"day\":\"2020-09-01\"}}," +
                        "{\"id\":2,\"lesson\":{\"id\":2,\"name\":\"second\"},\"subject\":{\"id\":2,\"name\":\"Subject2\",\"description\":\"Subject2\"},\"audience\":{\"id\":2,\"number\":2,\"desk\":40},\"day\":{\"id\":2,\"day\":\"2020-09-02\"}}," +
                        "{\"id\":3,\"lesson\":{\"id\":3,\"name\":\"third\"},\"subject\":{\"id\":3,\"name\":\"Subject3\",\"description\":\"Subject3\"},\"audience\":{\"id\":3,\"number\":3,\"desk\":30},\"day\":{\"id\":3,\"day\":\"2020-09-03\"}}]"));
    }

    @Test
    void findScheduleItemById() throws Exception {
        this.mockMvc.perform(get("/api/v1/scheduleItems/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("{\"id\":1,\"lesson\":{\"id\":1,\"name\":\"first\"},\"subject\":{\"id\":1,\"name\":\"Subject1\",\"description\":\"Subject1\"},\"audience\":{\"id\":1,\"number\":1,\"desk\":50},\"day\":{\"id\":1,\"day\":\"2020-09-01\"}}"));
    }

    @Test
    void findScheduleItemByIdWhenTodoIsNotFound() throws Exception {
        this.mockMvc.perform(get("/api/v1/scheduleItems/{id}", 5L))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewScheduleItem() throws Exception {
        this.mockMvc.perform(post("/api/v1/scheduleItems")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"lesson\":{\"id\":2,\"name\":\"second\"},\"subject\":{\"id\":1,\"name\":\"Subject1\",\"description\":\"Subject1\"},\"audience\":{\"id\":1,\"number\":1,\"desk\":50},\"day\":{\"id\":2,\"day\":\"2020-09-02\"}}"))
                .andExpect(status().isOk())
                .andExpect(content().string("ScheduleItem successfully saved"));
    }

    @Test
    void updateScheduleItem() throws Exception {
        this.mockMvc.perform(put("/api/v1/scheduleItems")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"lesson\":{\"id\":2,\"name\":\"second\"},\"subject\":{\"id\":1,\"name\":\"Subject1\",\"description\":\"Subject1\"},\"audience\":{\"id\":1,\"number\":1,\"desk\":50},\"day\":{\"id\":2,\"day\":\"2020-09-02\"}}"))
                .andExpect(status().isOk())
                .andExpect(content().string("ScheduleItem successfully updated"));
    }

    @Test
    void deleteScheduleItem() throws Exception {
        mockMvc.perform(delete("/api/v1/scheduleItems/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteScheduleItemWhenTodoIsNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/scheduleItems/5"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void pageWithScheduleItems() throws Exception {
        mockMvc.perform(get("/api/v1/scheduleItems/pages/1")
                .param("day", "2020-09-01"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[" +
                        "{\"id\":1,\"lesson\":{\"id\":1,\"name\":\"first\"},\"subject\":{\"id\":1,\"name\":\"Subject1\",\"description\":\"Subject1\"},\"audience\":{\"id\":1,\"number\":1,\"desk\":50},\"day\":{\"id\":1,\"day\":\"2020-09-01\"}}]"));
    }

    @Test
    void pageWithScheduleItemsByDay() throws Exception {
        mockMvc.perform(get("/api/v1/scheduleItems/pages/1")
                .param("day", "2020-09-01"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[" +
                        "{\"id\":1,\"lesson\":{\"id\":1,\"name\":\"first\"},\"subject\":{\"id\":1,\"name\":\"Subject1\",\"description\":\"Subject1\"},\"audience\":{\"id\":1,\"number\":1,\"desk\":50},\"day\":{\"id\":1,\"day\":\"2020-09-01\"}}]"));
    }

    @Test
    void pageWithScheduleItemsByGroupNameOrDay() throws Exception {
        mockMvc.perform(get("/api/v1/scheduleItems/pages/1")
                .param("name", "AAAA")
                .param("day", "2020-09-01"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[" +
                        "{\"id\":1,\"lesson\":{\"id\":1,\"name\":\"first\"},\"subject\":{\"id\":1,\"name\":\"Subject1\",\"description\":\"Subject1\"},\"audience\":{\"id\":1,\"number\":1,\"desk\":50},\"day\":{\"id\":1,\"day\":\"2020-09-01\"}}]"));
    }

    @Test
    void pageWithScheduleItemsByTeacherNameOrDay() throws Exception {
        mockMvc.perform(get("/api/v1/scheduleItems/pages/1")
                .param("name", "Teacher1")
                .param("day", "2020-09-01"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[" +
                        "{\"id\":1,\"lesson\":{\"id\":1,\"name\":\"first\"},\"subject\":{\"id\":1,\"name\":\"Subject1\",\"description\":\"Subject1\"},\"audience\":{\"id\":1,\"number\":1,\"desk\":50},\"day\":{\"id\":1,\"day\":\"2020-09-01\"}}]"));
    }
}
