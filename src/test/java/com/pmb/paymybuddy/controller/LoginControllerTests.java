package com.pmb.paymybuddy.controller;

import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.service.interfaces.ILoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTests {
    @Mock
    private ILoginService loginService;

    @InjectMocks
    private LoginController loginController;  // Injecte le mock dans le contrôleur

    @BeforeEach
    void setup(){
        Optional<User> userOptional = Optional.of(new User());

        when(loginService.login("john.doe@gmail.com", "Testtest1234!")).thenReturn(userOptional);
    }

    @Test
    void postLogin() {
       assertEquals("redirect:/transfer", loginController.login("john.doe@gmail.com", "Testtest1234!"));
       assertEquals("redirect:/login", loginController.login("john.doe@gmail.com", "Testtest134!"));
    }
}
