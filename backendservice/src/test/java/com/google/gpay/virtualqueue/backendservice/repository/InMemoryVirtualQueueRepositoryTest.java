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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.gpay.virtualqueue.backendservice.model.Shop;
import com.google.gpay.virtualqueue.backendservice.model.Token;
import com.google.gpay.virtualqueue.backendservice.model.Shop.ShopStatus;
import com.google.gpay.virtualqueue.backendservice.model.Token.Status;
import com.google.gpay.virtualqueue.backendservice.proto.CreateShopRequest;
import com.google.gpay.virtualqueue.backendservice.proto.UpdateTokenStatusRequest;
import com.google.gpay.virtualqueue.backendservice.proto.UpdateTokenStatusResponse;

import static com.google.gpay.virtualqueue.backendservice.repository.InMemoryVirtualQueueRepository.WAITING_TIME_MINS;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = InMemoryVirtualQueueRepository.class)
public class InMemoryVirtualQueueRepositoryTest {
    InMemoryVirtualQueueRepository inMemoryVirtualQueueRepository = new InMemoryVirtualQueueRepository();

    private static final String SHOP_OWNER_ID = "uid";
    private static final String SHOP_NAME = "shopName";
    private static final String SHOP_ADDRESS = "address";
    private static final String PHONE_NUMBER = "+919012192800";
    private static final String SHOP_TYPE = "shopType";

    @BeforeEach
    public void setUp() {
        inMemoryVirtualQueueRepository.getTokenMap().clear();
        inMemoryVirtualQueueRepository.getShopMap().clear();
        inMemoryVirtualQueueRepository.getShopIdToListOfTokensMap().clear();
        inMemoryVirtualQueueRepository.getShopIdToLastAllotedTokenMap().clear();
        inMemoryVirtualQueueRepository.getShopOwnerMap().clear();
    }

    @Test
    public void testCreateShop_success() throws Exception {
        // Arrange.
        CreateShopRequest shop = new CreateShopRequest(SHOP_OWNER_ID, SHOP_NAME, SHOP_ADDRESS, PHONE_NUMBER, SHOP_TYPE);

        // Act.
        UUID shopId = inMemoryVirtualQueueRepository.createShop(shop);

        // Assert.
        assertEquals("Size of shopMap", 1, inMemoryVirtualQueueRepository.getShopMap().size());
        assertEquals("Shop Owner Id ", SHOP_OWNER_ID,
                inMemoryVirtualQueueRepository.getShopMap().get(shopId).getShopOwnerId());
        assertEquals("Shop Name ", SHOP_NAME, inMemoryVirtualQueueRepository.getShopMap().get(shopId).getShopName());
        assertEquals("Shop Address ", SHOP_ADDRESS,
                inMemoryVirtualQueueRepository.getShopMap().get(shopId).getAddress());
        assertEquals("Shop Phone Number ", PHONE_NUMBER,
                inMemoryVirtualQueueRepository.getShopMap().get(shopId).getPhoneNumber());
        assertEquals("Shop Type ", SHOP_TYPE, inMemoryVirtualQueueRepository.getShopMap().get(shopId).getShopType());
    }

    @Test
    public void testGetWaitingTime() throws Exception {
        long waitingTime = inMemoryVirtualQueueRepository.getWaitingTime();

        assertEquals("Waiting Time per customer is", WAITING_TIME_MINS, waitingTime);
    }

    @Test
    public void testGetNewToken_success() throws Exception {
        // Arrange.
        UUID shopId = addShopToRepository(SHOP_OWNER_ID, SHOP_NAME, SHOP_ADDRESS, PHONE_NUMBER, SHOP_TYPE);
        addTokenListToShop(shopId);

        // Act.
        Token newToken = inMemoryVirtualQueueRepository.getNewToken(shopId);

        // Assert.
        UUID tokenId = newToken.getTokenId();
        int expectedTokenMapSize = 1;
        Integer expectedTokenNumber = 1;
        UUID expectedShopId = inMemoryVirtualQueueRepository.getTokenMap().get(tokenId).getShopId();

        assertEquals("Size of tokenMap", inMemoryVirtualQueueRepository.getTokenMap().size(), expectedTokenMapSize);
        assertEquals("Token number is", inMemoryVirtualQueueRepository.getTokenMap().get(tokenId).getTokenNumber(),
                expectedTokenNumber);
        assertEquals("Shop id is", shopId, expectedShopId);
    }

    @Test
    public void testGetTokensForShop_success() throws Exception {
        // Arrange.
        UUID shopId = addShopToRepository(SHOP_OWNER_ID, SHOP_NAME, SHOP_ADDRESS, PHONE_NUMBER, SHOP_TYPE);
        addTokenListToShop(shopId);

        // Act.
        List<Token> getTokensResponseList = inMemoryVirtualQueueRepository.getTokens(shopId);

        // Assert.
        assertEquals("Size of token list", getTokensResponseList.size(),
                inMemoryVirtualQueueRepository.getShopIdToListOfTokensMap().get(shopId).stream().filter(token -> token.getStatus() == Status.ACTIVE).count());
    }
    
    @Test
    public void testUpdateToken_success() throws Exception {
        // Arrange.
        UUID tokenId = addTokenToTokenMap();
        UpdateTokenStatusRequest updateTokenStatusRequest = new UpdateTokenStatusRequest(tokenId, Status.CANCELLED_BY_CUSTOMER);

        // Act.
        UpdateTokenStatusResponse updateTokenStatusResponse = inMemoryVirtualQueueRepository.updateToken(updateTokenStatusRequest);

        // Assert.
        assertEquals("token map status is", inMemoryVirtualQueueRepository.getTokenMap().get(tokenId).getStatus(), updateTokenStatusResponse.getUpdatedToken().getStatus());
    }

    @Test
    public void testGetShop_success() {
        // Arrange.
        UUID shopId = addShopToRepository(SHOP_OWNER_ID, SHOP_NAME, SHOP_ADDRESS, PHONE_NUMBER, SHOP_TYPE);
        
        // Act.
        Shop newShop = inMemoryVirtualQueueRepository.getShop(shopId);

        // Assert.
        assertEquals("Shop Owner Id ", SHOP_OWNER_ID, newShop.getShopOwnerId());
        assertEquals("Shop Name ", SHOP_NAME, newShop.getShopName());
        assertEquals("Shop Address ", SHOP_ADDRESS, newShop.getAddress());
        assertEquals("Shop Phone Number ", PHONE_NUMBER, newShop.getPhoneNumber());
        assertEquals("Shop Type ", SHOP_TYPE, newShop.getShopType());
    }

    @Test
    public void testCustomersInQueue_success() {
        // Arrange.
        UUID shopId = addShopToRepository(SHOP_OWNER_ID, SHOP_NAME, SHOP_ADDRESS, PHONE_NUMBER, SHOP_TYPE);
        addTokenListToShop(shopId);

        // Act.
        long customersInQueue = inMemoryVirtualQueueRepository.getCustomersInQueue(shopId);

        // Assert.
        assertEquals("Customers in Queue is ", inMemoryVirtualQueueRepository.getShopIdToListOfTokensMap().get(shopId).stream().filter(token -> token.getStatus() == Status.ACTIVE).count(), customersInQueue);
    }

    private UUID addTokenToTokenMap() {
        UUID tokenId = UUID.randomUUID();
        UUID shopId = UUID.randomUUID();
        Token token = new Token(tokenId, shopId, 1);
        token.setStatus(Status.ACTIVE);
        inMemoryVirtualQueueRepository.getTokenMap().put(tokenId, token);
        return tokenId;
    }

    private UUID addShopToRepository(String shopOwnerId, String shopName, String shopAddress, String phoneNumber, String ShopType) {
        Shop shop = new Shop(shopOwnerId, shopName, shopAddress, phoneNumber, ShopType);
        shop.setStatus(ShopStatus.ACTIVE);
        UUID shopId = UUID.randomUUID();
        inMemoryVirtualQueueRepository.getShopMap().put(shopId, shop);
        inMemoryVirtualQueueRepository.getShopIdToLastAllotedTokenMap().put(shopId, new AtomicInteger(0));
        return shopId;
    }

    private void addTokenListToShop(UUID shopId) {
        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token(UUID.randomUUID(), shopId, 1, Status.ACTIVE));
        tokens.add(new Token(UUID.randomUUID(), shopId, 1, Status.EXPIRED));
        tokens.add(new Token(UUID.randomUUID(), shopId, 1, Status.CANCELLED_BY_CUSTOMER));
        inMemoryVirtualQueueRepository.getShopIdToListOfTokensMap().put(shopId, tokens);
    }
}
