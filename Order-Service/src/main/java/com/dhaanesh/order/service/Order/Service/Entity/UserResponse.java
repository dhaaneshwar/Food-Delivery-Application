package com.dhaanesh.order.service.Order.Service.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserResponse {
    @Id
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
}
