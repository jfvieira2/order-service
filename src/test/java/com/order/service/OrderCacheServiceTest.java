// File: src/test/java/com/order/service/OrderCacheServiceTest.java
package com.order.service;

import com.order.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.math.BigDecimal;
import java.time.Duration;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderCacheServiceTest {

    @InjectMocks
    private OrderCacheService orderCacheService;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1L);
        order.setOrderId("123ABC");
        order.setTotalValue(new BigDecimal("100.00"));

        // Simula o comportamento do RedisTemplate para evitar NullPointerException
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void deveArmazenarPedidoNoCache() {
        orderCacheService.cacheOrder(order);

        verify(valueOperations, times(1)).set(
            eq("pedido:" + order.getId()), 
            eq(order),                     
            eq(Duration.ofMinutes(30))   
        );
    }

    @Test
    void deveBuscarPedidoDoCache() {
        when(valueOperations.get("pedido:" + order.getId())).thenReturn(order);

        Order cachedOrder = orderCacheService.getCachedOrder(order.getId());

        assertNotNull(cachedOrder);
        assertEquals(order.getOrderId(), cachedOrder.getOrderId());
    }
}
