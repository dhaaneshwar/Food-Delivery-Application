package com.dhaanesh.inventory.service.Inventory_Service.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "inventory")
@Data
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long restaurantId;

    private Long itemId;

    private String itemName;

    private Integer quantity;

    private Integer minimumStock;

    private Boolean available;
}
