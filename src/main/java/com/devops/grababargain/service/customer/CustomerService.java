package com.devops.grababargain.service.customer;

import com.devops.grababargain.config.exception.DomainException;
import com.devops.grababargain.config.exception.NotFoundException;
import com.devops.grababargain.model.Customer;
import com.devops.grababargain.repository.CustomerRepository;
import lombok.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Getter
@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository repository;

    public CustomerService(final CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer create(Customer data) {
        return getRepository().save(data);
    }

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    @Override
    public Customer findById(Long id) throws DomainException {
        return getRepository()
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Nenhum registro encontrado."));
    }

    @Override
    public Customer update(Long id, Customer data) throws DomainException {
        Customer existent = getRepository()
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Registro com identificador " + id + " não encontrado"));

        bind(existent, data);

        return getRepository().save(existent);
    }

    @Override
    public void delete(Long id) {
        getRepository().deleteById(id);
    }

    private void bind(Customer entity, Customer update) {
        BeanUtils.copyProperties(update, entity, "id");
    }
}
