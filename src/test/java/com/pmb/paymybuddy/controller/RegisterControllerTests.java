package com.pmb.paymybuddy.controller;

import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.service.interfaces.IUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterControllerTests {
    @Mock
    private IUserService userService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private RegisterController registerController;  // Injecte le mock dans le contrôleur

    @Test
    void getRegisterPage() {
        assertEquals("register", registerController.getRegisterPage(model));
        verify(model, times(1)).addAttribute(eq("user"), any(User.class));
    }

    @Test
    void registerWithAlreadyExistingUsername() {
        // Mock already existing username
        when(userService.existsByUsername(any(String.class))).thenReturn(true);

        User user = new User();
        user.setUsername("johndoe");

        assertEquals("redirect:/register", registerController.register(user, redirectAttributes));
    }

    @Test
    void registerWithAlreadyExistingEmail() {
        // Mock already not existing username
        when(userService.existsByUsername(any(String.class))).thenReturn(false);

        // Mock already existing email
        when(userService.existsByEmail(any(String.class))).thenReturn(true);

        User user = new User();
        user.setUsername("johndoe");
        user.setEmail("johndoe@gmail.com");

        assertEquals("redirect:/register", registerController.register(user, redirectAttributes));
    }

    @Test
    void register() {
        // Mock already not existing username
        when(userService.existsByUsername(any(String.class))).thenReturn(false);

        // Mock already not existing email
        when(userService.existsByEmail(any(String.class))).thenReturn(false);

        User user = new User();
        user.setUsername("johndoe");
        user.setEmail("johndoe@gmail.com");

        assertEquals("redirect:/login", registerController.register(user, redirectAttributes));
        verify(userService, times(1)).saveUser(any(User.class));
    }

    @Test
    void registerWithIllegalArgumentException() {
        // Mock already not existing username
        when(userService.existsByUsername(any(String.class))).thenReturn(false);

        // Mock already not existing email
        when(userService.existsByEmail(any(String.class))).thenReturn(false);

        User user = new User();
        user.setUsername("johndoe");
        user.setEmail("johndoe@gmail.com");

        doThrow(new IllegalArgumentException()).when(userService).saveUser(any(User.class));
        assertEquals("redirect:/register", registerController.register(user, redirectAttributes));
    }
}
