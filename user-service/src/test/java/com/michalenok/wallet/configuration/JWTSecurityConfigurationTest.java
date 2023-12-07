
package com.michalenok.wallet.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michalenok.wallet.model.dto.request.UserCreateDto;
import com.michalenok.wallet.service.api.UserService;
import com.michalenok.wallet.web.controller.UserController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static java.util.UUID.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {SecurityConfiguration.class, UserController.class})
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JWTSecurityConfigurationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService service;
    private UserCreateDto user;
    private UUID uuid;

    @BeforeAll
    public void init() {
        uuid = randomUUID();
        user = UserCreateDto.builder()
                .mail("dfghj")
                .mobilePhone("1234567")
                .password("12345678")
                .build();
    }

    @Test
    public void givenRequestNotAuthorized_whenGetAllUsers_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .header("Is-Proxy-Request", true))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void givenRequestAuthorized_whenGetUsers_thenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .header("Is-Proxy-Request", true))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenRequestAuthorizedAsUser_whenGetUsers_thenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .header("Is-Proxy-Request", true))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenRequestAuthorizedAsAdmin_whenGetUsers_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .header("Is-Proxy-Request", true))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void givenRequestAuthorizedAsUserAndAdmin_whenGetUsers_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .header("Is-Proxy-Request", true))
                .andExpect(status().isOk());
    }

    @Test
    public void givenRequestNotAuthorized_whenCreateUser_thenUnauthorized() throws Exception {

        mockMvc.perform(post("/api/v1/users")
                        .header("Is-Proxy-Request", true)
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenRequestAuthorizedAsAdmin_whenCreateUser_thenCreated() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .header("Is-Proxy-Request", true)
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenRequestAuthorizedAsUser_whenCreateUser_thenForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .header("Is-Proxy-Request", true)
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void givenRequestAuthorizedAsUserAndAdmin_whenCreateUser_thenCreated() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .header("Is-Proxy-Request", true)
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenRequestNotAuthorized_whenGetUser_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/users/{uuid}", uuid)
                        .header("Is-Proxy-Request", true))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void givenRequestAuthorizedAsUser_whenGetUser_thenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/users/{uuid}", uuid)
                        .header("Is-Proxy-Request", true))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenRequestAuthorizedAsAdmin_whenGetUser_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/users/{uuid}", uuid)
                        .header("Is-Proxy-Request", true))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void givenRequestAuthorizedAsUserAndAdmin_whenGetUser_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/users/{uuid}", uuid)
                        .header("Is-Proxy-Request", true))
                .andExpect(status().isOk());
    }

    @Test
    public void givenRequestNotAuthorized_whenUpdateUser_thenUnauthorized() throws Exception {
        mockMvc.perform(put("/api/v1/users/{uuid}",uuid)
                        .header("Is-Proxy-Request", true)
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenRequestAuthorizedAsAdmin_whenCreateUpdate_thenCreated() throws Exception {
        mockMvc.perform(put("/api/v1/users/{uuid}",uuid)
                        .header("Is-Proxy-Request", true)
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenRequestAuthorizedAsUser_whenCreateUpdate_thenForbidden() throws Exception {
        mockMvc.perform(put("/api/v1/users/{uuid}",uuid)
                        .header("Is-Proxy-Request", true)
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void givenRequestAuthorizedAsUserAndAdmin_whenUpdateUser_thenCreated() throws Exception {
        mockMvc.perform(put("/api/v1/users/{uuid}",uuid)
                        .header("Is-Proxy-Request", true)
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}