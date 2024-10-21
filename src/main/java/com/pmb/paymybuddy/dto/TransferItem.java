package com.pmb.paymybuddy.dto;

import java.util.Date;
import java.util.Objects;

/**
 * Represents a money transfer to another user
 */
public class TransferItem implements Comparable<TransferItem> {

    /**
     * Username of the user
     */
    public final String username;

    /**
     * Description of the money transfer
     */
    public final String description;

    /**
     * Amount of money transferred
     */
    public final String amount;

    /**
     * Date when the transfer occurred
     */
    public final Date date;

    public TransferItem(String username, String description, String amount, Date date) {
        this.username = username;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public int compareTo(TransferItem o) {
        return this.date.compareTo(o.date);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, description, amount, date);
    }
}
