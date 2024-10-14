package com.devops.grababargain.repository;

import com.devops.grababargain.model.Customer;
import com.devops.grababargain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByCustomerId(Pageable pageable, Long customerId);
}
