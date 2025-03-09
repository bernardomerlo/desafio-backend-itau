package com.bernardomerlo.services;

import com.bernardomerlo.entities.Transaction;
import com.bernardomerlo.entities.TransactionStats;
import com.bernardomerlo.exceptions.InvalidTimeException;
import com.bernardomerlo.exceptions.InvalidValueException;
import com.bernardomerlo.exceptions.NullValuesException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ValidateTransactionTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionStats transactionStats;

    @Test
    public void shouldThrowNullValuesException_whenDateOrValueIsNull() {
        Transaction transaction = new Transaction();
        transaction.setDate(null);
        transaction.setValue(100.0);

        assertThrows(NullValuesException.class, () -> {
            transactionService.validateTransaction(transaction);
        });
    }

    @Test
    public void shouldThrowInvalidTimeException_whenDateIsAtFuture() {
        Transaction transaction = new Transaction();
        transaction.setDate(OffsetDateTime.now().plusDays(1));
        transaction.setValue(100.0);

        assertThrows(InvalidTimeException.class, () -> {
            transactionService.validateTransaction(transaction);
        });
    }

    @Test
    public void shouldThrowInvalidValueException_whenValueIsNegative() {
        Transaction transaction = new Transaction();
        transaction.setDate(OffsetDateTime.now().minusDays(1));
        transaction.setValue(-1.0);

        assertThrows(InvalidValueException.class, () -> {
            transactionService.validateTransaction(transaction);
        });
    }

    @Test
    public void shouldNotThrowException_whenTransactionIsValid() {
        Transaction transaction = new Transaction();
        transaction.setDate(OffsetDateTime.now().minusDays(1));
        transaction.setValue(100.0);

        assertDoesNotThrow(() -> {
            transactionService.validateTransaction(transaction);
        });
    }

}
