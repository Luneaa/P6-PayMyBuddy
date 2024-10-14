package com.pmb.paymybuddy.controller;

import com.pmb.paymybuddy.service.interfaces.IUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserConnectionsControllerTests {
    @Mock
    private IUserService userService;

    @Mock
    private User user;

    @InjectMocks
    private UserConnectionsController userConnectionsController;

    @Test
    void getConnections() {
        assertEquals("userconnections", userConnectionsController.getConnections());
    }

    @Test
    void addUserConnectionWhileNotBeingLoggedIn() {
        lenient().when(userService.getUserByUsername(any(String.class))).thenReturn(Optional.empty());

        assertEquals("redirect:/login", userConnectionsController.addUserConnection("johndoe@gmail.com", user).getViewName());
    }

    @Test
    void addUserConnectionWhileAddingHimself() {
        com.pmb.paymybuddy.model.User currentUser = new com.pmb.paymybuddy.model.User();
        List<com.pmb.paymybuddy.model.User> relations = new ArrayList<>();
        currentUser.setUsername("johndoe");
        currentUser.setEmail("john.doe@gmail.com");
        currentUser.setConnections(relations);
        when(userService.getUserByUsername(null)).thenReturn(Optional.of(currentUser));

        assertEquals("redirect:/userconnections", userConnectionsController.addUserConnection("john.doe@gmail.com", user).getViewName());
    }

    @Test
    void addUserConnectionAlreadyAdded() {
        com.pmb.paymybuddy.model.User currentUser = new com.pmb.paymybuddy.model.User();
        var relation = new com.pmb.paymybuddy.model.User();
        relation.setEmail("friend@gmail.com");
        List<com.pmb.paymybuddy.model.User> relations = new ArrayList<>();
        relations.add(relation);
        currentUser.setUsername("johndoe");
        currentUser.setEmail("john.doe@gmail.com");
        currentUser.setConnections(relations);
        when(userService.getUserByUsername(null)).thenReturn(Optional.of(currentUser));

        assertEquals("redirect:/userconnections", userConnectionsController.addUserConnection("friend@gmail.com", user).getViewName());
    }

    @Test
    void addUserConnectionDoesNotExist() {
        com.pmb.paymybuddy.model.User currentUser = new com.pmb.paymybuddy.model.User();
        List<com.pmb.paymybuddy.model.User> relations = new ArrayList<>();
        currentUser.setUsername("johndoe");
        currentUser.setEmail("john.doe@gmail.com");
        currentUser.setConnections(relations);
        when(userService.getUserByUsername(null)).thenReturn(Optional.of(currentUser));
        when(userService.getUserByEmail(any(String.class))).thenReturn(Optional.empty());

        assertEquals("redirect:/userconnections", userConnectionsController.addUserConnection("friend@gmail.com", user).getViewName());
    }

    @Test
    void addUserConnection() {
        com.pmb.paymybuddy.model.User currentUser = new com.pmb.paymybuddy.model.User();
        var relation = new com.pmb.paymybuddy.model.User();
        relation.setEmail("friend@gmail.com");
        List<com.pmb.paymybuddy.model.User> relations = new ArrayList<>();
        currentUser.setUsername("johndoe");
        currentUser.setEmail("john.doe@gmail.com");
        currentUser.setConnections(relations);
        when(userService.getUserByUsername(null)).thenReturn(Optional.of(currentUser));
        when(userService.getUserByEmail(any(String.class))).thenReturn(Optional.of(relation));

        assertEquals("redirect:/userconnections", userConnectionsController.addUserConnection("friend@gmail.com", user).getViewName());
        verify(userService, times(1)).updateUserConnections(any(com.pmb.paymybuddy.model.User.class));
        assertEquals(1, currentUser.getConnections().size());
    }
}
