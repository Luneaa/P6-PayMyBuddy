package com.pmb.paymybuddy.controller;

import com.pmb.paymybuddy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transfer")
public class TransferController {

    private static final Logger logger = LoggerFactory.getLogger(TransferController.class);

    private final UserService userService;

    public TransferController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("message", "Test");
        return "transfer";
    }
}
