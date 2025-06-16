package com.comsystem.service.validation;

import com.comsystem.error.ConstraintViolationException;
import com.comsystem.error.ErrorType;
import com.comsystem.model.dto.TransactionDto;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class TransactionValidationImpl implements TransactionValidation {

    @Override
    public void validateTransactionRequest(List<TransactionDto> requests) {
        if (!CollectionUtils.isEmpty(requests)) {
            for (TransactionDto request : requests) {
                if (request.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
                    throw new ConstraintViolationException(
                            ErrorType.GB_424, ErrorType.GB_424.getMessage());
                } else if (request.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new ConstraintViolationException(
                            ErrorType.GB_428, ErrorType.GB_428.getMessage());
                }
            }

        } else {
            throw new ConstraintViolationException(ErrorType.GB_433, ErrorType.GB_433.getMessage());
        }
    }
}
