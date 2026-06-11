package com.dhaanesh.delivery.service.Delivery.Partner.Service.Controller;

import com.dhaanesh.delivery.service.Delivery.Partner.Service.Entity.Delivery;
import com.dhaanesh.delivery.service.Delivery.Partner.Service.Entity.DeliveryAssignRequest;
import com.dhaanesh.delivery.service.Delivery.Partner.Service.Entity.DeliveryPartner;
import com.dhaanesh.delivery.service.Delivery.Partner.Service.Entity.LocationRequest;
import com.dhaanesh.delivery.service.Delivery.Partner.Service.Service.DeliveryPartnerService;
import org.springframework.web.bind.annotation.*;

@RestController
public class DeliveryPartnerController {

    private final DeliveryPartnerService deliveryPartnerService;

    public DeliveryPartnerController(DeliveryPartnerService deliveryPartnerService) {
        this.deliveryPartnerService = deliveryPartnerService;
    }

    @PostMapping("/delivery-partners")
    public DeliveryPartner createPartner(@RequestBody DeliveryPartner partner) {

        return deliveryPartnerService.createPartner(partner);
    }

    @GetMapping("/delivery-partners/{id}")
    public DeliveryPartner getPartner(@PathVariable Long id) {

        return deliveryPartnerService.getPartner(id);
    }

    @PutMapping("/delivery-partners/{id}")
    public DeliveryPartner updatePartner(@PathVariable Long id, @RequestBody DeliveryPartner partner) {

        return deliveryPartnerService.updatePartner(id, partner);
    }

//    @PostMapping("/deliveries/assign")
//    public Delivery assignDelivery(@RequestBody DeliveryAssignRequest request) {
//
//        return deliveryPartnerService.assignDelivery(request);
//    }

    @PutMapping("/deliveries/{id}/pickup")
    public Delivery pickupOrder(@PathVariable Long id) {

        return deliveryPartnerService.pickupOrder(id);
    }

    @PutMapping("/deliveries/{id}/delivered")
    public Delivery deliveredOrder(@PathVariable Long id) {

        return deliveryPartnerService.deliveredOrder(id);
    }

    @PutMapping("/delivery-partners/{id}/location")
    public DeliveryPartner updateLocation(@PathVariable Long id, @RequestBody LocationRequest request) {

        return deliveryPartnerService.updateLocation(id, request);
    }

    @GetMapping("/deliveries/{id}/track")
    public Delivery trackDelivery(@PathVariable Long id) {

        return deliveryPartnerService.trackDelivery(id);
    }
}
