package com.pmb.paymybuddy.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootRedirectController {

    @GetMapping("/")
    public void redirectToTransfer(HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Location", "/transfer");
        httpServletResponse.setStatus(302);
    }
}
