package com.dhaanesh.food.delivery.user.service.UserService.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "addresses")
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    private String city;

    private String state;

    private String pincode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
