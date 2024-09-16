package com.pmb.paymybuddy.service;

import com.pmb.paymybuddy.dto.TransferItem;
import com.pmb.paymybuddy.model.Transaction;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repository.ITransactionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TransactionService implements com.pmb.paymybuddy.service.interfaces.ITransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final ITransactionRepository transactionRepository;

    @Override
    public List<TransferItem> getTransactions(Integer userId) {
        var result = new ArrayList<TransferItem>();

        // Get sent transactions
        Optional<List<Transaction>> sent = this.transactionRepository.findBySenderId(userId);

        if (sent.isPresent()) {
            for (Transaction t : sent.get()){
                result.add(new TransferItem(t.getReceiver().getUsername(),
                                            t.getDescription(),
                                     t.getAmount() * -1 + " €",
                                            t.getDate()));
            }
        }

        // Get received transactions
        Optional<List<Transaction>> received = this.transactionRepository.findByReceiverId(userId);

        if (received.isPresent()){
            for (Transaction t : received.get()) {
                result.add(new TransferItem(t.getSender().getUsername(),
                                            t.getDescription(),
                                     t.getAmount() + " €",
                                            t.getDate()));
            }
        }

        // Sort elements
        result.sort(Collections.reverseOrder());

        return result;
    }

    public Transaction addTransaction(User sender, User receiver, String description, double amount) {
        Transaction result = new Transaction();

        result.setSender(sender);
        result.setReceiver(receiver);
        result.setDescription(description);
        result.setAmount(amount); // add 0.5% tax in V1 of the application
        result.setDate(Calendar.getInstance().getTime());

        transactionRepository.save(result);

        return result;
    }
}
