package com.devops.grababargain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ITEMS_FROM_ORDER")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDEN_ORDER_ITEMS", nullable = false)
    private Long id;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;

    @ManyToOne
    @JoinColumn(name = "IDEN_PRODUCT", foreignKey = @ForeignKey(name = "ORDER_ITEM_FK_01"), nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "IDEN_ORDER", foreignKey = @ForeignKey(name = "ORDER_ITEM_FK_02"), nullable = false)
    private Order order;
}
