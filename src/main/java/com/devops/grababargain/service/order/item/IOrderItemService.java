package com.devops.grababargain.service.order.item;

import com.devops.grababargain.model.Order;
import com.devops.grababargain.model.OrderItem;

public interface IOrderItemService {

    Order addItem(Long id, OrderItem item);

    Order removeItem(Long id, Long itemId);

    Order updateItemQuantity(Long orderId, Long productId, int newQuantity);

}
