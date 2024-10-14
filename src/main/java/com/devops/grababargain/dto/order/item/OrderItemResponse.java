package com.devops.grababargain.dto.order.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.devops.grababargain.model.OrderItem}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse implements Serializable {
    private Long id;

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Integer quantity;

    private ProductOrderResponse product;

    private BigDecimal unitPrice;

    /**
     * DTO for {@link com.devops.grababargain.model.Product}
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductOrderResponse implements Serializable {
        private Long id;
        private String name;
        private BigDecimal price;
    }
}