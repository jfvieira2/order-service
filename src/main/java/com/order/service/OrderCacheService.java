package com.order.service;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.order.model.Order;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderCacheService {
   
    private final RedisTemplate<String, Object> redisTemplate;

    public void cacheOrder(Order order) {
        redisTemplate.opsForValue().set("pedido:" + order.getId(), order, Duration.ofMinutes(30));
    }

    public Order getCachedOrder(Long id) {
        return (Order) redisTemplate.opsForValue().get("pedido:" + id);
    }
}
