package com.devops.grababargain.dto.order.item;

import com.devops.grababargain.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "product.id", source = "productId")
    OrderItem toEntity(OrderItemRequest request);

    OrderItemResponse toResponse(OrderItem customer);
}
