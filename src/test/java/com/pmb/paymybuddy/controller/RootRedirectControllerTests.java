package com.pmb.paymybuddy.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RootRedirectControllerTests {

    @Mock
    private HttpServletResponse httpServletResponse;


    @InjectMocks
    private RootRedirectController rootRedirectController;  // Injecte le mock dans le contrôleur

    @Test
    void rootRedirectToTransfer() {
        rootRedirectController.redirectToTransfer(httpServletResponse);

        verify(httpServletResponse, times(1)).setHeader("Location", "/transfer");
        verify(httpServletResponse, times(1)).setStatus(302);
    }
}
