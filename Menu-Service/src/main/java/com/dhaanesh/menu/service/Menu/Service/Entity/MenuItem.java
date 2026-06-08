package com.dhaanesh.menu.service.Menu.Service.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "menu_items")
@Data
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;

    private Double price;

    private String category;

    private boolean available;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
