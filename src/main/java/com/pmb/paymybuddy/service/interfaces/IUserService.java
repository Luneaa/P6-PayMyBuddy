package com.pmb.paymybuddy.service.interfaces;

import com.pmb.paymybuddy.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Manage users
 */
@Service
public interface IUserService {

    /**
     * Creates or updates the given user, while validating data
     *
     * @param user User to update or create
     */
    void saveUser(User user);

    /**
     * Updates the given user connections
     *
     * @param user User to update with populated connections
     */
    void updateUserConnections(User user);

    /**
     * Gets the user by the given username
     *
     * @param username Username to find
     * @return Optional of the user
     */
    Optional<User> getUserByUsername(String username);

    /**
     * Gets the user by the given email
     *
     * @param email Email to find
     * @return Optional of the user
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Checks that the given username exists
     *
     * @param username Username to check
     * @return Boolean value whether the user exists or not
     */
    boolean existsByUsername(String username);

    /**
     * Checks that the given email exists
     *
     * @param email Email to check
     * @return Boolean value whether the email exists or not
     */
    boolean existsByEmail(String email);
}
