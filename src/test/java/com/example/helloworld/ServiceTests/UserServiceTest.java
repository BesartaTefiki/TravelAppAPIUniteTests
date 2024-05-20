package com.example.helloworld.ServiceTests;

import com.example.helloworld.pojo.User;
import com.example.helloworld.repository.UserRepository;
import com.example.helloworld.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterNewUserSuccess() {
        User newUser = new User();
        newUser.setUsername("testUser");
        newUser.setPassword("password");
        newUser.setEmail("test@example.com");

        when(userRepository.findByName("testUser")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User result = userService.registerNewUser("testUser", "password", "test@example.com");

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, times(1)).findByName("testUser");
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterNewUserUsernameExists() {
        User existingUser = new User();
        existingUser.setUsername("testUser");

        when(userRepository.findByName("testUser")).thenReturn(Optional.of(existingUser));
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userService.registerNewUser("testUser", "password", "test@example.com");
        });

        assertEquals("Username or Email already exists.", exception.getMessage());
        verify(userRepository, times(1)).findByName("testUser");
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testRegisterNewUserEmailExists() {
        User existingUser = new User();
        existingUser.setEmail("test@example.com");

        when(userRepository.findByName("testUser")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userService.registerNewUser("testUser", "password", "test@example.com");
        });

        assertEquals("Username or Email already exists.", exception.getMessage());
        verify(userRepository, times(1)).findByName("testUser");
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testFindAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(new User(), new User()));

        List<User> users = userService.findAllUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindAllUsersEmpty() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> users = userService.findAllUsers();

        assertNotNull(users);
        assertTrue(users.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findByName("testUser")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername("testUser");

        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getUsername());
        verify(userRepository, times(1)).findByName("testUser");
    }

    @Test
    void testFindByUsernameNotFound() {
        when(userRepository.findByName("testUser")).thenReturn(Optional.empty());

        Optional<User> result = userService.findByUsername("testUser");

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByName("testUser");
    }

    @Test
    void testFindByEmail() {
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testFindByEmailNotFound() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        Optional<User> result = userService.findByEmail("test@example.com");

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testUpdateByName() {
        User existingUser = new User();
        existingUser.setUsername("oldUser");
        existingUser.setEmail("old@example.com");

        User updatedUser = new User();
        updatedUser.setUsername("newUser");
        updatedUser.setEmail("new@example.com");

        when(userRepository.findByName("oldUser")).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        Optional<User> result = userService.updateByName("oldUser", updatedUser);

        assertTrue(result.isPresent());
        assertEquals("newUser", result.get().getUsername());
        assertEquals("new@example.com", result.get().getEmail());
        verify(userRepository, times(1)).findByName("oldUser");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateByNameNotFound() {
        User updatedUser = new User();
        updatedUser.setUsername("newUser");
        updatedUser.setEmail("new@example.com");

        when(userRepository.findByName("oldUser")).thenReturn(Optional.empty());

        Optional<User> result = userService.updateByName("oldUser", updatedUser);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByName("oldUser");
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testDeleteByName() {
        User user = new User();
        user.setUsername("deleteUser");

        when(userRepository.findByName("deleteUser")).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(any(User.class));

        Optional<Boolean> result = userService.deleteByName("deleteUser");

        assertTrue(result.isPresent());
        assertTrue(result.get());
        verify(userRepository, times(1)).findByName("deleteUser");
        verify(userRepository, times(1)).delete(any(User.class));
    }

    @Test
    void testDeleteByNameNotFound() {
        when(userRepository.findByName("deleteUser")).thenReturn(Optional.empty());

        Optional<Boolean> result = userService.deleteByName("deleteUser");

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByName("deleteUser");
        verify(userRepository, times(0)).delete(any(User.class));
    }
}
