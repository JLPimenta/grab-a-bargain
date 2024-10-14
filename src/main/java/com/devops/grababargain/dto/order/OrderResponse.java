package com.devops.grababargain.dto.order;

import com.devops.grababargain.dto.order.item.OrderItemResponse;
import com.devops.grababargain.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private LocalDateTime orderDate;
    private OrderStatusEnum status;
    private CustomerOrderResponse customer;
    private Set<OrderItemResponse> orderItemList = new HashSet<>();


    /**
     * DTO for {@link com.devops.grababargain.model.Customer}
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class CustomerOrderResponse implements Serializable {
        private Long id;
        private String name;

    }
}