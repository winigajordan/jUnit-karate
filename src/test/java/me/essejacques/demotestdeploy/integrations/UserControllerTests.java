package me.essejacques.demotestdeploy.integrations;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void findAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect( status().isOk())
                .andExpect( jsonPath( "$.length()").value(3))
                .andExpect(
                        jsonPath( "$.[0].name").value("Jordan")
                );

    }

    @Test
    @Order(2)
    public  void createUser() throws Exception {
        mockMvc.perform(
                post("/users"  )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"REMA\"," +
                                "\"email\":\"rema@test.com\"," +
                                "\"adresse\":\"Fass\"," +
                                "\"password\":\"12345\"" +
                                "}")
        )
        .andExpect( status().isOk())
        .andExpect( jsonPath( "$.name").value("REMA"));
    }

    @Test
    @Order(3)
    public void getUserById() throws Exception {
        mockMvc.perform(get("/users/{id}", 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.adresse").isString())
                .andExpect(jsonPath("$.password").isString());
    }

    @Test
    @Order(4)
    public void updateUser() throws Exception {
        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\":1," +
                                "\"name\":\"JordanUpdated\"," +
                                "\"adresse\":\"jord-update@gmail.com\"," +
                                "\"password\":\"password1234\"" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("JordanUpdated"))
                .andExpect(jsonPath("$.adresse").value("jord-update@gmail.com"))
                .andExpect(jsonPath("$.password").value("password1234"));
    }

    @Test
    @Order(5)
    public void deleteUser() throws Exception {
        mockMvc.perform(delete("/users/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Suppression OK"));
    }

}
