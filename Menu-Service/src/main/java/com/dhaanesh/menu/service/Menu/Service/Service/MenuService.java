package com.dhaanesh.menu.service.Menu.Service.Service;

import com.dhaanesh.menu.service.Menu.Service.Entity.Menu;
import com.dhaanesh.menu.service.Menu.Service.Entity.MenuItem;
import com.dhaanesh.menu.service.Menu.Service.Repository.MenuItemRepository;
import com.dhaanesh.menu.service.Menu.Service.Repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    public MenuService(MenuRepository menuRepository, MenuItemRepository menuItemRepository) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public Menu createMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public List<Menu> getMenusByRestaurant(Long restaurantId) {
        return menuRepository.findMenusByRestaurantId(restaurantId);
    }

    public Menu updateMenu(Long menuId, Menu updatedMenu) {
        Menu menu = menuRepository.findMenuById(menuId).orElseThrow(() -> new RuntimeException("Menu Not Found"));
        menu.setMenuName(updatedMenu.getMenuName());
        return menuRepository.save(menu);
    }

    public String deleteMenu(Long menuId) {
        Menu menu = menuRepository.findMenuById(menuId).orElseThrow(() -> new RuntimeException("Menu Not Found"));
        menuRepository.delete(menu);
        return "Menu Deleted Successfully";
    }

    public MenuItem addMenuItem(Long menuId, MenuItem item) {
        Menu menu = menuRepository.findMenuById(menuId).orElseThrow(() -> new RuntimeException("Menu Not Found"));
        item.setMenu(menu);
        return menuItemRepository.save(item);
    }

    public List<MenuItem> getMenuItems(Long menuId) {
        return menuItemRepository.findItemsByMenuId(menuId);
    }

    public MenuItem updateMenuItem(Long itemId, MenuItem updatedItem) {
        MenuItem item = menuItemRepository.findItemById(itemId).orElseThrow(() -> new RuntimeException("Menu Item Not Found"));
        item.setItemName(updatedItem.getItemName());
        item.setPrice(updatedItem.getPrice());
        item.setCategory(updatedItem.getCategory());
        item.setAvailable(updatedItem.isAvailable());
        return menuItemRepository.save(item);
    }

    public String deleteMenuItem(Long itemId) {
        MenuItem item = menuItemRepository.findItemById(itemId).orElseThrow(() -> new RuntimeException("Menu Item Not Found"));
        menuItemRepository.delete(item);
        return "Menu Item Deleted Successfully";
    }
}
