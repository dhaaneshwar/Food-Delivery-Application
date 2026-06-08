package com.dhaanesh.delivery.service.Delivery.Partner.Service.Repository;

import com.dhaanesh.delivery.service.Delivery.Partner.Service.Entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
}
