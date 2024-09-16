package com.pmb.paymybuddy.repository;

import com.pmb.paymybuddy.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<List<Transaction>> findBySenderId(Integer senderId);

    Optional<List<Transaction>> findByReceiverId(Integer senderId);
}
