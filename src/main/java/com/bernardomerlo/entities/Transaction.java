package com.bernardomerlo.entities;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;


public class Transaction {
    private Double value;
    private OffsetDateTime date;
    private LocalDateTime whenAdded;

    public Transaction(Double value, OffsetDateTime date) {
        this.value = value;
        this.date = date;
        this.whenAdded = LocalDateTime.now();
    }

    public Transaction(){
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public LocalDateTime getWhenAdded() {
        return whenAdded;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "value=" + value +
                ", date=" + date +
                ", whenAdded=" + whenAdded +
                '}';
    }
}
