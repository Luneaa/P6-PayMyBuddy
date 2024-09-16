package com.pmb.paymybuddy.service.interfaces;

import com.pmb.paymybuddy.dto.TransferItem;
import com.pmb.paymybuddy.model.Transaction;
import com.pmb.paymybuddy.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITransactionService {
    List<TransferItem> getTransactions(Integer userId);

    Transaction addTransaction(User sender, User receiver, String description, double amount);
}
