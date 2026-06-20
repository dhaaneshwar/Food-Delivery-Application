package com.dhaanesh.delivery.service.Delivery.Partner.Service.Exception;

public class OrderPickUpException extends RuntimeException{

    public OrderPickUpException(){
        super("Order Already picked up");
    }

    public OrderPickUpException(String message){
        super(message);
    }
}
