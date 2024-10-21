package com.pmb.paymybuddy.repository;

import com.pmb.paymybuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Manages users entities
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user using their username
     *
     * @param username Username to search for
     * @return Optional of the user
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user using their email
     *
     * @param email Email to search for
     * @return Optional of the user
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if the given username exists
     *
     * @param username Username to check for
     * @return Boolean value indicating if the username is used or not
     */
    boolean existsByUsername(String username);

    /**
     * Checks if the given email exists
     *
     * @param email Email to check for
     * @return Boolean value indicating if the email is used or not
     */
    boolean existsByEmail(String email);
}
