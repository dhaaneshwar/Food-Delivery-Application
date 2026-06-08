package com.dhaanesh.restaurant.service.Restaurant.Service.Repository;

import com.dhaanesh.restaurant.service.Restaurant.Service.Entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long>{

    List<Restaurant> findByNameContainingIgnoreCase(
            String name
    );

    List<Restaurant> findByCuisineContainingIgnoreCase(
            String cuisine
    );
}
