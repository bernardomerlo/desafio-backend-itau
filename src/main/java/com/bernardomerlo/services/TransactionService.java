package com.bernardomerlo.services;

import com.bernardomerlo.entities.dtos.StatsDTO;
import com.bernardomerlo.entities.Transaction;
import com.bernardomerlo.entities.TransactionStats;
import com.bernardomerlo.exceptions.InvalidTimeException;
import com.bernardomerlo.exceptions.InvalidValueException;
import com.bernardomerlo.exceptions.NullValuesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;

@Service
public class TransactionService {

    @Autowired
    private final TransactionStats transactions;

    public TransactionService(TransactionStats transactions) {
        this.transactions = transactions;
    }

    public void validateTransaction(Transaction transaction) {
        OffsetDateTime date = transaction.getDate();
        Double value = transaction.getValue();
        if (date == null || value == null) {
            throw new NullValuesException("Valores tem que ser diferentes de null");
        }
        if (date.isAfter(OffsetDateTime.now())) {
            throw new InvalidTimeException("Transacao nao pode ser no futuro");
        }
        if (value < 0) {
            throw new InvalidValueException("Valores nao pode ser negativo");
        }
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public void delete(){
        transactions.delete();
    }

    public StatsDTO getStats(){
        ArrayList<Transaction> transactions =  this.transactions.getLastTransactions();

        int count = 0;
        double sum = 0;
        double avg = 0;
        double max = 0;
        double min = 0;

        for(Transaction t : transactions){
            count++;
            sum += t.getValue();
            if(t.getValue() > max){
                max = t.getValue();
            }
            if(t.getValue() < min){
                min = t.getValue();
            }
        }
        if(count > 0){
            avg = sum / count;
        }
        return new StatsDTO(count, sum, avg, min, max);
    }
}
