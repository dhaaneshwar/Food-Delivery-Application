package com.dhaanesh.menu.service.Menu.Service.Repository;

import com.dhaanesh.menu.service.Menu.Service.Entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query(value = "SELECT * FROM menus WHERE restaurant_id = :restaurantId", nativeQuery = true)
    List<Menu> findMenusByRestaurantId(@Param("restaurantId") Long restaurantId);

    @Query(value = "SELECT * FROM menus WHERE id = :menuId", nativeQuery = true)
    Optional<Menu> findMenuById(@Param("menuId") Long menuId);
}
