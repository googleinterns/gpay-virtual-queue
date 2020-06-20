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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.stream.Collectors;

import com.google.gpay.virtualqueue.backendservice.model.Shop;
import com.google.gpay.virtualqueue.backendservice.model.Shop.ShopStatus;
import com.google.gpay.virtualqueue.backendservice.model.Token.Status;
import com.google.gpay.virtualqueue.backendservice.model.ShopOwner;
import com.google.gpay.virtualqueue.backendservice.model.Token;
import com.google.gpay.virtualqueue.backendservice.proto.CreateShopRequest;
import com.google.gpay.virtualqueue.backendservice.proto.UpdateTokenStatusResponse;
import com.google.gpay.virtualqueue.backendservice.proto.GetShopsByShopOwnerResponse;
import com.google.gpay.virtualqueue.backendservice.proto.UpdateShopStatusRequest;
import com.google.gpay.virtualqueue.backendservice.proto.UpdateShopStatusResponse;
import com.google.gpay.virtualqueue.backendservice.proto.UpdateTokenStatusRequest;

import org.springframework.stereotype.Repository;

import lombok.Getter;

@Repository
@Getter
public class InMemoryVirtualQueueRepository implements VirtualQueueRepository {
	private Map<UUID, Token> tokenMap = new HashMap<>();
	private Map<UUID, Shop> shopMap = new HashMap<>();
	private Map<UUID, List<Token>> shopIdToListOfTokensMap = new HashMap<>();
	private Map<UUID, AtomicInteger> shopIdToLastAllotedTokenMap = new HashMap<>();
	private Map<String, ShopOwner> shopOwnerMap = new HashMap<>();

	public UUID createShop(CreateShopRequest createShopRequest) {
		Shop newShop = new Shop();
		newShop.setShopOwnerId(createShopRequest.getShopOwnerId());
		newShop.setShopName(createShopRequest.getShopName());
		newShop.setAddress(createShopRequest.getAddress());
		newShop.setPhoneNumber(createShopRequest.getPhoneNumber());
		newShop.setShopType(createShopRequest.getShopType());
		newShop.setStatus(ShopStatus.ACTIVE);

		UUID uuid = UUID.randomUUID();
		newShop.setShopId(uuid);

		shopMap.put(uuid, newShop);
		shopIdToLastAllotedTokenMap.put(uuid, new AtomicInteger(0));
		shopIdToListOfTokensMap.put(uuid, new ArrayList<>());

		return newShop.getShopId();
	}

	public List<Shop> getAllShops() {
		return shopMap.entrySet().stream().filter(shop -> ShopStatus.ACTIVE.equals(shop.getValue().getStatus()))
				.map(Map.Entry::getValue).collect(Collectors.toList());
	}

	public List<Token> getTokens(UUID shopId) {
		if (shopMap.get(shopId).getStatus() == ShopStatus.ACTIVE) {
			return shopIdToListOfTokensMap.get(shopId).stream().filter(token -> token.getStatus() == Status.ACTIVE)
					.collect(Collectors.toList());
		}

		// TODO(b/158975796): Simplify logging across all backend APIs.
		Logger.getLogger(InMemoryVirtualQueueRepository.class.getName()).log(Level.SEVERE,
				"Tried to fetch tokens of a shop which is not in the ACTIVE state.");
		return new ArrayList<>();
	}

	public Token getNewToken(UUID shopId) {
		if (shopMap.get(shopId).getStatus() == ShopStatus.ACTIVE) {
			Integer newTokenNumber = shopIdToLastAllotedTokenMap.get(shopId).incrementAndGet();
			UUID uuid = UUID.randomUUID();
			Token newToken = new Token(uuid, shopId, newTokenNumber);
			newToken.setStatus(Token.Status.ACTIVE);
			tokenMap.put(uuid, newToken);
			shopIdToListOfTokensMap.get(shopId).add(newToken);
			shopIdToListOfTokensMap.put(shopId, shopIdToListOfTokensMap.get(shopId));
			return newToken;
		}
		// TODO: Throw exception here.
		Logger.getLogger(InMemoryVirtualQueueRepository.class.getName()).log(Level.SEVERE,
				"Tried to get new token of a shop which is not in the ACTIVE state.");
		return new Token();
	}

	// Method returns all shops keeping in mind the feature of restoring shops later
	// on.
	public GetShopsByShopOwnerResponse getShopsByShopOwner(String shopOwnerId) {
		List<Shop> shops = shopMap.entrySet().stream()
				.filter(map -> map.getValue().getShopOwnerId().equals(shopOwnerId)).map(map -> map.getValue())
				.collect(Collectors.toList());
		return new GetShopsByShopOwnerResponse(shopOwnerMap.get(shopOwnerId), shops);
	}

	public UpdateTokenStatusResponse updateToken(UpdateTokenStatusRequest updateTokenStatusRequest) {
		tokenMap.get(updateTokenStatusRequest.getTokenId()).setStatus(updateTokenStatusRequest.getTokenStatus());
		return new UpdateTokenStatusResponse(tokenMap.get(updateTokenStatusRequest.getTokenId()));
	}

	public UpdateShopStatusResponse updateShop(UpdateShopStatusRequest updateShopStatusRequest) {
		shopMap.get(updateShopStatusRequest.getShopId()).setStatus(updateShopStatusRequest.getShopStatus());
		return new UpdateShopStatusResponse(shopMap.get(updateShopStatusRequest.getShopId()));
	}

	public Shop getShop(UUID shopId) {
		if (shopMap.get(shopId).getStatus() == ShopStatus.ACTIVE) {
			return shopMap.get(shopId);
		}
		// TODO: Throw exception here.
		Logger.getLogger(InMemoryVirtualQueueRepository.class.getName()).log(Level.SEVERE,
				"Tried to fetch information of a shop which is not in the ACTIVE state.");
		return new Shop();
	}

	public long getCustomersInQueue(UUID shopId) {
		List<Token> tokenList = shopIdToListOfTokensMap.get(shopId);
		return tokenList.stream().filter(token -> token.getStatus() == Status.ACTIVE).count();
	}
}
