package com.pmb.paymybuddy.controller;

import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    private final IUserService userService;

    private static final String ERROR_REDIRECT = "redirect:/register";

    private static final String ERROR_ATTRIBUTE = "error";


    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        logger.info("Get register form");
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute("user") User user) {
        if (userService.existsByUsername(user.getUsername())){
            logger.error("Username {} already exists", user.getUsername());
            var modelAndView = new ModelAndView(ERROR_REDIRECT);
            modelAndView.addObject(ERROR_ATTRIBUTE, "Nom d'utilisateur déjà utilisé");
            return modelAndView;
        }

        if (userService.existsByEmail(user.getEmail())){
            logger.error("Email {} already exists", user.getEmail());
            var modelAndView = new ModelAndView(ERROR_REDIRECT);
            modelAndView.addObject(ERROR_ATTRIBUTE, "Email déjà utilisé");
            return modelAndView;
        }

        try {
            userService.saveUser(user);
            logger.info("User account created for {}", user.getUsername());
            return new ModelAndView("redirect:/login");
        }
        catch (IllegalArgumentException e) {
            var modelAndView = new ModelAndView(ERROR_REDIRECT);
            modelAndView.addObject(ERROR_ATTRIBUTE, e.getMessage());
            return modelAndView;
        }
    }
}
