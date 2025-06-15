package com.comsystem.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_seq")
    @SequenceGenerator(
            name = "transaction_id_seq",
            sequenceName = "transactions_id_seq",
            allocationSize = 1)
    private Long id;

    private String itemName;

    private double quantity;

    private String unit;

    private BigDecimal unitPrice;

    private String warehouseName;

    private LocalDateTime createdAt = LocalDateTime.now();
}
