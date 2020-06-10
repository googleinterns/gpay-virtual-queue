package com.google.gpay.virtualqueue.backendservice.proto;

import lombok.Data;

@Data
public class CreateShopRequest {
    private String shopOwnerUid;
    private String shopName;
    private String address;
    private String phoneNumber;
    private String shopType;
}
