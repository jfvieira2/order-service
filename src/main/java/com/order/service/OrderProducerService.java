package com.order.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.order.model.Order;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderProducerService {
    
    private final KafkaTemplate<String, Order> kafkaTemplate;

    public void sendOrderToQueue(Order order) {
        kafkaTemplate.send("orders", order);
    }
}
