package com.devops.grababargain.controller;

import com.devops.grababargain.config.exception.DomainException;
import com.devops.grababargain.config.exception.ResponseError;
import com.devops.grababargain.dto.product.ProductMapper;
import com.devops.grababargain.dto.product.ProductRequest;
import com.devops.grababargain.dto.product.ProductResponse;
import com.devops.grababargain.model.Product;
import com.devops.grababargain.service.product.IProductService;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RestController
@RequestMapping(value = "/v1/products", produces = "application/json")
@Tag(name = "Produtos", description = "Controlador de Produtos.")
@CrossOrigin
public class ProductController {

    private final IProductService service;
    private final ProductMapper mapper;

    public ProductController(final IProductService service, final ProductMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Transactional(rollbackOn = Exception.class)
    @Operation(description = "Cria um novo Produto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro realizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao realizar criação do novo Produto.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })
    })
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        Product product =
                getService()
                        .create(getMapper()
                                .toEntity(request));

        return new ResponseEntity<>(getMapper().toResponse(product), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(description = "Busca registro específico por id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })
    })
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) throws DomainException {
        Product product = getService().findById(id);

        return new ResponseEntity<>(getMapper().toResponse(product), HttpStatus.OK);
    }

    @GetMapping
    @Operation(description = "Busca paginada de todos os Produtos.", operationId = "id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista com paginação retornada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro de passagem do parâmetro de ordenação.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })})
    public Page<ProductResponse> findAll(Pageable pageable) {
        return getService().findAll(pageable).map(mapper::toResponse);
    }

    @PutMapping("/{id}")
    @Transactional(rollbackOn = Exception.class)
    @Operation(description = "Atualiza um Produto na base de dados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })
    })
    public ResponseEntity<ProductResponse> update(@Valid @RequestBody ProductRequest request, @PathVariable Long id) {
        Product product =
                getService()
                        .update(id, getMapper().toEntity(request));

        return new ResponseEntity<>(getMapper().toResponse(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional(rollbackOn = Exception.class)
    @Operation(description = "Deleta um Produto da base de dados")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })
    })
    public ResponseEntity<?> delete(@PathVariable Long id) {
        getService().delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
