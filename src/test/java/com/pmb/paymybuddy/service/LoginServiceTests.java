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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTests {
    @Mock
    IUserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    LoginService loginService;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(loginService, "passwordEncoder", passwordEncoder);
    }

    @Test
    void loginDoesNotExist() {
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

        var result = this.loginService.login("email", "password");
        assertTrue(result.isEmpty());
    }

    @Test
    void loginWithIncorrectPassword() {
        User user = new User();
        user.setPassword("password");

        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(false);

        var result = this.loginService.login("email", "wordpass");

        assertTrue(result.isEmpty());
    }

    @Test
    void login() {
        User user = new User();
        user.setPassword("password");

        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(true);

        var result = this.loginService.login("email", "password");

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }
}
