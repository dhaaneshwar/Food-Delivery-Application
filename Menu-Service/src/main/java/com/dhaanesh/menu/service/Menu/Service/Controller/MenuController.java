package com.dhaanesh.menu.service.Menu.Service.Controller;

import com.dhaanesh.menu.service.Menu.Service.Entity.Menu;
import com.dhaanesh.menu.service.Menu.Service.Entity.MenuItem;
import com.dhaanesh.menu.service.Menu.Service.Service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menus")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public Menu createMenu(@RequestBody Menu menu) {
        return menuService.createMenu(menu);
    }

    @GetMapping("/{restaurantId}")
    public List<Menu> getMenus(@PathVariable Long restaurantId) {
        return menuService.getMenusByRestaurant(restaurantId);
    }

    @PutMapping("/{menuId}")
    public Menu updateMenu(@PathVariable Long menuId, @RequestBody Menu menu) {
        return menuService.updateMenu(menuId, menu);
    }

    @DeleteMapping("/{menuId}")
    public String deleteMenu(@PathVariable Long menuId) {
        return menuService.deleteMenu(menuId);
    }

    @PostMapping("/{menuId}/items")
    public MenuItem addMenuItem(@PathVariable Long menuId, @RequestBody MenuItem item) {
        return menuService.addMenuItem(menuId, item);
    }

    @GetMapping("/{menuId}/items")
    public List<MenuItem> getMenuItems(@PathVariable Long menuId) {
        return menuService.getMenuItems(menuId);
    }

    @PutMapping("/items/{itemId}")
    public MenuItem updateMenuItem(@PathVariable Long itemId, @RequestBody MenuItem item) {
        return menuService.updateMenuItem(itemId, item);
    }

    @DeleteMapping("/items/{itemId}")
    public String deleteMenuItem(@PathVariable Long itemId) {
        return menuService.deleteMenuItem(itemId);
    }
}
