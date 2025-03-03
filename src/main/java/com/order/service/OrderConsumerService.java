package com.order.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.order.model.Order;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderConsumerService{

    private final OrderService orderService;
   
	@KafkaListener(topics = "orders", groupId = "order-group")
    @Transactional
    public void processOrder(Order order) {
        try {
            orderService.processOrder(order);
            System.out.println("Pedido processado e salvo com sucesso: " + order.getOrderId());
        } catch (Exception e) {
            System.err.println("Erro ao processar pedido: " + order.getOrderId() + " - " + e.getMessage());
        }
    }
}
