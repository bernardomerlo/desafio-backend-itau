package com.bernardomerlo.entities;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class TransactionStats {

    private final ArrayList<Transaction> transactions;

    public TransactionStats() {
        this.transactions = new ArrayList<>();
    }

    public TransactionStats(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void add(Transaction transaction) {
        transactions.add(transaction);
    }

    public void delete() {
        transactions.clear();
    }

    public ArrayList<Transaction> getLastTransactions(Integer seconds) {
        LocalDateTime last60Seconds = LocalDateTime.now().minusSeconds(seconds);
        ArrayList<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions) {
            if(t.getWhenAdded().isAfter(last60Seconds)){
                result.add(t);
            }
        }
        return result;
    }

}
