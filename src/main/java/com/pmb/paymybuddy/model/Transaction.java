package com.pmb.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * Entity representing a transaction between two users
 */
@Data
@Entity
@Table(name = "transactions")
public class Transaction {

    /**
     * ID of the transaction
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    private Integer id;

    /**
     * User that sent the money
     */
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    /**
     * User that received the money
     */
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    /**
     * Date of the transaction
     */
    @Column(name = "date")
    private Date date;

    /**
     * Description of the transaction
     */
    @Column(name = "description")
    private String description;

    /**
     * Amount of money transferred
     */
    @Column(name = "amount")
    private double amount;
}
