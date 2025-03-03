// File: src/main/java/com/order/dto/OrderDTO.java
package com.order.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class OrderDTO {
    private String orderId;
    private String status;
    private BigDecimal totalValue;
    private List<ProductDTO> products;
}
