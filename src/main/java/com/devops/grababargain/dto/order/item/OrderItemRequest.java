package com.devops.grababargain.dto.order.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest implements Serializable {

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Integer quantity;
    private Long productId;
}
