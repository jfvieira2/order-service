// File: src/test/java/com/order/service/OrderConsumerServiceTest.java
package com.order.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.order.model.Order;

@ExtendWith(MockitoExtension.class)
class OrderConsumerServiceTest {

    @InjectMocks
    private OrderConsumerService orderConsumerService;

    @Mock
    private OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1L);
        order.setOrderId("123ABC");
    }

    @Test
    void deveProcessarPedidoAoConsumirDoKafka() {
        orderConsumerService.processOrder(order);
        verify(orderService, times(1)).processOrder(order);
    }
}
