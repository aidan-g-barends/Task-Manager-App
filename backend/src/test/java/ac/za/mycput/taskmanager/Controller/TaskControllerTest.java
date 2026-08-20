package ac.za.mycput.taskmanager.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Helper: creates a User and returns their generated id
    private Long createUser(String name, String email) throws Exception {
        String body = String.format("""
                {
                    "name": "%s",
                    "email": "%s",
                    "password": "TestPass123!",
                    "role": "USER"
                }
                """, name, email);

        String response = mockMvc.perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readTree(response).get("id").asLong();
    }

    // Helper: logs in and returns a real JWT
    private String loginAndGetToken(String email, String password) throws Exception {
        String body = String.format("""
                {
                    "email": "%s",
                    "password": "%s"
                }
                """, email, password);

        String response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readTree(response).get("token").asText();
    }

    @Test
    void create() throws Exception {
        Long userId = createUser("Task Owner", "owner@example.com");

        String taskBody = String.format("""
                {
                    "title": "Finish report",
                    "completed": false,
                    "dueDate": "2026-12-01",
                    "user": { "id": %d }
                }
                """, userId);

        mockMvc.perform(post("/api/task/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Finish report"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void create_emptyTitle() throws Exception {
        String taskBody = """
                {
                    "title": "",
                    "completed": false,
                    "dueDate": "2026-12-01"
                }
                """;

        mockMvc.perform(post("/api/task/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Task title cannot be null or empty"));
    }

    @Test
    void read() throws Exception {
        String taskBody = """
                {
                    "title": "Read Me Task",
                    "completed": false,
                    "dueDate": "2026-12-01"
                }
                """;

        String response = mockMvc.perform(post("/api/task/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskBody))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/api/task/read/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Read Me Task"));
    }

    @Test
    void read_nonExistancceTask() throws Exception {
        mockMvc.perform(get("/api/task/read/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Task with id: 99999 not found"));
    }

    @Test
    void updates() throws Exception {
        Long userId = createUser("Update Owner", "updateowner@example.com");
        String token = loginAndGetToken("updateowner@example.com", "TestPass123!");

        String createBody = String.format("""
                {
                    "title": "Original Title",
                    "completed": false,
                    "dueDate": "2026-12-01",
                    "user": { "id": %d }
                }
                """, userId);

        String response = mockMvc.perform(post("/api/task/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        String updateBody = String.format("""
                {
                    "id": %d,
                    "title": "Updated Title",
                    "completed": true,
                    "dueDate": "2026-12-15"
                }
                """, id);

        mockMvc.perform(put("/api/task/update")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void deletes() throws Exception {
        String createBody = """
                {
                    "title": "Delete This Task",
                    "completed": false,
                    "dueDate": "2026-12-01"
                }
                """;

        String response = mockMvc.perform(post("/api/task/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(delete("/api/task/delete/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        mockMvc.perform(get("/api/task/read/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByTitle() throws Exception {
        String createBody = """
                {
                    "title": "UniqueSearchTitle",
                    "completed": false,
                    "dueDate": "2026-12-01"
                }
                """;

        mockMvc.perform(post("/api/task/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createBody));

        mockMvc.perform(get("/api/task/title/UniqueSearchTitle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("UniqueSearchTitle"));
    }

    @Test
    void findByUser() throws Exception {
        Long userId = createUser("Task Assignee", "assignee@example.com");

        String taskBody = String.format("""
                {
                    "title": "Assigned Task",
                    "completed": false,
                    "dueDate": "2026-12-01",
                    "user": { "id": %d }
                }
                """, userId);

        mockMvc.perform(post("/api/task/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskBody));

        mockMvc.perform(get("/api/task/user/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Assigned Task"));
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/api/task/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}