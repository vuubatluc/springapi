package com.vu.springapi.repository;

import com.vu.springapi.model.Address;
import com.vu.springapi.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
