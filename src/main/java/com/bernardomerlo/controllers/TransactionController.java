package com.bernardomerlo.controllers;

import com.bernardomerlo.entities.dtos.TransactionDTO;
import com.bernardomerlo.exceptions.InvalidTimeException;
import com.bernardomerlo.exceptions.InvalidValueException;
import com.bernardomerlo.exceptions.NullValuesException;
import com.bernardomerlo.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody TransactionDTO transactionDto) {
        try {
            this.transactionService.validateTransaction(transactionDto.toEntity());
            this.transactionService.addTransaction(transactionDto.toEntity());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (InvalidTimeException | InvalidValueException | NullValuesException exception ) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete() {
        try{
            this.transactionService.delete();
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
