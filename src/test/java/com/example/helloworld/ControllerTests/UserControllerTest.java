package com.example.helloworld.ControllerTests;

import com.example.helloworld.controller.UserController;
import com.example.helloworld.pojo.User;
import com.example.helloworld.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void registerUserSuccess() throws Exception {
        User user = new User();
        user.setUsername("user1");
        user.setPassword("password");
        user.setEmail("user1@example.com");

        when(userService.registerNewUser(anyString(), anyString(), anyString())).thenReturn(user);

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("user1"))
                .andExpect(jsonPath("$.email").value("user1@example.com"));

        verify(userService, times(1)).registerNewUser(anyString(), anyString(), anyString());
    }

    @Test
    public void registerUserBadRequest() throws Exception {
        User user = new User();
        user.setUsername("user1");
        user.setPassword("password");
        user.setEmail("user1@example.com");

        when(userService.registerNewUser(anyString(), anyString(), anyString())).thenThrow(new IllegalStateException("User already exists"));

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User already exists"));

        verify(userService, times(1)).registerNewUser(anyString(), anyString(), anyString());
    }

    @Test
    public void findAllUsersSuccess() throws Exception {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");

        List<User> users = List.of(user1, user2);

        when(userService.findAllUsers()).thenReturn(users);

        mockMvc.perform(get("/user/findAllUsers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[0].email").value("user1@example.com"))
                .andExpect(jsonPath("$[1].username").value("user2"))
                .andExpect(jsonPath("$[1].email").value("user2@example.com"));

        verify(userService, times(1)).findAllUsers();
    }

    @Test
    public void findAllUsersNotFound() throws Exception {
        when(userService.findAllUsers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/user/findAllUsers"))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).findAllUsers();
    }

    @Test
    public void findUserSuccess() throws Exception {
        User user = new User();
        user.setUsername("user1");
        user.setEmail("user1@example.com");

        when(userService.findByUsername("user1")).thenReturn(Optional.of(user));

        mockMvc.perform(post("/user/findUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Collections.singletonMap("username", "user1"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("user1"))
                .andExpect(jsonPath("$.email").value("user1@example.com"));

        verify(userService, times(1)).findByUsername("user1");
    }

    @Test
    public void findUserNotFound() throws Exception {
        when(userService.findByUsername("user1")).thenReturn(Optional.empty());

        mockMvc.perform(post("/user/findUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Collections.singletonMap("username", "user1"))))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).findByUsername("user1");
    }

    @Test
    public void updateUserSuccess() throws Exception {
        User user = new User();
        user.setUsername("user1");
        user.setEmail("user1@example.com");

        String userData = new JSONObject()
                .put("username", "user1")
                .put("email", "user1@example.com")
                .toString();

        when(userService.updateByName(eq("user1"), any(User.class))).thenReturn(Optional.of(user));

        mockMvc.perform(post("/user/updateUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userData))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("user1"))
                .andExpect(jsonPath("$.email").value("user1@example.com"));

        verify(userService, times(1)).updateByName(eq("user1"), any(User.class));
    }

    @Test
    public void updateUserNotFound() throws Exception {
        String userData = new JSONObject()
                .put("username", "user1")
                .put("email", "user1@example.com")
                .toString();

        when(userService.updateByName(eq("user1"), any(User.class))).thenReturn(Optional.empty());

        mockMvc.perform(post("/user/updateUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userData))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));

        verify(userService, times(1)).updateByName(eq("user1"), any(User.class));
    }

    @Test
    public void loginSuccess() throws Exception {
        User user = new User();
        user.setEmail("user1@example.com");
        user.setPassword("password");

        when(userService.findByEmail("user1@example.com")).thenReturn(Optional.of(user));

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("email", "user1@example.com", "password", "password"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("user1@example.com"))
                .andExpect(jsonPath("$.password").value("password"));

        verify(userService, times(1)).findByEmail("user1@example.com");
    }

    @Test
    public void loginUnauthorized() throws Exception {
        User user = new User();
        user.setEmail("user1@example.com");
        user.setPassword("password");

        when(userService.findByEmail("user1@example.com")).thenReturn(Optional.of(user));

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("email", "user1@example.com", "password", "wrongpassword"))))
                .andExpect(status().isUnauthorized());

        verify(userService, times(1)).findByEmail("user1@example.com");
    }

    @Test
    public void loginUserNotFound() throws Exception {
        when(userService.findByEmail("user1@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("email", "user1@example.com", "password", "password"))))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).findByEmail("user1@example.com");
    }

    @Test
    public void deleteUserSuccess() throws Exception {
        String userData = new JSONObject().put("name", "user1").toString();
        when(userService.deleteByName("user1")).thenReturn(Optional.of(true));

        mockMvc.perform(delete("/user/deleteUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userData))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(userService, times(1)).deleteByName("user1");
    }

    @Test
    public void deleteUserNotFound() throws Exception {
        String userData = new JSONObject().put("name", "user1").toString();
        when(userService.deleteByName("user1")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/user/deleteUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userData))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("User not found"));

        verify(userService, times(1)).deleteByName("user1");
    }
}
