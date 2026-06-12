package com.dhaanesh.delivery.service.Delivery.Partner.Service.Repository;

import com.dhaanesh.delivery.service.Delivery.Partner.Service.Entity.Delivery;
import com.dhaanesh.delivery.service.Delivery.Partner.Service.Entity.DeliveryPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner,Long> {
    Optional<DeliveryPartner> findFirstByAvailableTrue();
    Optional<Delivery> findByOrderId(Long orderId);
}
