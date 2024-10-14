package com.devops.grababargain.controller;

import com.devops.grababargain.dto.order.OrderMapper;
import com.devops.grababargain.dto.order.OrderRequest;
import com.devops.grababargain.dto.order.OrderResponse;
import com.devops.grababargain.dto.order.item.OrderItemMapper;
import com.devops.grababargain.dto.order.item.OrderItemRequest;
import com.devops.grababargain.enums.OrderStatusEnum;
import com.devops.grababargain.model.Order;
import com.devops.grababargain.service.order.IOrderService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Getter
@RestController
@RequestMapping(value = "/v1/orders", produces = "application/json")
@CrossOrigin
public class OrderController {

    private final IOrderService service;
    private final OrderMapper mapper;
    private final OrderItemMapper itemMapper;

    public OrderController(final IOrderService service, final OrderMapper mapper, final OrderItemMapper itemMapper) {
        this.service = service;
        this.mapper = mapper;
        this.itemMapper = itemMapper;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        Order createdOrder = getService().create(getMapper().toEntity(orderRequest));
        OrderResponse response = getMapper().toResponse(createdOrder);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderResponse> addItemToOrder(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderItemRequest itemRequest) {

        Order order = getService().addItem(orderId, getItemMapper().toEntity(itemRequest));
        OrderResponse response = getMapper().toResponse(order);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}/items/{productId}")
    public ResponseEntity<OrderResponse> removeItemFromOrder(
            @PathVariable Long orderId,
            @PathVariable Long productId) {

        Order updatedOrder = getService().removeItem(orderId, productId);
        OrderResponse response = getMapper().toResponse(updatedOrder);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{orderId}/items/{productId}")
    public ResponseEntity<OrderResponse> updateItemQuantity(
            @PathVariable Long orderId,
            @PathVariable Long productId,
            @RequestParam int quantity) {

        Order updatedOrder = getService().updateItemQuantity(orderId, productId, quantity);
        OrderResponse response = getMapper().toResponse(updatedOrder);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
        Order order = getService().findById(orderId);
        OrderResponse response = getMapper().toResponse(order);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatusEnum status) {
        Order order = getService().updateStatus(orderId, status);
        OrderResponse response = getMapper().toResponse(order);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public Page<OrderResponse> findAllByCustomer(Pageable pageable, @RequestParam Long customerId) {
        return getService().findAllByCustomer(pageable, customerId).map(mapper::toResponse);
    }
}
