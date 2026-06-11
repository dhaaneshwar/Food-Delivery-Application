package com.dhaanesh.order.service.Order.Service.Entity;

import lombok.Data;

@Data
public class RestaurantResponse {
    private Long id;
    private String name;
    private boolean open;
}
