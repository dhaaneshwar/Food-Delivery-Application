package com.dhaanesh.order.service.Order.Service.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RestaurantClosedException extends RuntimeException {

    public RestaurantClosedException() {
        super("Restaurant is closed");
    }

    public RestaurantClosedException(String message) {
        super(message);
    }
}

