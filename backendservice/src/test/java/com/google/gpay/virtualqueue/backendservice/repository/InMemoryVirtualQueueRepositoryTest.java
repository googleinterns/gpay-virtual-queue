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
import java.util.UUID;

import com.google.gpay.virtualqueue.backendservice.proto.CreateShopRequest;
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
    public void setUp(){
        inMemoryVirtualQueueRepository.getTokenMap().clear();
        inMemoryVirtualQueueRepository.getShopMap().clear();
        inMemoryVirtualQueueRepository.getShopIdToListOfTokensMap().clear();
        inMemoryVirtualQueueRepository.getShopIdToLastAllotedTokenMap().clear();
        inMemoryVirtualQueueRepository.getShopOwnerMap().clear();
    }

    @Test
    public void testCreateShop() throws Exception {
        CreateShopRequest shop = new CreateShopRequest(SHOP_OWNER_ID, SHOP_NAME, SHOP_ADDRESS, PHONE_NUMBER, SHOP_TYPE);

        UUID shopId = inMemoryVirtualQueueRepository.createShop(shop);

        assertEquals("Size of shopMap", 1, inMemoryVirtualQueueRepository.getShopMap().size());
        assertEquals("Shop Owner Id ", SHOP_OWNER_ID, inMemoryVirtualQueueRepository.getShopMap().get(shopId).getShopOwnerId());
        assertEquals("Shop Name ", SHOP_NAME, inMemoryVirtualQueueRepository.getShopMap().get(shopId).getShopName());
        assertEquals("Shop Address ", SHOP_ADDRESS, inMemoryVirtualQueueRepository.getShopMap().get(shopId).getAddress());
        assertEquals("Shop Phone Number ", PHONE_NUMBER, inMemoryVirtualQueueRepository.getShopMap().get(shopId).getPhoneNumber());
        assertEquals("Shop Type ", SHOP_TYPE, inMemoryVirtualQueueRepository.getShopMap().get(shopId).getShopType());
    }
    
    @Test
    public void testGetWaitingTime() throws Exception {
        long waitingTime = inMemoryVirtualQueueRepository.getWaitingTime();
        
        assertEquals("Waiting Time per customer is", WAITING_TIME_MINS, waitingTime);
    }
}