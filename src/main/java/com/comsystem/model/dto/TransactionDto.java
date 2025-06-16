package com.comsystem.model.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransactionDto {

    @NotBlank(message = "GB_420")
    @NotNull(message = "GB_421")
    @Size(min = 1, max = 255, message = "GB_422")
    private String itemName;

    @Digits(integer = 10, fraction = 2, message = "GB_423")
    @NotNull(message = "GB_434")
    private BigDecimal quantity;

    @NotBlank(message = "GB_425")
    @NotNull(message = "GB_426")
    @Size(min = 1, max = 255, message = "GB_427")
    private String unit;

    @Digits(integer = 10, fraction = 2, message = "GB_429")
    private BigDecimal unitPrice;

    @NotBlank(message = "GB_430")
    @NotNull(message = "GB_431")
    @Size(min = 1, max = 255, message = "GB_432")
    private String warehouseName;
}
