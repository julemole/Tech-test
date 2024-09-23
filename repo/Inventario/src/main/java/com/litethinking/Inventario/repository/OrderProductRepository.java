package com.litethinking.Inventario.repository;

import com.litethinking.Inventario.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("orderProductRepository")
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
