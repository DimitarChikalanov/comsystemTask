package com.comsystem.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.comsystem.error.ConstraintViolationException;
import com.comsystem.error.ErrorType;
import com.comsystem.model.dto.TransactionDto;
import com.comsystem.service.validation.TransactionValidationImpl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class TransactionValidationTest {

    private final TransactionValidationImpl transactionValidation = new TransactionValidationImpl();

    @Test
    void validateTransactionRequest_shouldThrowExceptionWhenRequestListIsEmpty() {
        // Arrange
        List<TransactionDto> requests = Collections.emptyList();

        // Act & Assert
        ConstraintViolationException exception =
                assertThrows(
                        ConstraintViolationException.class,
                        () -> transactionValidation.validateTransactionRequest(requests));
        assertEquals(ErrorType.GB_433, exception.getErrorType());
        assertEquals(ErrorType.GB_433.getMessage(), exception.getMessage());
    }

    @Test
    void validateTransactionRequest_shouldThrowExceptionWhenQuantityIsZero() {
        // Arrange
        TransactionDto transaction = new TransactionDto();
        transaction.setItemName("Test Item");
        transaction.setQuantity(BigDecimal.ZERO);
        transaction.setUnit("piece");
        transaction.setUnitPrice(BigDecimal.valueOf(10.00));
        transaction.setWarehouseName("Warehouse A");

        List<TransactionDto> requests = List.of(transaction);

        // Act & Assert
        ConstraintViolationException exception =
                assertThrows(
                        ConstraintViolationException.class,
                        () -> transactionValidation.validateTransactionRequest(requests));
        assertEquals(ErrorType.GB_424, exception.getErrorType());
        assertEquals(ErrorType.GB_424.getMessage(), exception.getMessage());
    }

    @Test
    void validateTransactionRequest_shouldThrowExceptionWhenUnitPriceIsNotPositive() {
        // Arrange
        TransactionDto transaction = new TransactionDto();
        transaction.setItemName("Test Item");
        transaction.setQuantity(BigDecimal.valueOf(5));
        transaction.setUnit("piece");
        transaction.setUnitPrice(BigDecimal.ZERO);
        transaction.setWarehouseName("Warehouse A");

        List<TransactionDto> requests = List.of(transaction);

        // Act & Assert
        ConstraintViolationException exception =
                assertThrows(
                        ConstraintViolationException.class,
                        () -> transactionValidation.validateTransactionRequest(requests));
        assertEquals(ErrorType.GB_428, exception.getErrorType());
        assertEquals(ErrorType.GB_428.getMessage(), exception.getMessage());
    }

    @Test
    void validateTransactionRequest_shouldValidateSuccessfullyForValidRequest() {
        // Arrange
        TransactionDto transaction = new TransactionDto();
        transaction.setItemName("Test Item");
        transaction.setQuantity(BigDecimal.valueOf(10));
        transaction.setUnit("piece");
        transaction.setUnitPrice(BigDecimal.valueOf(50.00));
        transaction.setWarehouseName("Warehouse A");

        List<TransactionDto> requests = List.of(transaction);

        // Act
        transactionValidation.validateTransactionRequest(requests);

        // Assert
        // No exception is thrown when valid
    }

    @Test
    void validateTransactionRequest_shouldThrowExceptionWhenAnyTransactionInListIsInvalid() {
        // Arrange
        TransactionDto validTransaction = new TransactionDto();
        validTransaction.setItemName("Valid Item");
        validTransaction.setQuantity(BigDecimal.valueOf(5));
        validTransaction.setUnit("box");
        validTransaction.setUnitPrice(BigDecimal.valueOf(20.00));
        validTransaction.setWarehouseName("Warehouse B");

        TransactionDto invalidTransaction = new TransactionDto();
        invalidTransaction.setItemName("Invalid Item");
        invalidTransaction.setQuantity(BigDecimal.ZERO);
        invalidTransaction.setUnit("box");
        invalidTransaction.setUnitPrice(BigDecimal.valueOf(20.00));
        invalidTransaction.setWarehouseName("Warehouse B");

        List<TransactionDto> requests = new ArrayList<>();
        requests.add(validTransaction);
        requests.add(invalidTransaction);

        // Act & Assert
        ConstraintViolationException exception =
                assertThrows(
                        ConstraintViolationException.class,
                        () -> transactionValidation.validateTransactionRequest(requests));
        assertEquals(ErrorType.GB_424, exception.getErrorType());
        assertEquals(ErrorType.GB_424.getMessage(), exception.getMessage());
    }
}
