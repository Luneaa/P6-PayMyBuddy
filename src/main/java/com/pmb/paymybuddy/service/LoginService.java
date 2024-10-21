package com.pmb.paymybuddy.service;

import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repository.IUserRepository;
import com.pmb.paymybuddy.service.interfaces.ILoginService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Manage login
 */
@Service
@RequiredArgsConstructor
public class LoginService implements ILoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Tries to log the user with the given credentials
     *
     * @param email email to use
     * @param password password to use
     * @return Optional of the user
     */
    @Override
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
