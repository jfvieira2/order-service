// File: src/test/java/com/order/service/OrderProducerServiceTest.java
package com.order.service;

import com.order.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderProducerServiceTest {

    @InjectMocks
    private OrderProducerService orderProducerService;

    @Mock
    private KafkaTemplate<String, Order> kafkaTemplate;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1L);
        order.setOrderId("123ABC");
    }

    @Test
    void deveEnviarPedidoParaKafka() {
        orderProducerService.sendOrderToQueue(order);
        verify(kafkaTemplate, times(1)).send("orders", order);
    }
}
