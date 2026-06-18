package com.dhaanesh.order.service.Order.Service.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User Not Found");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}

