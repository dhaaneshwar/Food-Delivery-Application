package com.dhaanesh.order.service.Order.Service.Controller;

import com.dhaanesh.order.service.Order.Service.Entity.Order;
import com.dhaanesh.order.service.Order.Service.Entity.OrderTimeline;
import com.dhaanesh.order.service.Order.Service.Service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }

    @PutMapping("/{id}/cancel")
    public String cancelOrder(@PathVariable Long id) {
        return orderService.cancelOrder(id);
    }

    @PutMapping("/{id}/confirm")
    public String confirmOrder(@PathVariable Long id) {
        return orderService.confirmOrder(id);
    }

    @PutMapping("/{id}/complete")
    public String completeOrder(@PathVariable Long id) {
        return orderService.completeOrder(id);
    }

    @GetMapping("/{id}/status")
    public String getOrderStatus(@PathVariable Long id) {
        return orderService.getOrderStatus(id);
    }

    @GetMapping("/{id}/timeline")
    public List<OrderTimeline> getTimeline(@PathVariable Long id) {
        return orderService.getTimeline(id);
    }
}
