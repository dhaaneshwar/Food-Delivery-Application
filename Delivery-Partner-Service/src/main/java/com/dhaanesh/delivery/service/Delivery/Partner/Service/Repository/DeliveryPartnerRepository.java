package com.dhaanesh.delivery.service.Delivery.Partner.Service.Repository;

import com.dhaanesh.delivery.service.Delivery.Partner.Service.Entity.DeliveryPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner,Long> {
    Optional<DeliveryPartner> findFirstByAvailableTrue();

    // Native query to fetch the first available delivery partner
    @Query(value = "SELECT * FROM delivery_partners WHERE available = true LIMIT 1", nativeQuery = true)
    Optional<DeliveryPartner> findFirstAvailableNative();
//    Optional<Delivery> findByOrderId(Long orderId);

    /*
    @Query(value = "SELECT * FROM user WHERE email = ?1",nativeQuery = true)
    User findByEmail(String email);

    @Query(value = "SELECT * FROM user WHERE email = :email",nativeQuery = true)
    User findByEmail(@Param("email") String email);

     */
}
