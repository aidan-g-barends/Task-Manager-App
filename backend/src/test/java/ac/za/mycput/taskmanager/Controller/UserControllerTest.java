package ac.za.mycput.taskmanager.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create() throws Exception {

        String requestBody = """
                {
                    "name": "Test User",
                    "email": "test@email.com",
                    "password": "TestPass123!"
                }
                """;

        mockMvc.perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@email.com"));
    }

    @Test
    void read() throws Exception {
        String requestBody = """
            {
                "name": "Read Test User",
                "email": "readtest@example.com",
                "password": "TestPass123!"
            }
            """;

        String response = mockMvc.perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/api/user/read/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Read Test User"));
    }

    @Test
    void read_nonExistentUser() throws Exception {
        mockMvc.perform(get("/api/user/read/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Users with id: 99999 not found"));
    }

    @Test
    void updates() throws Exception {
        String createBody = """
            {
                "name": "Original Name",
                "email": "original@example.com",
                "password": "TestPass123!"
            }
            """;

        String response = mockMvc.perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        String updateBody = String.format("""
            {
                "id": %d,
                "name": "Updated Name",
                "email": "updated@example.com"
            }
            """, id);

        mockMvc.perform(put("/api/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.email").value("updated@example.com"));
    }

    @Test
    void deletes() throws Exception {
        String requestBody = """
            {
                "name": "Delete Me",
                "email": "deleteme@example.com",
                "password": "TestPass123!"
            }
            """;

        String response = mockMvc.perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(delete("/api/user/delete/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        mockMvc.perform(get("/api/user/read/" + id))
                .andExpect(status().isNotFound());
    }


    @Test
    void findByEmail() throws Exception {
        String createBody = """
            {
                "name": "Email Search User",
                "email": "findme@example.com",
                "password": "TestPass123!"
            }
            """;

        mockMvc.perform(post("/api/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createBody));

        mockMvc.perform(get("/api/user/email/findme@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Email Search User"));
    }

    @Test
    void findByName() throws Exception {
        String createBody = """
            {
                "name": "Search By Name",
                "email": "searchbyname@example.com",
                "password": "TestPass123!"
            }
            """;

        mockMvc.perform(post("/api/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createBody));

        mockMvc.perform(get("/api/user/names/Search By Name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Search By Name"));
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/api/user/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}