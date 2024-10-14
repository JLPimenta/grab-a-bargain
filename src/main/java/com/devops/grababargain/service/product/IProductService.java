package com.devops.grababargain.service.product;

import com.devops.grababargain.config.exception.DomainException;
import com.devops.grababargain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    Product create(Product data);

    Page<Product> findAll(Pageable pageable);

    Product findById(Long id) throws DomainException;

    Product update(Long id, Product data) throws DomainException;

    void delete(Long id);
}
