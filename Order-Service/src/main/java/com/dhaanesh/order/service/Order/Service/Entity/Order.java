package com.dhaanesh.order.service.Order.Service.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long restaurantId;

    private Double totalAmount;

    private String status;

    private LocalDateTime createdAt;
}
