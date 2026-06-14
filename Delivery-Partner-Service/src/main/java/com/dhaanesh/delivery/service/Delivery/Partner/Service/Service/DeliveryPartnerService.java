package com.dhaanesh.delivery.service.Delivery.Partner.Service.Service;

import com.dhaanesh.delivery.service.Delivery.Partner.Service.Entity.Delivery;
import com.dhaanesh.delivery.service.Delivery.Partner.Service.Entity.DeliveryAssignRequest;
import com.dhaanesh.delivery.service.Delivery.Partner.Service.Entity.DeliveryPartner;
import com.dhaanesh.delivery.service.Delivery.Partner.Service.Entity.LocationRequest;
import com.dhaanesh.delivery.service.Delivery.Partner.Service.Repository.DeliveryPartnerRepository;
import com.dhaanesh.delivery.service.Delivery.Partner.Service.Repository.DeliveryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DeliveryPartnerService {

    private final DeliveryPartnerRepository deliveryPartnerRepository;

    private final DeliveryRepository deliveryRepository;

    public DeliveryPartnerService(DeliveryPartnerRepository deliveryPartnerRepository, DeliveryRepository deliveryRepository) {
        this.deliveryPartnerRepository = deliveryPartnerRepository;
        this.deliveryRepository = deliveryRepository;
    }

    public DeliveryPartner createPartner(DeliveryPartner partner) {
        partner.setAvailable(true);
        return deliveryPartnerRepository.save(partner);
    }

    public DeliveryPartner getPartner(Long id) {
        return deliveryPartnerRepository.findById(id).orElseThrow(() -> new RuntimeException("Partner Not Found"));
    }

    public DeliveryPartner updatePartner(Long id, DeliveryPartner updatedPartner) {
        DeliveryPartner partner = getPartner(id);
        partner.setName(updatedPartner.getName());
        partner.setPhoneNumber(updatedPartner.getPhoneNumber());
        partner.setVehicleNumber(updatedPartner.getVehicleNumber());
        return deliveryPartnerRepository.save(partner);
    }

    public Delivery pickupOrder(Long deliveryId) {

        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(() -> new RuntimeException("Delivery Not Found"));
        delivery.setStatus("PICKED_UP");
        delivery.setPickedUpAt(LocalDateTime.now().plusMinutes(30));
        return deliveryRepository.save(delivery);
    }

    public Delivery deliveredOrder(Long deliveryId) {

        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(() -> new RuntimeException("Delivery Not Found"));
        delivery.setStatus("DELIVERED");
        delivery.setDeliveredAt(LocalDateTime.now());
        Delivery savedDelivery = deliveryRepository.save(delivery);
        DeliveryPartner partner = getPartner(delivery.getDeliveryPartnerId());
        partner.setAvailable(true);
        delivery.setDeliveredAt(LocalDateTime.now().plusHours(1));
        deliveryPartnerRepository.save(partner);
        return savedDelivery;
    }

    public DeliveryPartner updateLocation(Long partnerId, LocationRequest request) {
        DeliveryPartner partner = getPartner(partnerId);
        partner.setLatitude(request.getLatitude());
        partner.setLongitude(request.getLongitude());
        return deliveryPartnerRepository.save(partner);
    }

    public Delivery trackDelivery(Long deliveryId) {
        return deliveryRepository.findById(deliveryId).orElseThrow(() -> new RuntimeException("Delivery Not Found"));
    }

    @Transactional
    public Delivery assignDelivery(DeliveryAssignRequest request) {
        DeliveryPartner partner = deliveryPartnerRepository
                        .findFirstByAvailableTrue()
                        .orElseThrow(() -> new RuntimeException("No Delivery Partner Available"));
        partner.setAvailable(false);
        deliveryPartnerRepository.save(partner);
        Delivery delivery = new Delivery();
        delivery.setOrderId(request.getOrderId());
        delivery.setDeliveryPartnerId(partner.getId());
        delivery.setStatus("ASSIGNED");
        delivery.setAssignedAt(LocalDateTime.now());
        return deliveryRepository.save(delivery);
    }

}
