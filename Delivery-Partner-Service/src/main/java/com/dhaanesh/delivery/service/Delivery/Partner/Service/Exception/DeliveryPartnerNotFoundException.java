package com.dhaanesh.delivery.service.Delivery.Partner.Service.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DeliveryPartnerNotFoundException extends RuntimeException {

    public DeliveryPartnerNotFoundException() {
        super("Delivery Partner Not Found");
    }

    public DeliveryPartnerNotFoundException(String message) {
        super(message);
    }
}

