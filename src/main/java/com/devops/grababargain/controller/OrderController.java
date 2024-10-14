package com.devops.grababargain.controller;

import com.devops.grababargain.config.exception.ResponseError;
import com.devops.grababargain.dto.order.OrderMapper;
import com.devops.grababargain.dto.order.OrderRequest;
import com.devops.grababargain.dto.order.OrderResponse;
import com.devops.grababargain.dto.order.item.OrderItemMapper;
import com.devops.grababargain.dto.order.item.OrderItemRequest;
import com.devops.grababargain.enums.OrderStatusEnum;
import com.devops.grababargain.model.Order;
import com.devops.grababargain.service.order.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
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
@Tag(name = "Pedidos", description = "Controlador de Pedidos")
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
    @Transactional(rollbackOn = Exception.class)
    @Operation(description = "Cria um novo pedido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro realizado com sucesso."),
            @ApiResponse(responseCode = "409", description = "Erro ao realizar criação do novo registro.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })
    })
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        Order createdOrder = getService().create(getMapper().toEntity(orderRequest));
        OrderResponse response = getMapper().toResponse(createdOrder);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/{orderId}/items")
    @Operation(description = "Adiciona um produto a um pedido existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto incluído no pedido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao inclusão do produto no pedido.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })
    })
    public ResponseEntity<OrderResponse> addItemToOrder(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderItemRequest itemRequest) {

        Order order = getService().addItem(orderId, getItemMapper().toEntity(itemRequest));
        OrderResponse response = getMapper().toResponse(order);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}/items/{productId}")
    @Transactional(rollbackOn = Exception.class)
    @Operation(description = "Remove o Produto de um pedido existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto removido do pedido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Produto não encontrado no pedido.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })
    })
    public ResponseEntity<OrderResponse> removeItemFromOrder(
            @PathVariable Long orderId,
            @PathVariable Long productId) {

        Order updatedOrder = getService().removeItem(orderId, productId);
        OrderResponse response = getMapper().toResponse(updatedOrder);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{orderId}/items/{productId}")
    @Transactional(rollbackOn = Exception.class)
    @Operation(description = "Atualiza a quantidade de itens de um Produto no Pedido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Quantidade do produto atualizada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })
    })
    public ResponseEntity<OrderResponse> updateItemQuantity(
            @PathVariable Long orderId,
            @PathVariable Long productId,
            @RequestParam int quantity) {

        Order updatedOrder = getService().updateItemQuantity(orderId, productId, quantity);
        OrderResponse response = getMapper().toResponse(updatedOrder);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    @Operation(description = "Busca Pedido específico por id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso."),
            @ApiResponse(responseCode = "409", description = "Pedido não encontrado.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })
    })
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
        Order order = getService().findById(orderId);
        OrderResponse response = getMapper().toResponse(order);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{orderId}")
    @Transactional(rollbackOn = Exception.class)
    @Operation(description = "Atualiza a situação de um Pedido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Situação atualizada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })
    })
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatusEnum status) {
        Order order = getService().updateStatus(orderId, status);
        OrderResponse response = getMapper().toResponse(order);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @Operation(description = "Busca paginada de todos os Pedidos vinculados à um Cliente.", operationId = "customerId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista com paginação retornada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro de passagem do parâmetro de ordenação.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })})
    public Page<OrderResponse> findAllByCustomer(Pageable pageable, @RequestParam Long customerId) {
        return getService().findAllByCustomer(pageable, customerId).map(mapper::toResponse);
    }
}
