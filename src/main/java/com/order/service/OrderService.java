// File: src/main/java/com/order/service/OrderService.java
package com.order.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.order.model.Order;
import com.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {
   
    private final OrderRepository orderRepository;
   
    private final OrderCacheService orderCacheService;

    /**
     * Processa um pedido garantindo que não há duplicação e que o total seja calculado corretamente.
     * Usa transação para garantir consistência.
     */
    @Transactional
    public Order processOrder(Order order) {
        // Verifica se o pedido já existe para evitar duplicação
        if (orderRepository.existsByOrderId(order.getOrderId())) {
            throw new IllegalArgumentException("Pedido duplicado detectado!");
        }

        // Calcula o valor total do pedido
        order.setStatus("PROCESSING");
        order.setTotalValue(calculateTotal(order));

        // Salva no banco garantindo transação ACID
        Order savedOrder = orderRepository.save(order);

        // Cacheia o pedido para evitar sobrecarga no banco
        orderCacheService.cacheOrder(savedOrder);

        return savedOrder;
    }

    /**
     * Busca um pedido, primeiro verificando no cache para evitar carga no banco.
     */
    public Order getOrder(String orderId) {
        // Primeiro, verifica no cache
        Order cachedOrder = orderCacheService.getCachedOrder(Long.parseLong(orderId));
        if (cachedOrder != null) {
            return cachedOrder;
        }

        // Se não encontrar no cache, busca no banco
        Optional<Order> order = orderRepository.findByOrderId(orderId);
        return order.orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado!"));
    }

    /**
     * Calcula o valor total do pedido com base nos produtos.
     */
    private BigDecimal calculateTotal(Order order) {
        return order.getTotalValue() != null ? order.getTotalValue() : BigDecimal.ZERO;
    }
}
