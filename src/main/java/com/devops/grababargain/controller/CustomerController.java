package com.devops.grababargain.controller;

import com.devops.grababargain.config.exception.DomainException;
import com.devops.grababargain.dto.customer.CustomerMapper;
import com.devops.grababargain.dto.customer.CustomerRequest;
import com.devops.grababargain.dto.customer.CustomerResponse;
import com.devops.grababargain.model.Customer;
import com.devops.grababargain.service.customer.ICustomerService;
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
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest request) {
        Customer customer =
                getService()
                        .create(getMapper()
                                .toEntity(request));

        return new ResponseEntity<>(getMapper().toResponse(customer), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable Long id) throws DomainException {
        Customer customer = getService().findById(id);

        return new ResponseEntity<>(getMapper().toResponse(customer), HttpStatus.OK);
    }

    @GetMapping
    public Page<CustomerResponse> findAll(Pageable pageable) {
        return getService().findAll(pageable).map(mapper::toResponse);
    }

    @PutMapping("/{id}")
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<CustomerResponse> update(@Valid @RequestBody CustomerRequest request, @PathVariable Long id) {
        Customer customer =
                getService()
                        .update(id, getMapper().toEntity(request));

        return new ResponseEntity<>(getMapper().toResponse(customer), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        getService().delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
