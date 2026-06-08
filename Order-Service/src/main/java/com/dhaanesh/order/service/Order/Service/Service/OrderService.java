package com.dhaanesh.order.service.Order.Service.Service;

import com.dhaanesh.order.service.Order.Service.Entity.Order;
import com.dhaanesh.order.service.Order.Service.Entity.OrderTimeline;
import com.dhaanesh.order.service.Order.Service.Repository.OrderRepository;
import com.dhaanesh.order.service.Order.Service.Repository.OrderTimelineRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderTimelineRepository orderTimelineRepository;

    public OrderService(OrderRepository orderRepository, OrderTimelineRepository orderTimelineRepository) {
        this.orderRepository = orderRepository;
        this.orderTimelineRepository = orderTimelineRepository;
    }

    public Order createOrder(Order order) {

        order.setStatus("PLACED");
        order.setCreatedAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);
        addTimeline(savedOrder.getId(), "PLACED");
        return savedOrder;
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order Not Found"));
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public String cancelOrder(Long id) {
        updateStatus(id, "CANCELLED");
        return "Order Cancelled";
    }

    public String confirmOrder(Long id) {
        updateStatus(id, "CONFIRMED");
        return "Order Confirmed";
    }

    public String completeOrder(Long id) {
        updateStatus(id, "COMPLETED");
        return "Order Completed";
    }

    public String getOrderStatus(Long id) {
        return getOrder(id).getStatus();
    }

    public List<OrderTimeline> getTimeline(Long orderId) {
        return orderTimelineRepository.findByOrderIdOrderByUpdatedAt(orderId);
    }

    private void updateStatus(Long orderId, String status) {
        Order order = getOrder(orderId);
        order.setStatus(status);
        orderRepository.save(order);
        addTimeline(orderId, status);
    }

    private void addTimeline(Long orderId, String status) {
        OrderTimeline timeline = new OrderTimeline();
        timeline.setOrderId(orderId);
        timeline.setStatus(status);
        timeline.setUpdatedAt(LocalDateTime.now());
        orderTimelineRepository.save(timeline);
    }
}
