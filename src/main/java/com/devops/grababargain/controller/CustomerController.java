package com.devops.grababargain.controller;

import com.devops.grababargain.config.exception.DomainException;
import com.devops.grababargain.config.exception.ResponseError;
import com.devops.grababargain.dto.customer.CustomerMapper;
import com.devops.grababargain.dto.customer.CustomerRequest;
import com.devops.grababargain.dto.customer.CustomerResponse;
import com.devops.grababargain.model.Customer;
import com.devops.grababargain.service.customer.ICustomerService;
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
@RequestMapping(value = "/v1/customers", produces = "application/json")
@Tag(name = "Clientes", description = "Controlador de clientes.")
@CrossOrigin
public class CustomerController {

    private final ICustomerService service;
    private final CustomerMapper mapper;

    public CustomerController(final ICustomerService service, final CustomerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Transactional(rollbackOn = Exception.class)
    @Operation(description = "Cria um novo registro.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro realizado com sucesso."),
            @ApiResponse(responseCode = "409", description = "Erro ao realizar criação do novo registro.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })
    })
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest request) {
        Customer customer =
                getService()
                        .create(getMapper()
                                .toEntity(request));

        return new ResponseEntity<>(getMapper().toResponse(customer), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(description = "Busca registro específico por id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro encontrado com sucesso."),
            @ApiResponse(responseCode = "409", description = "Item não encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })
    })
    public ResponseEntity<CustomerResponse> findById(@PathVariable Long id) throws DomainException {
        Customer customer = getService().findById(id);

        return new ResponseEntity<>(getMapper().toResponse(customer), HttpStatus.OK);
    }

    @GetMapping
    @Operation(description = "Busca paginada de todos os clientes.", operationId = "id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista com paginação retornada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro de passagem do parâmetro de ordenação.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })})
    public Page<CustomerResponse> findAll(Pageable pageable) {
        return getService().findAll(pageable).map(mapper::toResponse);
    }

    @PutMapping("/{id}")
    @Transactional(rollbackOn = Exception.class)
    @Operation(description = "Atualiza um registro na base de dados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro criado com sucesso."),
            @ApiResponse(responseCode = "409", description = "Registro não encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })
    })
    public ResponseEntity<CustomerResponse> update(@Valid @RequestBody CustomerRequest request, @PathVariable Long id) {
        Customer customer =
                getService()
                        .update(id, getMapper().toEntity(request));

        return new ResponseEntity<>(getMapper().toResponse(customer), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional(rollbackOn = Exception.class)
    @Operation(description = "Deleta um registro da base de dados")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Registro deleta com sucesso."),
            @ApiResponse(responseCode = "409", description = "Registro não encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })
    })
    public ResponseEntity<?> delete(@PathVariable Long id) {
        getService().delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
