package com.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.dto.OrderDTO;
import com.order.mapper.OrderMapper;
import com.order.model.Order;
import com.order.service.OrderProducerService;
import com.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    
    private final OrderProducerService orderProducerService;
    
    private final OrderService orderService;

    @PostMapping
    public String createOrder(@RequestBody Order order) {
        orderProducerService.sendOrderToQueue(order);
        return "Pedido enviado para processamento!";
    }

    @GetMapping("/{orderId}")
    public OrderDTO getOrder(@PathVariable String orderId) {
    	
        Order order = orderService.getOrder(orderId);         
        return OrderMapper.INSTANCE.toDTO(order);
    }

}
