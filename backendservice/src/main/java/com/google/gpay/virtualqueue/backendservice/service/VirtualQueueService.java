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

package com.google.gpay.virtualqueue.backendservice.service;

import java.util.UUID;

import com.google.gpay.virtualqueue.backendservice.proto.CreateShopRequest;
import com.google.gpay.virtualqueue.backendservice.proto.CreateShopResponse;
import com.google.gpay.virtualqueue.backendservice.proto.GetAllShopsResponse;
import com.google.gpay.virtualqueue.backendservice.proto.GetShopResponse;
import com.google.gpay.virtualqueue.backendservice.proto.UpdateTokenStatusResponse;
import com.google.gpay.virtualqueue.backendservice.proto.GetShopsByShopOwnerResponse;
import com.google.gpay.virtualqueue.backendservice.proto.GetTokensResponse;
import com.google.gpay.virtualqueue.backendservice.proto.UpdateShopStatusRequest;
import com.google.gpay.virtualqueue.backendservice.proto.UpdateShopStatusResponse;
import com.google.gpay.virtualqueue.backendservice.proto.UpdateTokenStatusRequest;
import com.google.gpay.virtualqueue.backendservice.repository.VirtualQueueRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VirtualQueueService {
	@Autowired
	VirtualQueueRepository virtualQueueRepository;

	public CreateShopResponse createShop(CreateShopRequest createShopRequest) {
		CreateShopResponse createShopResponse = new CreateShopResponse();
		createShopResponse.setShopId(virtualQueueRepository.createShop(createShopRequest));
		return createShopResponse;
	}

	public GetTokensResponse getTokens(UUID shopId) {
		return new GetTokensResponse(shopId, virtualQueueRepository.getTokens(shopId));
	}

	public GetAllShopsResponse getAllShops() {
		return new GetAllShopsResponse(virtualQueueRepository.getAllShops());
	}

	public GetShopsByShopOwnerResponse getShopsByShopOwner(String shopOwnerId) {
		return virtualQueueRepository.getShopsByShopOwner(shopOwnerId);
	}

	public UpdateTokenStatusResponse updateToken(UpdateTokenStatusRequest updateTokenStatusRequest) {
		return virtualQueueRepository.updateToken(updateTokenStatusRequest);
	}

	public UpdateShopStatusResponse updateShop(UpdateShopStatusRequest updateShopStatusRequest) {
		return virtualQueueRepository.updateShop(updateShopStatusRequest);
	}

	public GetShopResponse getShopByShopId(UUID shopId) {
		return new GetShopResponse(virtualQueueRepository.getShopByShopId(shopId),
				virtualQueueRepository.getPeopleInQueue(shopId));
	}
}
