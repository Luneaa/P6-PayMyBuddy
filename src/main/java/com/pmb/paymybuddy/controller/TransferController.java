package com.pmb.paymybuddy.controller;

import com.pmb.paymybuddy.service.interfaces.ITransactionService;
import com.pmb.paymybuddy.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

/**
 * Manages money transfers
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransferController {

    private static final Logger logger = LoggerFactory.getLogger(TransferController.class);

    private final ITransactionService transactionService;
    private final IUserService userService;

    /**
     * Gets the current transaction page
     *
     * @param model Model of the transaction page
     * @param user Current logged in user
     * @return Path to the transaction page
     */
    @GetMapping
    public String index(Model model, @AuthenticationPrincipal User user) {

        // get current user
        var currentUserOptional = userService.getUserByUsername(user.getUsername());
        if (currentUserOptional.isEmpty()){
            // user is not logged in
            logger.error("User was accessing transfer page without being logged in, redirecting to login page");
            return "redirect:/login";
        }

        // get user transactions
        var transactions = this.transactionService.getTransactions(currentUserOptional.get().getId());

        model.addAttribute("transactions", transactions);
        model.addAttribute("relations", currentUserOptional.get().getConnections());

        return "transfer";
    }

    /**
     * Tries to add a new transaction
     *
     * @param username User to send money to
     * @param description Description of the transaction
     * @param amount Amount of money to send
     * @param user Currently logged in user
     * @return Path to the transfer page
     */
    @PostMapping
    public String post(@RequestParam String username, @RequestParam String description, @RequestParam double amount, @AuthenticationPrincipal User user) {
        var receiver = userService.getUserByUsername(username);
        if (receiver.isEmpty()) {
            logger.error("Trying to send money to a non existing user");
            return "redirect:/transfer";
        }

        var currentUserOptional = userService.getUserByUsername(user.getUsername());
        if (currentUserOptional.isEmpty()){
            // user is not logged in
            logger.error("User was accessing transfer page without being logged in, redirecting to login page");
            return "redirect:/login";
        }

        this.transactionService.addTransaction(currentUserOptional.get(), receiver.get(), description, amount);

        return "redirect:/transfer";
    }
}
