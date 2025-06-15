package com.comsystem.model.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransactionDto {

    private String itemName;

    private double quantity;

    private String unit;

    private BigDecimal unitPrice;

    private String warehouseName;
}
