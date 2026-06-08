package com.dhaanesh.menu.service.Menu.Service.Repository;

import com.dhaanesh.menu.service.Menu.Service.Entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    @Query(value = "SELECT * FROM menu_items WHERE menu_id = :menuId", nativeQuery = true)
    List<MenuItem> findItemsByMenuId(@Param("menuId") Long menuId);

    @Query(value = "SELECT * FROM menu_items WHERE id = :itemId", nativeQuery = true)
    Optional<MenuItem> findItemById(@Param("itemId") Long itemId);
}
