package com.pmb.paymybuddy.controller;

import com.pmb.paymybuddy.dto.TransferItem;
import com.pmb.paymybuddy.model.Transaction;
import com.pmb.paymybuddy.service.interfaces.ITransactionService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransferControllerTests {

    @Mock
    private IUserService userService;

    @Mock
    private ITransactionService transactionService;

    @Mock
    private Model model;

    @Mock
    private User user;

    @InjectMocks
    private TransferController transferController;  // Injecte le mock dans le contrôleur

    @Test
    void indexWithoutBeingLoggedIn() {
        lenient().when(userService.getUserByUsername(any(String.class))).thenReturn(Optional.empty());

        assertEquals("redirect:/login", transferController.index(model, user));
    }

    @Test
    void index() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        lenient().when(request.getSession(false)).thenReturn(session);

        com.pmb.paymybuddy.model.User currentUser = new com.pmb.paymybuddy.model.User();
        List<com.pmb.paymybuddy.model.User> relations = new ArrayList<>();
        currentUser.setUsername("johndoe");
        currentUser.setEmail("john.doe@gmail.com");
        currentUser.setConnections(relations);
        when(userService.getUserByUsername(null)).thenReturn(Optional.of(currentUser));

        List<TransferItem> transactions = new ArrayList<>();
        lenient().when(transactionService.getTransactions(any(Integer.class))).thenReturn(transactions);

        assertEquals("transfer", transferController.index(model, user));
        verify(model, times(1)).addAttribute("transactions", transactions);
        verify(model, times(1)).addAttribute("relations", relations);
    }

    @Test
    void postToNonExistingUser() {
        lenient().when(userService.getUserByUsername(any(String.class))).thenReturn(Optional.empty());

        assertEquals("redirect:/transfer", transferController.post("nonexisting", "fail", 1, user));
    }

    @Test
    void postWhileNotLoggedIn() {
        com.pmb.paymybuddy.model.User localUser = new com.pmb.paymybuddy.model.User();
        localUser.setUsername("localuser");
        lenient().when(userService.getUserByUsername(any(String.class))).thenReturn(Optional.of(localUser)).thenReturn(Optional.empty());

        assertEquals("redirect:/login", transferController.post("johndoe", "fail", 1, user));
    }

    @Test
    void post() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        lenient().when(request.getSession(false)).thenReturn(session);

        com.pmb.paymybuddy.model.User currentUser = new com.pmb.paymybuddy.model.User();
        List<com.pmb.paymybuddy.model.User> relations = new ArrayList<>();
        currentUser.setUsername("johndoe");
        currentUser.setEmail("john.doe@gmail.com");
        currentUser.setConnections(relations);
        when(userService.getUserByUsername(any(String.class)))
                .thenReturn(Optional.of(currentUser));

        when(userService.getUserByUsername(null))
                .thenReturn(Optional.of(currentUser));

        assertEquals("redirect:/transfer", transferController.post("johndoe", "success", 1, user));
        verify(transactionService, times(1)).addTransaction(any(com.pmb.paymybuddy.model.User.class), any(com.pmb.paymybuddy.model.User.class), any(String.class), any(double.class));
    }
}
