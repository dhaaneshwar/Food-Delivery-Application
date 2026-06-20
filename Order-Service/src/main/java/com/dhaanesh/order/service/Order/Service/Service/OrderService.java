package com.dhaanesh.order.service.Order.Service.Service;

import com.dhaanesh.order.service.Order.Service.Entity.*;
import com.dhaanesh.order.service.Order.Service.Exception.UserNotFoundException;
import com.dhaanesh.order.service.Order.Service.Exception.RestaurantClosedException;
import com.dhaanesh.order.service.Order.Service.Exception.ItemOutOfStockException;
import com.dhaanesh.order.service.Order.Service.Exception.OrderNotFoundException;
import com.dhaanesh.order.service.Order.Service.Repository.OrderRepository;
import com.dhaanesh.order.service.Order.Service.Repository.OrderTimelineRepository;
import jakarta.transaction.Transactional;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderTimelineRepository orderTimelineRepository;

    private final RestTemplate restTemplate;

    private final WebClient webClient;

    private KafkaTemplate<String, DeliveryAssignRequest> kafkaTemplate;

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderRepository orderRepository, OrderTimelineRepository orderTimelineRepository, RestTemplate restTemplate, WebClient.Builder webClientBuilder, KafkaTemplate<String, DeliveryAssignRequest> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.orderTimelineRepository = orderTimelineRepository;
        this.restTemplate = restTemplate;
        this.webClient = webClientBuilder.build();
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public Order createOrder(Order order) {
        log.info("Creating order: userId={}, restaurantId={}, itemId={}, quantity={}", order.getUserId(), order.getRestaurantId(), order.getItemId(), order.getQuantity());
        UserResponse user = getUser(order.getUserId());
        if (user == null) {
            log.warn("User not found: userId={}", order.getUserId());
            throw new UserNotFoundException();
        }

        RestaurantResponse restaurant = getRestaurant(order.getRestaurantId());
        if (!restaurant.isOpen()) {
            throw new RestaurantClosedException();
        }

        Boolean available = checkAvailability(order.getItemId());
        if (!available) {
            throw new ItemOutOfStockException();
        }

        decreaseStock(order.getItemId(), order.getQuantity());
        order.setStatus("PLACED");
        order.setCreatedAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);
        log.info("Order created: id={}, status={}", savedOrder.getOrderId(), savedOrder.getStatus());
        assignDelivery(savedOrder.getOrderId());
        return savedOrder;
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("No order found for the id {} " + id));
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
        log.debug("Adding timeline entry for order {}", orderId);
        OrderTimeline timeline = new OrderTimeline();
        timeline.setOrderId(orderId);
        timeline.setStatus(status);
        timeline.setUpdatedAt(LocalDateTime.now());
        orderTimelineRepository.save(timeline);
        // notify delivery partner service that order was delivered using WebClient (synchronous)

            webClient.put()
                    .uri("http://delivery-partner-service/deliveries/{orderId}/delivered", orderId)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();  // remove block for synchronous communication

    }

    public UserResponse getUser(Long userId) {
        return restTemplate.getForObject("http://user-service/users/findById/" + userId, UserResponse.class);
    }

    public RestaurantResponse getRestaurant(Long restaurantId) {
        return restTemplate.getForObject("http://restaurant-service/restaurants/" + restaurantId, RestaurantResponse.class);
    }

    public Boolean checkAvailability(Long itemId) {
//        restTemplate.getForObject("http://inventory-service/inventory/check/"
//                + itemId, Boolean.class);
            return webClient.get()
                    .uri("http://inventory-service/inventory/check/{itemId}", itemId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

    }

    public void decreaseStock(Long itemId, Integer quantity) {
        log.debug("Decreasing stock for item {} by {}", itemId, quantity);
        StockRequest request = new StockRequest();
        request.setQuantity(quantity);
//        restTemplate.put("http://inventory-service/inventory/" + itemId + "/decrease", request);
            webClient.put()
                    .uri("http://inventory-service/inventory/{itemId}/decrease", itemId)
                    .bodyValue(request)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

    }

    public void assignDelivery(Long orderId) {
        log.debug("Assigning delivery for order {}", orderId);
        DeliveryAssignRequest request = new DeliveryAssignRequest();
        request.setOrderId(orderId);

//        restTemplate.postForObject("http://delivery-partner-service/deliveries/assign", request, Object.class);
        /*
        webClient.post()
                .uri("http://delivery-partner-service/deliveries/assign")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
         */

        kafkaTemplate.send("delivery-assignments", request);
    }
}
