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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.gpay.virtualqueue.backendservice.model.Shop;
import com.google.gpay.virtualqueue.backendservice.model.ShopOwner;
import com.google.gpay.virtualqueue.backendservice.model.Token;
import com.google.gpay.virtualqueue.backendservice.proto.CreateShopRequest;

import org.springframework.stereotype.Repository;

import lombok.Getter;

@Repository
@Getter
public class InMemoryShopRepository implements VirtualQueueRepository {
	private Map<UUID, Token> tokenMap = new HashMap<>();
	private Map<UUID, Shop> shopMap = new HashMap<>();
	private Map<UUID, List<Token>> tokens = new HashMap<>();
	private Map<UUID, AtomicInteger> lastAllottedToken = new HashMap<>();
	private Map<String, ShopOwner> shopOwnerMap = new HashMap<>();

	public UUID createShop(CreateShopRequest shop) {
		Shop newShop = new Shop();
		newShop.setShopOwnerUid(shop.getShopOwnerUid());
		newShop.setShopName(shop.getShopName());
		newShop.setAddress(shop.getAddress());
		newShop.setPhoneNumber(shop.getPhoneNumber());
		newShop.setShopType(shop.getShopType());

		UUID uuid = UUID.randomUUID();
		newShop.setShopId(uuid);

		shopMap.put(uuid, newShop);

		AtomicInteger lastToken = new AtomicInteger(0);
		lastAllottedToken.put(uuid, lastToken);
		
		return newShop.getShopId();
	}
}
