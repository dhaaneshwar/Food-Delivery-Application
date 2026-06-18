package com.dhaanesh.delivery.service.Delivery.Partner.Service.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DeliveryNotFoundException extends RuntimeException {

    public DeliveryNotFoundException() {
        super("Delivery Not found");
    }

    public DeliveryNotFoundException(String message) {
        super(message);
    }
}

