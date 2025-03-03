// File: src/test/java/com/order/service/OrderServiceTest.java
package com.order.service;

import com.order.dto.OrderDTO;
import com.order.mapper.OrderMapper;
import com.order.model.Order;
import com.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderCacheService orderCacheService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1L);
        order.setOrderId("123");
        order.setTotalValue(new BigDecimal("100.00"));
        order.setStatus("PENDENTE");
    }

    @Test
    void deveCriarPedidoComSucesso() {
        when(orderRepository.existsByOrderId(order.getOrderId())).thenReturn(false);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order savedOrder = orderService.processOrder(order);

        assertNotNull(savedOrder);
        assertEquals("123", savedOrder.getOrderId());
        verify(orderRepository, times(1)).save(order);
        verify(orderCacheService, times(1)).cacheOrder(order);
    }

    @Test
    void deveLancarErroSePedidoDuplicado() {
        when(orderRepository.existsByOrderId(order.getOrderId())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.processOrder(order);
        });

        assertEquals("Pedido duplicado detectado!", exception.getMessage());
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void deveConverterOrderParaDTOComMapper() {
        when(orderRepository.findByOrderId(order.getOrderId())).thenReturn(Optional.of(order));

        OrderDTO orderDTO = OrderMapper.INSTANCE.toDTO(orderService.getOrder(order.getOrderId()));

        assertNotNull(orderDTO);
        assertEquals(order.getOrderId(), orderDTO.getOrderId());
        assertEquals(order.getTotalValue(), orderDTO.getTotalValue());
    }
}
