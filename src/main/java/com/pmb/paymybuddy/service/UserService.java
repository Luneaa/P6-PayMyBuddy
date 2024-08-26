package com.pmb.paymybuddy.service;

import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService implements com.pmb.paymybuddy.service.interfaces.IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final Pattern EMAIL_VALIDATION = Pattern.compile("^(.+)@(.+)$");
    private static final Pattern USERNAME_VALIDATION = Pattern.compile("^[a-zA-Z0-9_-]{4,26}$");
    private static final Pattern PASSWORD_VALIDATION = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,24}$");

    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void saveUser(User user){
        if (!isEmailValid(user.getEmail())){
            throw new IllegalArgumentException("Invalid email");
        }

        if (!isUsernameValid(user.getUsername())){
            throw new IllegalArgumentException("Invalid username");
        }

        if (!isPasswordValid(user.getPassword())){
            throw new IllegalArgumentException("Invalid password");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void updateUserConnections(User user){
        var userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isEmpty()){
            logger.error("User does not exist");
            return;
        }

        var userToUpdate = userOptional.get();
        userToUpdate.setConnections(user.getConnections());
        userRepository.save(userToUpdate);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username){
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    private boolean isEmailValid(String email){
        return email != null && !email.isBlank() && !email.isEmpty() && EMAIL_VALIDATION.matcher(email).matches();
    }

    private boolean isUsernameValid(String username) {
        return username != null && !username.isBlank() && !username.isEmpty() && USERNAME_VALIDATION.matcher(username).matches();
    }

    private boolean isPasswordValid(String password) {
        return password != null && !password.isBlank() && !password.isEmpty() && PASSWORD_VALIDATION.matcher(password).matches();
    }
}
