package com.litethinking.Inventario.repository;

import com.litethinking.Inventario.model.Customer;
import com.litethinking.Inventario.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("customerOrderRepository")
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, String> {
    List<CustomerOrder> findByCustomerId(Long customerId);
}
