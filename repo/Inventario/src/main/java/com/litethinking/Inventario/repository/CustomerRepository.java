package com.litethinking.Inventario.repository;

import com.litethinking.Inventario.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository("customerRepository")
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findById(Long id);
    void deleteById(Long id);
}
