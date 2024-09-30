package com.pmb.paymybuddy.controller;

import com.pmb.paymybuddy.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private final IUserService userService;

    private static final String ERROR_ATTRIBUTE = "error";

    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal User user, Model model) {
        logger.info("Get profile form page");

        // get current user
        var currentUserOptional = userService.getUserByUsername(user.getUsername());
        if (currentUserOptional.isEmpty()){
            // user is not logged in
            logger.error("User was accessing profile page without being logged in, redirecting to login page");
            return "redirect:/login";
        }

        model.addAttribute("username", currentUserOptional.get().getUsername());
        model.addAttribute("email", currentUserOptional.get().getEmail());

        return "profile";
    }

    @PostMapping("/profile")
    public String postProfile(@RequestParam String email, @RequestParam String password, @AuthenticationPrincipal User user, RedirectAttributes model) {
        var currentUserOptional = userService.getUserByUsername(user.getUsername());
        if (currentUserOptional.isEmpty()){
            // user is not logged in
            logger.error("User was accessing profile page without being logged in, redirecting to login page");
            return "redirect:/login";
        }

        if (userService.existsByEmail(email) && !email.equals(currentUserOptional.get().getEmail())){
            logger.error("Email {} already exists", email);
            model.addFlashAttribute(ERROR_ATTRIBUTE, "Email déjà utilisé");
            return "redirect:/profile";
        }

        var currentUser = currentUserOptional.get();

        currentUser.setEmail(email);
        currentUser.setPassword(password);

        try {
            this.userService.saveUser(currentUser);
            logger.info("User account modified for {}", user.getUsername());
        }
        catch (IllegalArgumentException e) {
            model.addFlashAttribute(ERROR_ATTRIBUTE, e.getMessage());

            return "redirect:/profile";
        }

        return "redirect:/profile";
    }
}
