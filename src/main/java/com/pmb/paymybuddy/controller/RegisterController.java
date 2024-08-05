package com.pmb.paymybuddy.controller;

import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    private final UserService userService;

    private static final String ERROR_REDIRECT = "redirect:/register?error";

    private static final String ERROR_ATTRIBUTE = "error";


    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        logger.info("Get register form");
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, Model model) {
        if (userService.existsByUsername(user.getUsername())){
            logger.error("Username {} already exists", user.getUsername());
            model.addAttribute(ERROR_ATTRIBUTE, "Nom d'utilisateur déjà utilisé");
            return ERROR_REDIRECT;
        }

        if (userService.existsByEmail(user.getEmail())){
            logger.error("Email {} already exists", user.getEmail());
            model.addAttribute(ERROR_ATTRIBUTE, "Email déjà utilisé");
            return ERROR_REDIRECT;
        }

        try {
            userService.saveUser(user);
            logger.info("User account created for {}", user.getUsername());
            return "redirect:/login";
        }
        catch (IllegalArgumentException e) {
            model.addAttribute(ERROR_ATTRIBUTE, e.getMessage());
            return ERROR_REDIRECT;
        }
    }
}
