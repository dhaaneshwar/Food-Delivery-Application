package com.dhaanesh.inventory.service.Inventory_Service.Service;

import com.dhaanesh.inventory.service.Inventory_Service.Entity.Inventory;
import com.dhaanesh.inventory.service.Inventory_Service.Entity.StockRequest;
import com.dhaanesh.inventory.service.Inventory_Service.Repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Inventory createInventory(Inventory inventory) {

        inventory.setAvailable(inventory.getQuantity() > 0);
        return inventoryRepository.save(inventory);
    }

    public List<Inventory> getInventoryByRestaurant(Long restaurantId) {

        return inventoryRepository.findByRestaurantId(restaurantId);
    }

    public Inventory updateInventory(Long itemId, Inventory updatedInventory) {

        Inventory inventory = inventoryRepository.findByItemId(itemId).orElseThrow(() -> new RuntimeException("Item Not Found"));

        inventory.setItemName(updatedInventory.getItemName());
        inventory.setQuantity(updatedInventory.getQuantity());
        inventory.setMinimumStock(updatedInventory.getMinimumStock());
        inventory.setAvailable(inventory.getQuantity() > 0);
        return inventoryRepository.save(inventory);
    }

    public Inventory increaseStock(Long itemId, StockRequest request) {

        Inventory inventory = inventoryRepository.findByItemId(itemId).orElseThrow(() -> new RuntimeException("Item Not Found"));
        inventory.setQuantity(inventory.getQuantity() + request.getQuantity());
        inventory.setAvailable(true);
        return inventoryRepository.save(inventory);
    }

    public Inventory decreaseStock(Long itemId, StockRequest request) {
        Inventory inventory = inventoryRepository.findByItemId(itemId).orElseThrow(() -> new RuntimeException("Item Not Found"));
        int remaining = inventory.getQuantity() - request.getQuantity();
        inventory.setQuantity(Math.max(remaining, 0));
        inventory.setAvailable(inventory.getQuantity() > 0);
        return inventoryRepository.save(inventory);
    }

    public Boolean checkAvailability(Long itemId) {
        Inventory inventory = inventoryRepository.findByItemId(itemId).orElseThrow(() -> new RuntimeException("Item Not Found"));
        return inventory.getAvailable();
    }
}
