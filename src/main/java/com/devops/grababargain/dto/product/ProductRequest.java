package com.devops.grababargain.dto.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotNull(message = "O campo Nome do Produto não pode ser vazio.")
    @Size(max = 50, message = "O Nome do Produto deve ter no máximo 50 caracteres.")
    private String name;

    @Size(max = 50, message = "A Descrição do Produto deve ter no máximo 150 caracteres.")
    private String description;

    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal price;
}
