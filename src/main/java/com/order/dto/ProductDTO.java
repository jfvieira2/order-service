// File: src/main/java/com/order/dto/ProductDTO.java
package com.order.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
public class ProductDTO {
    private String name;
    private BigDecimal price;
}
