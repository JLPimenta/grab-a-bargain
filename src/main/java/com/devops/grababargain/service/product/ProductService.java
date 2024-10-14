package com.devops.grababargain.service.product;

import com.devops.grababargain.config.exception.DomainException;
import com.devops.grababargain.config.exception.NotFoundException;
import com.devops.grababargain.model.Product;
import com.devops.grababargain.repository.ProductRepository;
import lombok.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Getter
@Service
public class ProductService implements IProductService {

    private final ProductRepository repository;

    public ProductService(final ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product create(Product data) {
        return getRepository().save(data);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    @Override
    public Product findById(Long id) throws DomainException {
        return getRepository()
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Nenhum registro encontrado."));
    }

    @Override
    public Product update(Long id, Product data) throws DomainException {
        Product existent = getRepository()
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Registro com identificador " + id + " não encontrado"));

        bind(existent, data);

        return getRepository().save(existent);
    }

    @Override
    public void delete(Long id) {

    }

    private void bind(Product entity, Product update) {
        BeanUtils.copyProperties(update, entity, "id");
    }
}
