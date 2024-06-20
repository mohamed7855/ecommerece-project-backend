package com.ecommerce.dto;

import lombok.Data;

@Data
public class PurchaseResponse {

    // @Data will generate constructor for final fields
    private final String orderTrackingNumber;
}
