package comsystem.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.comsystem.ComsystemApplication;
import com.comsystem.model.dto.TransactionDto;
import com.comsystem.model.entity.InventoryEntity;
import com.comsystem.model.entity.TransactionEntity;
import com.comsystem.repository.InventoryRepository;
import com.comsystem.repository.TransactionRepository;
import com.comsystem.service.transaction.TransactionService;
import comsystem.initializer.IntegrationTestInitializer;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ComsystemApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionIntegrationTest extends IntegrationTestInitializer {

    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;
    private final InventoryRepository inventoryRepository;

    @Autowired
    public TransactionIntegrationTest(
            TransactionService transactionService,
            TransactionRepository transactionRepository,
            InventoryRepository inventoryRepository) {
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @AfterEach
    void cleanDb() {
        transactionRepository.deleteAll();
        inventoryRepository.deleteAll();
    }

    @Test
    void testCreateTransaction() {
        TransactionDto dto = new TransactionDto();
        dto.setItemName("Картофи");
        dto.setQuantity(BigDecimal.TEN);
        dto.setUnit("kg");
        dto.setUnitPrice(new BigDecimal("1.20"));
        dto.setWarehouseName("Склад София");

        transactionService.createTransaction(List.of(dto));

        List<TransactionEntity> transactions = transactionRepository.findAll();

        TransactionEntity savedTx = transactions.getFirst();
        assertThat(savedTx.getItemName()).isEqualTo(dto.getItemName());
        assertThat(savedTx.getQuantity()).isEqualByComparingTo(dto.getQuantity());
        assertThat(savedTx.getUnit()).isEqualTo(dto.getUnit());
        assertThat(savedTx.getUnitPrice()).isEqualByComparingTo(dto.getUnitPrice());
        assertThat(savedTx.getWarehouseName()).isEqualTo(dto.getWarehouseName());

        List<InventoryEntity> inventories = inventoryRepository.findAll();

        InventoryEntity inv = inventories.getFirst();
        assertThat(inv.getItemName()).isEqualTo(dto.getItemName());
        assertThat(inv.getQuantity()).isEqualByComparingTo(dto.getQuantity());
        assertThat(inv.getUnit()).isEqualTo(dto.getUnit());
        assertThat(inv.getUnitPrice()).isEqualByComparingTo(dto.getUnitPrice());
        assertThat(inv.getWarehouseName()).isEqualTo(dto.getWarehouseName());
    }

    @Test
    void testCorrectTransactionUpdatesInventory() {
        TransactionDto initialDto = new TransactionDto();
        initialDto.setItemName("Захар");
        initialDto.setQuantity(BigDecimal.valueOf(20.0));
        initialDto.setUnit("kg");
        initialDto.setUnitPrice(new BigDecimal("3.00"));
        initialDto.setWarehouseName("Склад Бургас");

        transactionService.correctionTransaction(List.of(initialDto));

        TransactionDto correctionDto = new TransactionDto();
        correctionDto.setItemName("Захар");
        correctionDto.setQuantity(BigDecimal.valueOf(-10.0));
        correctionDto.setUnit("kg");
        correctionDto.setUnitPrice(new BigDecimal("3.00"));
        correctionDto.setWarehouseName("Склад Бургас");

        transactionService.correctionTransaction(List.of(correctionDto));

        List<TransactionEntity> transactions = transactionRepository.findAll();

        TransactionEntity originalTx =
                transactions.stream()
                        .filter(tx -> tx.getQuantity().compareTo(BigDecimal.valueOf(20)) == 0)
                        .findFirst()
                        .orElseThrow();

        assertThat(originalTx.getItemName()).isEqualTo(initialDto.getItemName());
        assertThat(originalTx.getQuantity()).isEqualByComparingTo(initialDto.getQuantity());
        assertThat(originalTx.getUnit()).isEqualTo(initialDto.getUnit());
        assertThat(originalTx.getUnitPrice()).isEqualByComparingTo(initialDto.getUnitPrice());
        assertThat(originalTx.getWarehouseName()).isEqualTo(initialDto.getWarehouseName());

        TransactionEntity correctionTx =
                transactions.stream()
                        .filter(tx -> tx.getQuantity().compareTo(BigDecimal.valueOf(-10.0)) == 0)
                        .findFirst()
                        .orElseThrow();

        assertThat(correctionTx.getItemName()).isEqualTo(correctionDto.getItemName());
        assertThat(correctionTx.getQuantity()).isEqualByComparingTo(correctionDto.getQuantity());
        assertThat(correctionTx.getUnit()).isEqualTo(correctionDto.getUnit());
        assertThat(correctionTx.getUnitPrice()).isEqualByComparingTo(correctionDto.getUnitPrice());
        assertThat(correctionTx.getWarehouseName()).isEqualTo(correctionDto.getWarehouseName());

        List<InventoryEntity> inventories = inventoryRepository.findAll();

        InventoryEntity inventory = inventories.get(0);
        assertThat(inventory.getItemName()).isEqualTo("Захар");
        assertThat(inventory.getUnit()).isEqualTo("kg");
        assertThat(inventory.getWarehouseName()).isEqualTo("Склад Бургас");
        assertThat(inventory.getUnitPrice()).isEqualByComparingTo("3.00");
        assertThat(inventory.getQuantity()).isEqualByComparingTo(BigDecimal.valueOf(10)); // 20 - 10
    }
}
