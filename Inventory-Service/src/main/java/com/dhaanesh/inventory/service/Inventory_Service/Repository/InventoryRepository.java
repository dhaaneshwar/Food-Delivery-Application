package com.dhaanesh.inventory.service.Inventory_Service.Repository;

import com.dhaanesh.inventory.service.Inventory_Service.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    List<Inventory> findByRestaurantId(Long restaurantId);

    Optional<Inventory> findByItemId(Long itemId);
}
