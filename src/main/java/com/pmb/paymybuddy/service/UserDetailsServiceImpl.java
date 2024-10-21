package com.pmb.paymybuddy.service;

import com.pmb.paymybuddy.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository repository;

    /**
     * Loads a user using the given email
     *
     * @param email Email used to find the user to load
     * @return User account for the given email
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userOptional = repository.findByEmail(email);

        if (userOptional.isEmpty()){
            throw new UsernameNotFoundException("Utilisateur inconnu");
        }

        var user = userOptional.get();

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("ROLE_USER")
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
