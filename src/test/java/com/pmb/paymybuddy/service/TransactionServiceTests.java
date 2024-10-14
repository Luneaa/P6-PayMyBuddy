package com.pmb.paymybuddy.service;

import com.pmb.paymybuddy.model.Transaction;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repository.ITransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTests {

    @Mock
    ITransactionRepository transactionRepository;

    @InjectMocks
    TransactionService transactionService;

    @Test
    void getTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        var sender = new User();
        sender.setUsername("senderUsername");

        var receiver = new User();
        receiver.setUsername("receiverUsername");

        var transaction = new Transaction();
        transaction.setId(0);
        transaction.setDate(Date.from(LocalDate.of(2024, 10, 11).atStartOfDay(ZoneId.of("Europe/Paris")).toInstant()));
        transaction.setAmount(10);
        transaction.setDescription("Ten bucks");
        transaction.setSender(sender);
        transaction.setReceiver(receiver);

        transactions.add(transaction);

        when(this.transactionRepository.findBySenderId(any(Integer.class))).thenReturn(Optional.of(transactions));
        when(this.transactionRepository.findByReceiverId(any(Integer.class))).thenReturn(Optional.of(transactions));

        var transactionResult = this.transactionService.getTransactions(0);

        assertEquals(2, transactionResult.size());
    }

    @Test
    void addTransaction() {
        var sender = new User();
        sender.setUsername("senderUsername");

        var receiver = new User();
        receiver.setUsername("receiverUsername");

        var result = this.transactionService.addTransaction(sender, receiver, "description", 10);

        verify(transactionRepository, times(1)).save(any(Transaction.class));
        assertEquals("description", result.getDescription());
        assertEquals(10, result.getAmount());
        assertEquals(sender, result.getSender());
        assertEquals(receiver, result.getReceiver());
    }
}
