package com.devops.grababargain.service.customer;

import com.devops.grababargain.config.exception.DomainException;
import com.devops.grababargain.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService {
    Customer create(Customer data);

    Page<Customer> findAll(Pageable pageable);

    Customer findById(Long id) throws DomainException;

    Customer update(Long id, Customer data) throws DomainException;

    void delete(Long id);
}
