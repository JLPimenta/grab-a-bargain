package com.devops.grababargain.service.order;

import com.devops.grababargain.config.exception.DomainException;
import com.devops.grababargain.config.exception.NotFoundException;
import com.devops.grababargain.enums.OrderStatusEnum;
import com.devops.grababargain.model.Order;
import com.devops.grababargain.model.OrderItem;
import com.devops.grababargain.repository.OrderItemRepository;
import com.devops.grababargain.repository.OrderRepository;
import com.devops.grababargain.service.order.item.OrderItemService;
import com.devops.grababargain.service.product.IProductService;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Getter
@Service
public class OrderService extends OrderItemService implements IOrderService {

    public OrderService(final OrderRepository repository, final IProductService productService, final OrderItemRepository itemRepository) {
        super(repository, productService, itemRepository);
    }

    @Override
    public Order create(Order data) {

        for (OrderItem item : data.getOrderItemList()) {
            item.setOrder(data);

            var productPrice = item.getProduct().getPrice();
            var productQuantity = item.getQuantity();

            item.setUnitPrice(productPrice.multiply(BigDecimal.valueOf(productQuantity)));
        }

        return getRepository().save(data);
    }

    @Override
    public Order findById(Long id) throws DomainException {
        return getRepository()
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Nenhum registro encontrado."));
    }

    @Override
    public Page<Order> findAllByCustomer(Pageable pageable, Long customerId) {
        return getRepository().findByCustomerId(pageable, customerId);
    }

    @Override
    public Order updateStatus(Long id, OrderStatusEnum status) {
        Order order = findById(id);

        if (order.getStatus() != OrderStatusEnum.COMPLETED) {
            order.setStatus(status);

            return getRepository().save(order);
        }

        throw new DomainException("Não é possível reverter um pedido já completado.");
    }

    @Override
    public void delete(Long id) {
        getRepository().deleteById(id);
    }
}
