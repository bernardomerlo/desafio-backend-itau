package com.bernardomerlo.services;

import com.bernardomerlo.entities.Transaction;
import com.bernardomerlo.entities.TransactionStats;
import com.bernardomerlo.entities.dtos.StatsDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GetStatsTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionStats transactionStats;

    @Test
    public void testGetStatsWithEmptyList() {
        when(transactionStats.getLastTransactions(60)).thenReturn(new ArrayList<>());

        StatsDTO statsDTO = transactionService.getStats(60);

        assertEquals(0, statsDTO.getCount());
        assertEquals(0.0, statsDTO.getSum());
        assertEquals(0.0, statsDTO.getAvg());
        assertEquals(0.0, statsDTO.getMin());
        assertEquals(0.0, statsDTO.getMax());
    }

    @Test
    public void testGetStatsWithOneTransaction() {
        Transaction transaction = new Transaction(100.0, OffsetDateTime.now().minusDays(1));

        ArrayList<Transaction> arrayWithOneTransaction = new ArrayList<Transaction>();
        arrayWithOneTransaction.add(transaction);

        when(transactionStats.getLastTransactions(60)).thenReturn(arrayWithOneTransaction);

        StatsDTO statsDTO = transactionService.getStats(60);

        assertEquals(1, statsDTO.getCount());
        assertEquals(100.0, statsDTO.getSum());
        assertEquals(100.0, statsDTO.getAvg());
        assertEquals(100.0, statsDTO.getMin());
        assertEquals(100.0, statsDTO.getMax());
    }

    @Test
    public void testGetStatsWithMultiplePositiveTransactions(){
        Transaction transaction = new Transaction(1.0, OffsetDateTime.now().minusDays(1));
        Transaction transaction1 = new Transaction(2.0, OffsetDateTime.now().minusDays(1));
        Transaction transaction2 = new Transaction(3.0, OffsetDateTime.now().minusDays(1));
        Transaction transaction3 = new Transaction(4.0, OffsetDateTime.now().minusDays(1));
        Transaction transaction4 = new Transaction(5.0, OffsetDateTime.now().minusDays(1));
        Transaction transaction5 = new Transaction(6.0, OffsetDateTime.now().minusDays(1));

        ArrayList<Transaction> arrayWithMultipleTransactions = new ArrayList<>(Arrays.asList(transaction, transaction1, transaction2, transaction3, transaction4, transaction5));

        when(transactionStats.getLastTransactions(60)).thenReturn(arrayWithMultipleTransactions);

        StatsDTO statsDTO = transactionService.getStats(60);

        assertEquals(6, statsDTO.getCount());
        assertEquals(21.0, statsDTO.getSum());
        assertEquals(21.0 / 6, statsDTO.getAvg());
        assertEquals(1.0, statsDTO.getMin());
        assertEquals(6.0, statsDTO.getMax());
    }

}