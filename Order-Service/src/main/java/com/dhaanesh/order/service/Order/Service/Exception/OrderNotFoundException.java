package com.dhaanesh.order.service.Order.Service.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException() {
        super("Order Not Found");
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}

