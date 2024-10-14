package com.devops.grababargain.service.order.item;

import com.devops.grababargain.config.exception.DomainException;
import com.devops.grababargain.config.exception.NotFoundException;
import com.devops.grababargain.model.Order;
import com.devops.grababargain.model.OrderItem;
import com.devops.grababargain.model.Product;
import com.devops.grababargain.repository.OrderItemRepository;
import com.devops.grababargain.repository.OrderRepository;
import com.devops.grababargain.service.product.IProductService;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
public class OrderItemService implements IOrderItemService {

    private final OrderRepository repository;
    private final OrderItemRepository itemRepository;
    private final IProductService productService;

    public OrderItemService(final OrderRepository repository, final IProductService productService, final OrderItemRepository itemRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.productService = productService;
    }

    @Override
    public Order addItem(Long orderId, OrderItem item) {
        Order order = findOrderById(orderId);
        OrderItem existingItem = findExistingOrderItem(order, item.getProduct().getId());

        if (Objects.nonNull(existingItem)) {
            updateItemQuantity(existingItem, existingItem.getQuantity() + item.getQuantity());
            getItemRepository().save(existingItem);
        } else {
            convertToOrderItemAndSave(order, item);
        }

        return saveOrder(order);
    }

    @Override
    public Order removeItem(Long orderId, Long productId) {
        Order order = findOrderById(orderId);
        OrderItem itemToRemove = findExistingOrderItem(order, productId);

        if (Objects.nonNull(itemToRemove)) {
            order.getOrderItemList().remove(itemToRemove);
            getItemRepository().delete(itemToRemove);

            return saveOrder(order);
        }

        throw new DomainException("O produto com ID: " + productId + " não está presente no pedido.");
    }

    @Override
    public Order updateItemQuantity(Long orderId, Long productId, int newQuantity) {
        Order order = findOrderById(orderId);
        OrderItem existingItem = findExistingOrderItem(order, productId);

        if (Objects.nonNull(existingItem)) {
            if (newQuantity > 0) {
                updateItemQuantity(existingItem, newQuantity);
            } else {
                order.getOrderItemList().remove(existingItem);
                getItemRepository().delete(existingItem);
            }

            return saveOrder(order);
        }

        throw new DomainException("O produto com ID: " + productId + " não está presente no pedido.");
    }

    public void convertToOrderItemAndSave(Order order, OrderItem item) {
        Product product = getProductService().findById(item.getProduct().getId());
        item.setProduct(product);
        item.setUnitPrice(calculateTotalValue(product.getPrice(), item.getQuantity()));
        item.setOrder(order);

        order.getOrderItemList().add(item);

        getItemRepository().save(item);
    }

    private void updateItemQuantity(OrderItem item, int newQuantity) {
        item.setQuantity(newQuantity);
        item.setUnitPrice(calculateTotalValue(item.getProduct().getPrice(), newQuantity));
    }

    private Order findOrderById(Long orderId) {
        return getRepository()
                .findById(orderId)
                .orElseThrow(() -> new NotFoundException("Nenhum pedido encontrado com o ID: " + orderId));
    }

    private Order saveOrder(Order order) {
        return getRepository().save(order);
    }

    private OrderItem findExistingOrderItem(Order order, Long productId) {
        return order.getOrderItemList()
                .stream()
                .filter(orderItem -> orderItem.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public BigDecimal calculateTotalValue(BigDecimal price, Integer quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}

