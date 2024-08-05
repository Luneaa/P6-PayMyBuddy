package com.pmb.paymybuddy.service;

import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> login(String email, String password) {
        logger.info("Login attempt for user : {}", email);
        var userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()){
            // user does not exist
            logger.warn("User '{}' does not exist", email);
            return Optional.empty();
        }

        var user = userOptional.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            // password incorrect
            logger.warn("Incorrect password login attempt for user {}", email);
            return Optional.empty();
        }

        // valid login
        logger.info("User credentials valid for {}", email);
        return userOptional;
    }
}