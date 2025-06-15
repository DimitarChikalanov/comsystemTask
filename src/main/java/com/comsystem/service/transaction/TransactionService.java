package com.comsystem.service.transaction;

import com.comsystem.model.dto.TransactionDto;
import java.util.List;

public interface TransactionService {

    void createTransaction(List<TransactionDto> request);

    void correctionTransaction(List<TransactionDto> request);
}
