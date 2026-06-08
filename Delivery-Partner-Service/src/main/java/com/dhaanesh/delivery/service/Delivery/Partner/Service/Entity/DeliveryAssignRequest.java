package com.dhaanesh.delivery.service.Delivery.Partner.Service.Entity;

import lombok.Data;

@Data
public class DeliveryAssignRequest {
    private Long orderId;

    private Long deliveryPartnerId;
}
