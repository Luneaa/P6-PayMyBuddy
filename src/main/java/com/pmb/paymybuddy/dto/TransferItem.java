package com.pmb.paymybuddy.dto;

import java.util.Date;
import java.util.Objects;

public class TransferItem implements Comparable<TransferItem> {
    
    public final String username;

    public final String description;

    public final String amount;

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
