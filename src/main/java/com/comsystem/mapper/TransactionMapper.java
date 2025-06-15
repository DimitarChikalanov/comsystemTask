package com.comsystem.mapper;

import com.comsystem.model.dto.TransactionDto;
import com.comsystem.model.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

    TransactionEntity mapToTransactionEntity(TransactionDto dto);
}
