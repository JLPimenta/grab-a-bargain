package com.devops.grababargain.dto.order;

import com.devops.grababargain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "customer.id", source = "customerId")
    Order toEntity(OrderRequest request);

    OrderResponse toResponse(Order customer);
}
