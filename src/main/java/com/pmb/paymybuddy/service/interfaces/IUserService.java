package com.pmb.paymybuddy.service.interfaces;

import com.pmb.paymybuddy.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IUserService {
    void saveUser(User user);

    void updateUserConnections(User user);

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
