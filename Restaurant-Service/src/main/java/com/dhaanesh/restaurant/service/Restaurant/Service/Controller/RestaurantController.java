package com.dhaanesh.restaurant.service.Restaurant.Service.Controller;

import com.dhaanesh.restaurant.service.Restaurant.Service.Entity.Restaurant;
import com.dhaanesh.restaurant.service.Restaurant.Service.Service.RestaurantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantService.createRestaurant(restaurant);
    }

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable Long id) {
        return restaurantService.getRestaurant(id);
    }

    @PutMapping("/{id}")
    public Restaurant updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        return restaurantService.updateRestaurant(id, restaurant);
    }

    @DeleteMapping("/{id}")
    public String deleteRestaurant(@PathVariable Long id) {
        return restaurantService.deleteRestaurant(id);
    }

    @PutMapping("/{id}/open")
    public Restaurant openRestaurant(@PathVariable Long id) {
        return restaurantService.openRestaurant(id);
    }

    @PutMapping("/{id}/close")
    public Restaurant closeRestaurant(@PathVariable Long id) {
        return restaurantService.closeRestaurant(id);
    }

    @GetMapping("/search")
    public List<Restaurant> searchRestaurants(@RequestParam(required = false) String name, @RequestParam(required = false) String cuisine) {
        if (name != null) {
            return restaurantService.searchByName(name);
        }
        if (cuisine != null) {
            return restaurantService.searchByCuisine(cuisine);
        }
        return List.of();
    }
}
