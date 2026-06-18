package com.dhaanesh.order.service.Order.Service.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ItemOutOfStockException extends RuntimeException {

    public ItemOutOfStockException() {
        super("Item out of stock");
    }

    public ItemOutOfStockException(String message) {
        super(message);
    }
}

