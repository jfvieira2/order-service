// File: src/test/java/com/order/controller/OrderControllerTest.java
package com.order.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.order.model.Order;
import com.order.service.OrderProducerService;
import com.order.service.OrderService;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderProducerService orderProducerService;

    @Mock
    private OrderService orderService;  

    private Order order;
   

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

        order = new Order();
        order.setId(1L);
        order.setOrderId("123ABC");
        order.setTotalValue(new BigDecimal("100.00"));
        order.setStatus("PENDENTE");       
    }

    @Test
    void deveCriarPedidoComSucesso() throws Exception {
        String orderJson = """
            {
                "orderId": "123ABC",
                "status": "PENDENTE",
                "totalValue": 100.00
            }
        """;

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Pedido enviado para processamento!"));

        verify(orderProducerService, times(1)).sendOrderToQueue(any(Order.class));
    }

    @Test
    void deveRetornarPedidoPorId() throws Exception {
    	when(orderService.getOrder(order.getOrderId())).thenReturn(order);

        mockMvc.perform(get("/orders/{orderId}", "123ABC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("123ABC"))
                .andExpect(jsonPath("$.status").value("PENDENTE"))
                .andExpect(jsonPath("$.totalValue").value(100.00));

        verify(orderService, times(1)).getOrder("123ABC");
    }
}
