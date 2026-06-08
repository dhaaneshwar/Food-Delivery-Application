package com.dhaanesh.food.delivery.user.service.UserService.Repository;

import com.dhaanesh.food.delivery.user.service.UserService.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUserId(Long userId);
}
