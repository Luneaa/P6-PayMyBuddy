package com.pmb.paymybuddy.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Manages root redirection
 */
@Controller
public class RootRedirectController {

    /**
     * Redirects to the homepage
     *
     * @param httpServletResponse current http response
     */
    @GetMapping("/")
    public void redirectToTransfer(HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Location", "/transfer");
        httpServletResponse.setStatus(302);
    }
}
