package com.pmb.paymybuddy.service.interfaces;

import com.pmb.paymybuddy.dto.TransferItem;
import com.pmb.paymybuddy.model.Transaction;
import com.pmb.paymybuddy.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Manages transactions between users
 */
@Service
public interface ITransactionService {

    /**
     * Gets the given user's transactions
     *
     * @param userId User id of the user we want to get the transactions from
     * @return A list of all the user's transactions
     */
    List<TransferItem> getTransactions(Integer userId);

    /**
     * Adds a transaction corresponding to the given values
     *
     * @param sender User that sent the money
     * @param receiver User that received the money
     * @param description Description of the transaction
     * @param amount Amount of money sent
     * @return A transaction object describing the new transaction
     */
    Transaction addTransaction(User sender, User receiver, String description, double amount);
}
