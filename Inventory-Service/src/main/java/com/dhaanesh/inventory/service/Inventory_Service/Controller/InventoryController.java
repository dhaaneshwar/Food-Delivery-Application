package com.dhaanesh.inventory.service.Inventory_Service.Controller;

import com.dhaanesh.inventory.service.Inventory_Service.Entity.Inventory;
import com.dhaanesh.inventory.service.Inventory_Service.Entity.StockRequest;
import com.dhaanesh.inventory.service.Inventory_Service.Service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public Inventory createInventory(@RequestBody Inventory inventory) {
        return inventoryService.createInventory(inventory);
    }

    @GetMapping("/{restaurantId}")
    public List<Inventory> getInventory(@PathVariable Long restaurantId) {
        return inventoryService.getInventoryByRestaurant(restaurantId);
    }

    @PutMapping("/{itemId}")
    public Inventory updateInventory(@PathVariable Long itemId, @RequestBody Inventory inventory) {
        return inventoryService.updateInventory(itemId, inventory);
    }

    @PutMapping("/{itemId}/increase")
    public Inventory increaseStock(@PathVariable Long itemId, @RequestBody StockRequest request) {
        return inventoryService.increaseStock(itemId, request);
    }

    @PutMapping("/{itemId}/decrease")
    public Inventory decreaseStock(@PathVariable Long itemId, @RequestBody StockRequest request) {
        return inventoryService.decreaseStock(itemId, request);
    }

    @GetMapping("/check/{itemId}")
    public Boolean checkAvailability(@PathVariable Long itemId) {
        return inventoryService.checkAvailability(itemId);
    }
}
