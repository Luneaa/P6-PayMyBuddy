package com.pmb.paymybuddy.repository;

import com.pmb.paymybuddy.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Manages the transactions entities
 */
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Find transactions for the given sender
     *
     * @param senderId ID of the sender to check
     * @return Optional list of all transaction where the given user is the sender
     */
    Optional<List<Transaction>> findBySenderId(Integer senderId);

    /**
     * Find transactions for the given receiver
     *
     * @param receiverId ID of the receiver to check
     * @return Optional list of all transaction where the given user is the receiver
     */
    Optional<List<Transaction>> findByReceiverId(Integer receiverId);
}
