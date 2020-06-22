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

package com.google.gpay.virtualqueue.backendservice.controller;

import java.util.UUID;

import com.google.gpay.virtualqueue.backendservice.proto.CreateShopRequest;
import com.google.gpay.virtualqueue.backendservice.proto.CreateShopResponse;
import com.google.gpay.virtualqueue.backendservice.proto.GetWaitingTimeResponse;
import com.google.gpay.virtualqueue.backendservice.proto.GetAllShopsResponse;
import com.google.gpay.virtualqueue.backendservice.proto.GetNewTokenResponse;
import com.google.gpay.virtualqueue.backendservice.proto.GetShopResponse;
import com.google.gpay.virtualqueue.backendservice.proto.UpdateTokenStatusResponse;
import com.google.gpay.virtualqueue.backendservice.proto.GetShopsByShopOwnerResponse;
import com.google.gpay.virtualqueue.backendservice.proto.GetTokensResponse;
import com.google.gpay.virtualqueue.backendservice.proto.UpdateShopStatusRequest;
import com.google.gpay.virtualqueue.backendservice.proto.UpdateShopStatusResponse;
import com.google.gpay.virtualqueue.backendservice.proto.UpdateTokenStatusRequest;
import com.google.gpay.virtualqueue.backendservice.service.VirtualQueueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class VirtualQueueController {
    @Autowired
    public VirtualQueueService virtualQueueService;

    @PostMapping("/shop")
    public CreateShopResponse addShop(@RequestBody CreateShopRequest createShopRequest) {
        return virtualQueueService.createShop(createShopRequest);
    }

    @GetMapping("/waitingtime")
    public GetWaitingTimeResponse getWaitingTime() {
        return virtualQueueService.getWaitingTime();
    }

    @GetMapping("/token/{shopId}")
    public GetTokensResponse getTokens(@PathVariable UUID shopId) {
        return virtualQueueService.getTokens(shopId);
    }

    @GetMapping("/shop/{shopOwnerId}")
    public GetShopsByShopOwnerResponse getShopsByShopOwner(@PathVariable String shopOwnerId) {
        return virtualQueueService.getShopsByShopOwner(shopOwnerId);
    }

    @GetMapping("/shops")
    public GetAllShopsResponse getAllShops() {
        return virtualQueueService.getAllShops();
    }

    @PutMapping("/token")
    public UpdateTokenStatusResponse updateToken(@RequestBody UpdateTokenStatusRequest updateTokenStatusRequest) {
        return virtualQueueService.updateToken(updateTokenStatusRequest);
    }

    @PutMapping("/shop")
    public UpdateShopStatusResponse updateShop(@RequestBody UpdateShopStatusRequest updateShopStatusRequest) {
        return virtualQueueService.updateShop(updateShopStatusRequest);
    }

    @GetMapping("/shops/{shopId}")
    public GetShopResponse getShop(@PathVariable UUID shopId) {
        return virtualQueueService.getShop(shopId);
    }

    @PostMapping("/tokens/{shopId}")
    public GetNewTokenResponse getNewToken(@PathVariable UUID shopId) {
        return virtualQueueService.getNewToken(shopId);
    }

}
