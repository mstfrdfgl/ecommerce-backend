package com.redifoglu.ecommerce.repository;

import com.redifoglu.ecommerce.dto.CustomerOrderStatsDTO;
import com.redifoglu.ecommerce.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);

    @Query(value = "SELECT new com.redifoglu.ecommerce.dto.CustomerOrderStatsDTO(o.customer.id, COUNT(o)) " +
            "FROM Order o " +
            "WHERE o.status = 'DELIVERED' " +
            "GROUP BY o.customer.id " +
            "ORDER BY COUNT(o) DESC")
    List<CustomerOrderStatsDTO> findTop100CustomersByDeliveredOrders();
}
