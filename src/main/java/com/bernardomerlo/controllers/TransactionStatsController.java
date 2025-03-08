package com.bernardomerlo.controllers;

import com.bernardomerlo.entities.dtos.StatsDTO;
import com.bernardomerlo.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatistica")
public class TransactionStatsController {

    private final TransactionService transactionService;

    public TransactionStatsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<StatsDTO> getStats(){
        return ResponseEntity.ok(this.transactionService.getStats());
    }

}
