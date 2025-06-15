package com.comsystem.service.transaction;

import com.comsystem.mapper.TransactionMapper;
import com.comsystem.model.dto.TransactionDto;
import com.comsystem.model.entity.TransactionEntity;
import com.comsystem.repository.InventoryRepository;
import com.comsystem.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional
    public void createTransaction(List<TransactionDto> request) {

        for (TransactionDto transactionDto : request) {
            TransactionEntity entity = transactionMapper.mapToTransactionEntity(transactionDto);
            transactionRepository.save(entity);
            inventoryRepository.upsertInventory(
                    entity.getItemName(),
                    entity.getWarehouseName(),
                    entity.getUnitPrice(),
                    entity.getQuantity(),
                    entity.getUnit());
        }
    }
}
