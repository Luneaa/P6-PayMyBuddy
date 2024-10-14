package com.pmb.paymybuddy.service;

import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTests {
    @Mock
    IUserRepository userRepository;

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsernameNotFound() {
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("john.doe@gmail.com"));
    }

    @Test
    void loadUserByUsername() {
        User user = new User();
        user.setUsername("johndoe");
        user.setPassword("Test1234!");

        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        var result = userDetailsService.loadUserByUsername("johndoe");

        assertEquals("johndoe", result.getUsername());
        assertEquals("Test1234!", result.getPassword());
        assertTrue(result.isAccountNonExpired());
        assertTrue(result.isAccountNonLocked());
        assertTrue(result.isCredentialsNonExpired());
        assertTrue(result.isEnabled());
    }
}
