package com.devops.grababargain.dto.order;

import com.devops.grababargain.dto.order.item.OrderItemRequest;
import com.devops.grababargain.enums.OrderStatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @Builder.Default
    private LocalDateTime orderDate = LocalDateTime.now();

    @Builder.Default
    private OrderStatusEnum status = OrderStatusEnum.PENDING;

    @NotNull(message = "É necessário informar o Cliente que está gerando o Pedido.")
    private Long customerId;

    private Set<OrderItemRequest> orderItemList = new HashSet<>();
}
