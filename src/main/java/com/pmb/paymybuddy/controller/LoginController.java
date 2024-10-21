package com.pmb.paymybuddy.controller;

import com.pmb.paymybuddy.service.interfaces.ILoginService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Manages login calls
 */
@Controller
@RequiredArgsConstructor
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final ILoginService loginService;

    /**
     * Gets the login page
     *
     * @return Path of the login page
     */
    @GetMapping("/login")
    public String getLogin() {
        logger.info("Get login form page");
        return "login";
    }

    /**
     * Login action
     *
     * @param email Email used to log in
     * @param password Password used to log in
     * @return Path to either the login page to retry or the home page if successfully logged in
     */
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password){
        var userOptional = loginService.login(email, password);

        if (userOptional.isEmpty()){
            return "redirect:/login";
        }

        return "redirect:/transfer";
    }
}
