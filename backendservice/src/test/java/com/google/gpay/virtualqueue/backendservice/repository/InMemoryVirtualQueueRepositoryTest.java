package com.google.gpay.virtualqueue.backendservice.repository;

import static org.junit.Assert.assertEquals;
import java.util.UUID;

import com.google.gpay.virtualqueue.backendservice.proto.CreateShopRequest;

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
        CreateShopRequest shop = new CreateShopRequest("uid", "shopName", "address", "+919012192800", "type");
        UUID shopId = inMemoryVirtualQueueRepository.createShop(shop);
        int size = inMemoryVirtualQueueRepository.getShopMap().size();
        assertEquals("size ", 1, size);
        assertEquals("Shop Owner Id ", "uid", inMemoryVirtualQueueRepository.getShopMap().get(shopId).getShopOwnerId());
        assertEquals("Shop Name ", "shopName", inMemoryVirtualQueueRepository.getShopMap().get(shopId).getShopName());
        assertEquals("Shop Address ", "address", inMemoryVirtualQueueRepository.getShopMap().get(shopId).getAddress());
        assertEquals("Shop Phone Number ", "+919012192800", inMemoryVirtualQueueRepository.getShopMap().get(shopId).getPhoneNumber());
        assertEquals("Shop Type ", "type", inMemoryVirtualQueueRepository.getShopMap().get(shopId).getShopType());
    }
}

