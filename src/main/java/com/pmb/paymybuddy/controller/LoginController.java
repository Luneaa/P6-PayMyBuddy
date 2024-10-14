package com.pmb.paymybuddy.controller;

import com.pmb.paymybuddy.service.interfaces.ILoginService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final ILoginService loginService;

    @GetMapping("/login")
    public String getLogin() {
        logger.info("Get login form page");
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password){
        var userOptional = loginService.login(email, password);

        if (userOptional.isEmpty()){
            return "redirect:/login";
        }

        return "redirect:/transfer";
    }
}
