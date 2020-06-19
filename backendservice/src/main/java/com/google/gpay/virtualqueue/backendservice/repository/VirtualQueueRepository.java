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

package com.google.gpay.virtualqueue.backendservice.repository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.gpay.virtualqueue.backendservice.model.Shop;
import com.google.gpay.virtualqueue.backendservice.model.Token;
import com.google.gpay.virtualqueue.backendservice.proto.CreateShopRequest;
import com.google.gpay.virtualqueue.backendservice.proto.UpdateTokenStatusResponse;
import com.google.gpay.virtualqueue.backendservice.proto.GetShopsByShopOwnerResponse;
import com.google.gpay.virtualqueue.backendservice.proto.UpdateShopStatusRequest;
import com.google.gpay.virtualqueue.backendservice.proto.UpdateShopStatusResponse;
import com.google.gpay.virtualqueue.backendservice.proto.UpdateTokenStatusRequest;

public interface VirtualQueueRepository {

    public UUID createShop(CreateShopRequest createShopRequest);

    public List<Shop> getAllShops();

    public List<Token> getTokens(UUID shopId);

    public Token getNewToken(UUID shopId);

    public GetShopsByShopOwnerResponse getShopsByShopOwner(String shopOwnerId);

    public UpdateTokenStatusResponse updateToken(UpdateTokenStatusRequest updateTokenStatusRequest);

    public UpdateShopStatusResponse updateShop(UpdateShopStatusRequest updateShopStatusRequest);

    public Shop getShop(UUID shopId);

    public long getCustomersInQueue(UUID shopId);
}
