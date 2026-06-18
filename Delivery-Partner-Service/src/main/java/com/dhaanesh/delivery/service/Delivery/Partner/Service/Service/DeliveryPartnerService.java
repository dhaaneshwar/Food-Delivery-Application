package com.dhaanesh.delivery.service.Delivery.Partner.Service.Service;

import com.dhaanesh.delivery.service.Delivery.Partner.Service.Entity.Delivery;
import com.dhaanesh.delivery.service.Delivery.Partner.Service.Entity.DeliveryAssignRequest;
import com.dhaanesh.delivery.service.Delivery.Partner.Service.Entity.DeliveryPartner;
import com.dhaanesh.delivery.service.Delivery.Partner.Service.Entity.LocationRequest;
import com.dhaanesh.delivery.service.Delivery.Partner.Service.Repository.DeliveryPartnerRepository;
import com.dhaanesh.delivery.service.Delivery.Partner.Service.Repository.DeliveryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dhaanesh.delivery.service.Delivery.Partner.Service.Exception.DeliveryNotFoundException;
import com.dhaanesh.delivery.service.Delivery.Partner.Service.Exception.DeliveryPartnerNotFoundException;

import java.time.LocalDateTime;

@Service
public class DeliveryPartnerService {

    private final DeliveryPartnerRepository deliveryPartnerRepository;

    private final DeliveryRepository deliveryRepository;
    private static final Logger log = LoggerFactory.getLogger(DeliveryPartnerService.class);

    public DeliveryPartnerService(DeliveryPartnerRepository deliveryPartnerRepository, DeliveryRepository deliveryRepository) {
        this.deliveryPartnerRepository = deliveryPartnerRepository;
        this.deliveryRepository = deliveryRepository;
    }

    public DeliveryPartner createPartner(DeliveryPartner partner) {
        log.info("Creating delivery partner: {}", partner);
        partner.setAvailable(true);
        DeliveryPartner saved = deliveryPartnerRepository.save(partner);
        log.debug("Created delivery partner with id={}", saved.getId());
        return saved;
    }

    public DeliveryPartner getPartner(Long id) {
        log.debug("Fetching delivery partner with id={}", id);
        return deliveryPartnerRepository.findById(id).orElseThrow(() -> {
            log.warn("Delivery partner not found id={}", id);
            return new DeliveryPartnerNotFoundException("Partner Not Found with id=" + id);
        });
    }

    public DeliveryPartner updatePartner(Long id, DeliveryPartner updatedPartner) {
        log.info("Updating delivery partner id={}", id);
        DeliveryPartner partner = getPartner(id);
        partner.setName(updatedPartner.getName());
        partner.setPhoneNumber(updatedPartner.getPhoneNumber());
        partner.setVehicleNumber(updatedPartner.getVehicleNumber());
        DeliveryPartner saved = deliveryPartnerRepository.save(partner);
        log.debug("Updated delivery partner id={}", saved.getId());
        return saved;
    }

    public Delivery pickupOrder(Long deliveryId) {
        log.info("Picking up delivery id={}", deliveryId);
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(() -> {
            log.warn("Delivery not found for pickup id={}", deliveryId);
            return new DeliveryNotFoundException("Delivery Not Found with id=" + deliveryId);
        });
        delivery.setStatus("PICKED_UP");
        delivery.setPickedUpAt(LocalDateTime.now().plusMinutes(30));
        Delivery saved = deliveryRepository.save(delivery);
        log.debug("Delivery id={} marked PICKED_UP", saved.getId());
        return saved;
    }

    public Delivery deliveredOrder(Long deliveryId) {
        log.info("Marking delivery id={} as DELIVERED", deliveryId);
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(() -> {
            log.warn("Delivery not found for delivered operation id={}", deliveryId);
            return new DeliveryNotFoundException("Delivery Not Found with id=" + deliveryId);
        });
        delivery.setStatus("DELIVERED");
        delivery.setDeliveredAt(LocalDateTime.now());
        Delivery savedDelivery = deliveryRepository.save(delivery);
        DeliveryPartner partner = getPartner(delivery.getDeliveryPartnerId());
        partner.setAvailable(true);
        delivery.setDeliveredAt(LocalDateTime.now().plusHours(1));
        deliveryPartnerRepository.save(partner);
        log.debug("Delivery id={} delivered and partner id={} set available", deliveryId, partner.getId());
        return savedDelivery;
    }

    public DeliveryPartner updateLocation(Long partnerId, LocationRequest request) {
        log.debug("Updating location for partner id={} lat={} lon={}", partnerId, request.getLatitude(), request.getLongitude());
        DeliveryPartner partner = getPartner(partnerId);
        partner.setLatitude(request.getLatitude());
        partner.setLongitude(request.getLongitude());
        DeliveryPartner saved = deliveryPartnerRepository.save(partner);
        log.trace("Updated location saved for partner id={}", saved.getId());
        return saved;
    }

    public Delivery trackDelivery(Long deliveryId) {
        log.debug("Tracking delivery id={}", deliveryId);
        return deliveryRepository.findById(deliveryId).orElseThrow(() -> {
            log.warn("Delivery not found for track id={}", deliveryId);
            return new DeliveryNotFoundException("Delivery Not Found with id=" + deliveryId);
        });
    }

    @Transactional
    public Delivery assignDelivery(DeliveryAssignRequest request) {
        log.info("Assigning delivery for orderId={}", request.getOrderId());
        DeliveryPartner partner = deliveryPartnerRepository
                        .findFirstByAvailableTrue()
                        .orElseThrow(() -> {
                            log.warn("No available delivery partner for orderId={}", request.getOrderId());
                            return new DeliveryPartnerNotFoundException("No Delivery Partner Available for orderId=" + request.getOrderId());
                        });
        partner.setAvailable(false);
        deliveryPartnerRepository.save(partner);
        log.debug("Assigned partner id={} to orderId={}", partner.getId(), request.getOrderId());
        Delivery delivery = new Delivery();
        delivery.setOrderId(request.getOrderId());
        delivery.setDeliveryPartnerId(partner.getId());
        delivery.setStatus("ASSIGNED");
        delivery.setAssignedAt(LocalDateTime.now());
        Delivery saved = deliveryRepository.save(delivery);
        log.debug("Created delivery id={} for orderId={} with partnerId={}", saved.getId(), request.getOrderId(), partner.getId());
        return saved;
    }

}
