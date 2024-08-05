package com.pmb.paymybuddy.controller;

import com.pmb.paymybuddy.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final LoginService loginService;

    @GetMapping("/login")
    public String getLogin() {
        logger.info("Get login form page");
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model){
        var userOptional = loginService.login(email, password);

        if (userOptional.isEmpty()){
            model.addAttribute("error", "Email ou mot de passe incorrect");
            return "redirect:/login?error";
        }

        return "redirect:/transfer";
    }
}
