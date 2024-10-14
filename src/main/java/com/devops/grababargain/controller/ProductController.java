package com.devops.grababargain.controller;

import com.devops.grababargain.config.exception.DomainException;
import com.devops.grababargain.dto.product.ProductMapper;
import com.devops.grababargain.dto.product.ProductRequest;
import com.devops.grababargain.dto.product.ProductResponse;
import com.devops.grababargain.model.Product;
import com.devops.grababargain.service.product.IProductService;
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
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        Product product =
                getService()
                        .create(getMapper()
                                .toEntity(request));

        return new ResponseEntity<>(getMapper().toResponse(product), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) throws DomainException {
        Product product = getService().findById(id);

        return new ResponseEntity<>(getMapper().toResponse(product), HttpStatus.OK);
    }

    @GetMapping
    public Page<ProductResponse> findAll(Pageable pageable) {
        return getService().findAll(pageable).map(mapper::toResponse);
    }

    @PutMapping("/{id}")
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<ProductResponse> update(@Valid @RequestBody ProductRequest request, @PathVariable Long id) {
        Product product =
                getService()
                        .update(id, getMapper().toEntity(request));

        return new ResponseEntity<>(getMapper().toResponse(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        getService().delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
