package com.dhaanesh.delivery.service.Delivery.Partner.Service.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "delivery_partners")
@Data
public class DeliveryPartner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

    private String vehicleNumber;

    private Double latitude;

    private Double longitude;

    private boolean available;
}
