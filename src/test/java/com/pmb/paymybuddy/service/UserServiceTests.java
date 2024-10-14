package com.pmb.paymybuddy.service;

import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    IUserRepository userRepository;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(userService, "passwordEncoder", passwordEncoder);
    }

    @Test
    void saveUserInvalidEmail() {
        User user = new User();
        user.setEmail("invalidEmail");

        var exception = assertThrows(IllegalArgumentException.class, () -> this.userService.saveUser(user));
        assertEquals("Invalid email", exception.getMessage());
    }

    @Test
    void saveUserInvalidUsername() {
        User user = new User();
        user.setEmail("johndoe@gmail.com");
        user.setUsername("a");

        var exception = assertThrows(IllegalArgumentException.class, () -> this.userService.saveUser(user));
        assertEquals("Invalid username", exception.getMessage());
    }

    @Test
    void saveUserInvalidPassword() {
        User user = new User();
        user.setEmail("johndoe@gmail.com");
        user.setUsername("johndoe");
        user.setPassword("a");

        var exception = assertThrows(IllegalArgumentException.class, () -> this.userService.saveUser(user));
        assertEquals("Invalid password", exception.getMessage());
    }

    @Test
    void saveUser() {
        User user = new User();
        user.setEmail("johndoe@gmail.com");
        user.setUsername("johndoe");
        user.setPassword("Test1234!");

        this.userService.saveUser(user);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUserConnectionsDoesNotExist() {
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

        User user = new User();
        user.setEmail("johndoe@gmail.com");
        user.setUsername("johndoe");
        user.setPassword("Test1234!");

        this.userService.updateUserConnections(user);

        verify(userRepository, times(1)).findByEmail(any(String.class));
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void updateUserConnections() {
        User user = new User();
        user.setEmail("johndoe@gmail.com");
        user.setUsername("johndoe");
        user.setPassword("Test1234!");

        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        this.userService.updateUserConnections(user);

        verify(userRepository, times(1)).findByEmail(any(String.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getUserByUsername() {
        this.userService.getUserByUsername("john");

        verify(userRepository, times(1)).findByUsername("john");
    }

    @Test
    void getUserByEmail() {
        this.userService.getUserByEmail("john@gmail.com");

        verify(userRepository, times(1)).findByEmail("john@gmail.com");
    }

    @Test
    void existsByUsername() {
        this.userService.existsByUsername("john");

        verify(userRepository, times(1)).existsByUsername("john");
    }

    @Test
    void existsByEmail() {
        this.userService.existsByEmail("john@gmail.com");

        verify(userRepository, times(1)).existsByEmail("john@gmail.com");
    }
}
