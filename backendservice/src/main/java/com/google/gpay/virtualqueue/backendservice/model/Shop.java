/*
Copyright 2020 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.google.gpay.virtualqueue.backendservice.model;

import java.util.UUID;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class Shop {
    private UUID shopId;
    private String shopOwnerId;
    private String shopName;
    private String address;
    private String phoneNumber;
    private String shopType;

    public enum ShopStatus {
        STATUS_UNKNOWN, ACTIVE, INACTIVE, DELETED;
    }
    private ShopStatus status;

    public Shop(String shopOwnerId, String shopName, String address, String phoneNumber,
            String shopType) {
        super();
        this.shopOwnerId = shopOwnerId;
        this.shopName = shopName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.shopType = shopType;
    }
}
