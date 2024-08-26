package com.pmb.paymybuddy.service.interfaces;

import com.pmb.paymybuddy.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ILoginService {
    Optional<User> login(String email, String password);
}
