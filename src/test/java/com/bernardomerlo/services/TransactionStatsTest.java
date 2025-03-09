package com.bernardomerlo.services;

import com.bernardomerlo.entities.Transaction;
import com.bernardomerlo.entities.TransactionStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TransactionStatsTest {

    private TransactionStats transactionStats;
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        transactionStats = new TransactionStats();
    }

    @Test
    public void testGetLastTransactionsShouldReturnOnly60SecondsAgo(){
        Transaction validTransaction = new Transaction(100.0, OffsetDateTime.now().minusDays(1));

        LocalDateTime fakeWhenAdded = LocalDateTime.now().minusSeconds(70);
        Transaction invalidTransaction = new Transaction(100.0, OffsetDateTime.now().minusDays(2));

        // altera o whenAdded para menos de um minuto, para validar se está correto o método
        ReflectionTestUtils.setField(invalidTransaction, "whenAdded", fakeWhenAdded);

        transactionStats.add(validTransaction);
        transactionStats.add(invalidTransaction);

        ArrayList<Transaction> lastTransactions = transactionStats.getLastTransactions();

        assertEquals(1, lastTransactions.size(), "Deve haver apenas 1 transação recente");
        assertTrue(lastTransactions.contains(validTransaction), "Deve conter a transação recente");
        assertFalse(lastTransactions.contains(invalidTransaction), "Não deve conter a transação antiga");
    }


    @Test
    public void testDeleteTransactions(){
        Transaction validTransaction = new Transaction(100.0, OffsetDateTime.now().minusDays(1));
        transactionStats.add(validTransaction);

        assertFalse(transactionStats.getLastTransactions().isEmpty(), "Deve haver uma transação");

        transactionStats.delete();

        assertTrue(transactionStats.getLastTransactions().isEmpty(), "Não deve haver nenhuma transação");

        transactionStats.delete();

        assertTrue(transactionStats.getLastTransactions().isEmpty(), "Não deve haver erro ao deletar com a lista vazia");
    }

}
