package com.vu.springapi.repository;

import com.vu.springapi.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT COALESCE(SUM(oi.quantity), 0) " +
            "FROM OrderItem oi " +
            "WHERE oi.order.placedAt BETWEEN :from AND :to")
    Long countProductsSold(@Param("from") LocalDateTime from,
                           @Param("to") LocalDateTime to);
}
