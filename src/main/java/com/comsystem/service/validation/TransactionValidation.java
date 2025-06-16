package com.comsystem.service.validation;

import com.comsystem.model.dto.TransactionDto;
import java.util.List;

public interface TransactionValidation {

    void validateTransactionRequest(List<TransactionDto> requests);
}
