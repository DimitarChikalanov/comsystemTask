package com.comsystem.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorType {
    GB_420("Item name must not be blank"),
    GB_421("Item name must not be null"),
    GB_422("Item name must be between 1 and 255 characters"),
    GB_423("Quantity must have up to 10 integer and 2 fraction digits"),
    GB_424("Quantity must not be zero"),
    GB_425("Unit must not be blank"),
    GB_426("Unit must not be null"),
    GB_427("Unit must be between 1 and 255 characters"),
    GB_428("Unit price must be greater than 0"),
    GB_429("Unit price must have up to 10 integer and 2 fraction digits"),
    GB_430("Warehouse name must not be blank"),
    GB_431("Warehouse name must not be null"),
    GB_432("Warehouse name must be between 1 and 255 characters"),
    GB_433("Transaction list must contain at least one item"),
    GB_434("Quantity must not be null"),

    GB_500("Something went wrong"),
    ;
    private final String message;
}
