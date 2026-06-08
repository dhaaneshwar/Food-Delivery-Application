package com.dhaanesh.restaurant.service.Restaurant.Service.Service;

import com.dhaanesh.restaurant.service.Restaurant.Service.Entity.Restaurant;
import com.dhaanesh.restaurant.service.Restaurant.Service.Repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        restaurant.setOpen(true);
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurant(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Restaurant Not Found"));
    }

    public Restaurant updateRestaurant(Long id, Restaurant updatedRestaurant) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() ->
                                new RuntimeException("Restaurant Not Found"));
        restaurant.setName(updatedRestaurant.getName());
        restaurant.setCuisine(updatedRestaurant.getCuisine());
        restaurant.setAddress(updatedRestaurant.getAddress());
        return restaurantRepository.save(restaurant);
    }

    public String deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
        return "Restaurant Deleted Successfully";
    }

    public Restaurant openRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() ->
                                new RuntimeException("Restaurant Not Found"));
        restaurant.setOpen(true);
        return restaurantRepository.save(restaurant);
    }

    public Restaurant closeRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() ->
                                new RuntimeException("Restaurant Not Found"));
        restaurant.setOpen(false);
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> searchByName(String name) {
        return restaurantRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Restaurant> searchByCuisine(String cuisine) {
        return restaurantRepository.findByCuisineContainingIgnoreCase(cuisine);
    }
}