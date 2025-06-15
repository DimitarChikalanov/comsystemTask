package com.comsystem.controller;

import com.comsystem.model.dto.TransactionDto;
import com.comsystem.service.transaction.TransactionService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("transactions")
    public ResponseEntity<Void> createTransactions(
            @RequestBody @Valid List<TransactionDto> transactions) {
        log.info("Create transactions: {}", transactions);
        transactionService.createTransaction(transactions);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("transactions")
    public ResponseEntity<Void> correctTransaction(
            @RequestBody @Valid List<TransactionDto> request) {
        log.info("Correct transactions: {}", request);
        transactionService.correctionTransaction(request);
        return ResponseEntity.ok().build();
    }
}
