CREATE TABLE ITEMS_FROM_ORDER
(
    iden_order_items BIGSERIAL PRIMARY KEY,
    quantity         INTEGER NOT NULL,
    unit_price       DECIMAL(19, 2),
    iden_product     BIGINT  NOT NULL,
    iden_order       BIGINT  NOT NULL,

    CONSTRAINT ORDER_ITEM_FK_01 FOREIGN KEY (iden_product)
        REFERENCES PRODUCT (iden_product) ON DELETE CASCADE,

    CONSTRAINT ORDER_ITEM_FK_02 FOREIGN KEY (iden_order)
        REFERENCES "order" (iden_order) ON DELETE CASCADE
);
