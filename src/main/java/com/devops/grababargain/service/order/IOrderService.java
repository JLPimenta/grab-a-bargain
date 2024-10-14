package com.devops.grababargain.service.order;

import com.devops.grababargain.config.exception.DomainException;
import com.devops.grababargain.enums.OrderStatusEnum;
import com.devops.grababargain.model.Order;
import com.devops.grababargain.service.order.item.IOrderItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderService extends IOrderItemService {
    Order create(Order order);

    Order findById(Long id) throws DomainException;

    Page<Order> findAllByCustomer(Pageable pageable, Long customerId);

    Order updateStatus(Long id, OrderStatusEnum status);

    void delete(Long id);
}
