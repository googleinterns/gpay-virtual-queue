package com.google.gpay.virtualqueue.backendservice.proto;

import com.google.gpay.virtualqueue.backendservice.model.Shop;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetShopResponse {
    private Shop shop;
    private Integer customersInQueue;
}
