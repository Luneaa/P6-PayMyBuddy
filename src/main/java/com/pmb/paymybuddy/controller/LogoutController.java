package com.pmb.paymybuddy.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LogoutController {
    private static final Logger logger = LoggerFactory.getLogger(LogoutController.class);

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        var session = request.getSession(false);
        if (session == null){
            // not currently logged in
            logger.info("Tried to log off while not logged in");
            return "redirect:/login";
        }

        logger.info("Session for user {} invalidated", session.getAttribute("username"));
        session.invalidate();

        return "redirect:/login";
    }
}
