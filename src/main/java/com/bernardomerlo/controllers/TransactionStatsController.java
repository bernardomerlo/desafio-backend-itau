package com.bernardomerlo.controllers;

import com.bernardomerlo.entities.dtos.StatsDTO;
import com.bernardomerlo.exceptions.InvalidTimeRangeException;
import com.bernardomerlo.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatistica")
public class TransactionStatsController {

    private final TransactionService transactionService;

    public TransactionStatsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<StatsDTO> getStats(@RequestParam(required = false, defaultValue = "60") Integer seconds){
        if(seconds <= 0){
            throw new InvalidTimeRangeException("A faixa de tempo deve ser positiva e inteira.");
        }
        return ResponseEntity.ok(this.transactionService.getStats(seconds));
    }

}
