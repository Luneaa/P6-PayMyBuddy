package com.pmb.paymybuddy.service.interfaces;

import com.pmb.paymybuddy.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Manage login
 */
@Service
public interface ILoginService {

    /**
     * Tries to log the user with the given credentials
     *
     * @param email email to use
     * @param password password to use
     * @return Optional of the user
     */
    Optional<User> login(String email, String password);
}
