package com.bernardomerlo.entities.dtos;

import com.bernardomerlo.entities.Transaction;

import java.time.OffsetDateTime;

public class TransactionDTO {

    private Double valor;
    private OffsetDateTime dataHora;

    public TransactionDTO(Double valor, OffsetDateTime dataHora) {
        this.valor = valor;
        this.dataHora = dataHora;
    }

    public Transaction toEntity(){
        return new Transaction(valor, dataHora);
    }
}
