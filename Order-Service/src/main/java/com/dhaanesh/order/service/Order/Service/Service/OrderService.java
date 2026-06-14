package com.dhaanesh.order.service.Order.Service.Service;

import com.dhaanesh.order.service.Order.Service.Entity.*;
import com.dhaanesh.order.service.Order.Service.Repository.OrderRepository;
import com.dhaanesh.order.service.Order.Service.Repository.OrderTimelineRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderTimelineRepository orderTimelineRepository;

    private final RestTemplate restTemplate;

    public OrderService(OrderRepository orderRepository, OrderTimelineRepository orderTimelineRepository,RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.orderTimelineRepository = orderTimelineRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public Order createOrder(Order order) {
        UserResponse user = getUser(order.getUserId());
        if (user == null) {
            throw new RuntimeException("User Not Found");
        }

        RestaurantResponse restaurant = getRestaurant(order.getRestaurantId());
        if (!restaurant.isOpen()) {
            throw new RuntimeException("Restaurant Closed");
        }

        Boolean available = checkAvailability(order.getItemId());
        if (!available) {
            throw new RuntimeException("Item Out Of Stock");
        }

        decreaseStock(order.getItemId(), order.getQuantity());
        order.setStatus("PLACED");
        order.setCreatedAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);
        assignDelivery(savedOrder.getId());
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
        restTemplate.put("http://delivery-partner-service/deliveries/"+orderId+"/delivered",null);
    }

    public UserResponse getUser(Long userId) {
        return restTemplate.getForObject("http://user-service/users/findById/" + userId, UserResponse.class);
    }

    public RestaurantResponse getRestaurant(Long restaurantId) {
        return restTemplate.getForObject("http://restaurant-service/restaurants/" + restaurantId, RestaurantResponse.class);
    }

    public Boolean checkAvailability(Long itemId) {
        return restTemplate.getForObject("http://inventory-service/inventory/check/"
                        + itemId, Boolean.class);
    }

    public void decreaseStock(Long itemId, Integer quantity) {
        StockRequest request = new StockRequest();
        request.setQuantity(quantity);
        restTemplate.put("http://inventory-service/inventory/" + itemId + "/decrease", request);
    }

    public void assignDelivery(Long orderId) {
        DeliveryAssignRequest request = new DeliveryAssignRequest();
        request.setOrderId(orderId);
        restTemplate.postForObject("http://delivery-partner-service/deliveries/assign", request, Object.class);
    }
}
