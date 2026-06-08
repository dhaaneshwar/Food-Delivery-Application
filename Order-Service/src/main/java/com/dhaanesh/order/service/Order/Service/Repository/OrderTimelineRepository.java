package com.dhaanesh.order.service.Order.Service.Repository;

import com.dhaanesh.order.service.Order.Service.Entity.OrderTimeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderTimelineRepository extends JpaRepository<OrderTimeline,Long> {
    List<OrderTimeline> findByOrderIdOrderByUpdatedAt(Long orderId);
}
