// File: src/main/java/com/order/mapper/OrderMapper.java
package com.order.mapper;

import com.order.dto.OrderDTO;
import com.order.dto.ProductDTO;
import com.order.model.Order;
import com.order.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {
    
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "totalValue", target = "totalValue")
    @Mapping(source = "products", target = "products")
    OrderDTO toDTO(Order order);

    List<OrderDTO> toDTOList(List<Order> orders);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    ProductDTO toDTO(Product product);

    List<ProductDTO> toProductDTOList(List<Product> products);

    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "totalValue", target = "totalValue")
    @Mapping(source = "products", target = "products")
    Order toEntity(OrderDTO orderDTO);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    Product toEntity(ProductDTO productDTO);
}
