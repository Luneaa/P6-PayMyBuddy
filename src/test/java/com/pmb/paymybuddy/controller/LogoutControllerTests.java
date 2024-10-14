package com.pmb.paymybuddy.controller;

import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.service.interfaces.ILoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogoutControllerTests {

    @InjectMocks
    private LogoutController logoutController;  // Injecte le mock dans le contrôleur

    @Test
    void logoutWhileNotLoggedIn() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertEquals("redirect:/login", logoutController.logout(request));
    }

    @Test
    void logoutWhileLoggedIn() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        lenient().when(request.getSession(false)).thenReturn(session);
        assertEquals("redirect:/login", logoutController.logout(request));
    }
}
