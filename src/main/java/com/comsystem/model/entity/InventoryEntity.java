package com.comsystem.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "inventory",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"item_name", "warehouse_name", "unit_price"})
        })
@Getter
@Setter
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_id_seq")
    @SequenceGenerator(
            name = "inventory_id_seq",
            sequenceName = "inventory_id_seq",
            allocationSize = 1)
    private Long id;

    private String itemName;

    private String warehouseName;

    private BigDecimal unitPrice;

    private double quantity;

    private String unit;
}
