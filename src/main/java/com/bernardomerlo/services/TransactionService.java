package com.bernardomerlo.services;

import com.bernardomerlo.entities.dtos.StatsDTO;
import com.bernardomerlo.entities.Transaction;
import com.bernardomerlo.entities.TransactionStats;
import com.bernardomerlo.exceptions.InvalidTimeException;
import com.bernardomerlo.exceptions.InvalidValueException;
import com.bernardomerlo.exceptions.NullValuesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;


@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionStats transactions;

    public TransactionService(TransactionStats transactions) {
        this.transactions = transactions;
    }

    public void validateTransaction(Transaction transaction) {
        logger.debug("Iniciando validação de transação");
        OffsetDateTime date = transaction.getDate();
        Double value = transaction.getValue();
        if (date == null || value == null) {
            logger.debug("Falha na validação: valores nulos detectados em {}", transaction);
            throw new NullValuesException("Valores tem que ser diferentes de null");
        }
        if (date.isAfter(OffsetDateTime.now())) {
            logger.debug("Falha na validação: data futura detectada: {}", date);
            throw new InvalidTimeException("Transacao nao pode ser no futuro");
        }
        if (value < 0) {
            logger.debug("Falha na validação: valor negativo detectado: {}", value);
            throw new InvalidValueException("Valores nao pode ser negativo");
        }
        logger.debug("Transação validada com sucesso: {}", transaction);
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public void delete(){
        transactions.delete();
    }

    public StatsDTO getStats(Integer seconds){
        long startTime = System.nanoTime();
        logger.debug("Iniciando cálculo de estatística");
        ArrayList<Transaction> transactions =  this.transactions.getLastTransactions(seconds);

        int count = 0;
        double sum = 0;
        double avg = 0;
        double max = 0;
        Double min = null;

        for(Transaction t : transactions){
            count++;
            sum += t.getValue();
            if(t.getValue() > max){
                max = t.getValue();
            }
            if(min == null || t.getValue() < min ){
                min = t.getValue();
            }
        }
        if(count > 0){
            avg = sum / count;
        }

        if(min == null){
            min = 0.0;
        }

        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        logger.debug("Terminando cálculo de estatística: {}", duration);
        return new StatsDTO(count, sum, avg, min, max);
    }
}
