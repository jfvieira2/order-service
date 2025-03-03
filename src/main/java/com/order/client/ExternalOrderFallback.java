package com.order.client;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.order.dto.OrderDTO;

@Component
public class ExternalOrderFallback implements ExternalOrderClient {
    @Override
    public List<OrderDTO> fetchExternalOrders() {
        return Collections.emptyList(); 
    }    
}