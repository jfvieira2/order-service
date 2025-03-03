package com.order.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.order.dto.OrderDTO;

@FeignClient(name = "externalOrderClient", url = "https://produtoA.com/api/orders", fallback = ExternalOrderFallback.class)
public interface ExternalOrderClient {
    @GetMapping
    List<OrderDTO> fetchExternalOrders();
}