package com.pmb.paymybuddy.controller;

import com.pmb.paymybuddy.service.interfaces.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ProfileControllerTests {
    @Mock
    private IUserService userService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private User user;

    @InjectMocks
    private ProfileController profileController;  // Injecte le mock dans le contrôleur

    @Test
    void getProfileWhileNotLoggedIn() {
        lenient().when(userService.getUserByUsername(any(String.class))).thenReturn(Optional.empty());

        assertEquals("redirect:/login", profileController.getProfile(user, model));
    }

    @Test
    void getProfileWhileLoggedIn() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        lenient().when(request.getSession(false)).thenReturn(session);

        com.pmb.paymybuddy.model.User currentUser = new com.pmb.paymybuddy.model.User();
        currentUser.setUsername("johndoe");
        currentUser.setEmail("john.doe@gmail.com");
        when(userService.getUserByUsername(null)).thenReturn(Optional.of(currentUser));

        assertEquals("profile", profileController.getProfile(user, model));
        verify(model, times(1)).addAttribute("username", "johndoe");
        verify(model, times(1)).addAttribute("email", "john.doe@gmail.com");
    }

    @Test
    void postProfileWhileNotLoggedIn() {
        lenient().when(userService.getUserByUsername(any(String.class))).thenReturn(Optional.empty());

        assertEquals("redirect:/login", profileController.postProfile("johndoe", "Test1234!", user, redirectAttributes));
    }

    @Test
    void postProfileWithAlreadyExistingEmail() {
        // Mock valid session
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        lenient().when(request.getSession(false)).thenReturn(session);

        // Mock current user
        com.pmb.paymybuddy.model.User currentUser = new com.pmb.paymybuddy.model.User();
        currentUser.setUsername("johndoe");
        currentUser.setEmail("john.doe@gmail.com");
        when(userService.getUserByUsername(null)).thenReturn(Optional.of(currentUser));

        // Mock email as duplicate
        when(userService.existsByEmail(any(String.class))).thenReturn(true);

        assertEquals("redirect:/profile", profileController.postProfile("newemail@gmail.com", "Test1234!", user, redirectAttributes));
    }

    @Test
    void postProfile() {
        // Mock valid session
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        lenient().when(request.getSession(false)).thenReturn(session);

        // Mock current user
        com.pmb.paymybuddy.model.User currentUser = new com.pmb.paymybuddy.model.User();
        currentUser.setUsername("johndoe");
        currentUser.setEmail("john.doe@gmail.com");
        when(userService.getUserByUsername(null)).thenReturn(Optional.of(currentUser));

        // Mock email as not duplicate
        when(userService.existsByEmail(any(String.class))).thenReturn(false);

        assertEquals("redirect:/profile", profileController.postProfile("newemail@gmail.com", "Test1234!", user, redirectAttributes));
        verify(userService, times(1)).saveUser(any(com.pmb.paymybuddy.model.User.class));
    }
}
