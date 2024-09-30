package com.pmb.paymybuddy.controller;

import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.service.interfaces.ILoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LoginController.class)
public class LoginControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ILoginService loginService;

    @Test
    void getLogin() throws Exception {
        mockMvc.perform(get("/login")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "john.doe@gmail.com", password = "Testtest1234!", roles = "USER")
    void postLogin() throws Exception {
        Optional<User> userOptional = Optional.of(new User());

        when(loginService.login(any(String.class), any(String.class))).thenReturn(userOptional);

        mockMvc.perform(post("/login").with(csrf())
                    .param("email", "john.doe@gmail.com")
                    .param("password", "Testtest1234!"))
                .andExpect(redirectedUrl("/transfer"));
    }
}
