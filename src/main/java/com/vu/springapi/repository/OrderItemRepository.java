package com.vu.springapi.repository;

import com.vu.springapi.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT COALESCE(SUM(oi.quantity), 0) " +
            "FROM OrderItem oi " +
            "WHERE oi.order.placedAt BETWEEN :from AND :to")
    Long countProductsSold(@Param("from") LocalDateTime from,
                           @Param("to") LocalDateTime to);

    // Top 5 sản phẩm bán chạy
    @Query("SELECT oi.product.id, oi.product.name, SUM(oi.quantity), oi.product.imageUrl " +
           "FROM OrderItem oi " +
           "WHERE oi.order.placedAt BETWEEN :from AND :to " +
           "GROUP BY oi.product.id, oi.product.name, oi.product.imageUrl " +
           "ORDER BY SUM(oi.quantity) DESC")
    List<Object[]> getTopSellingProducts(@Param("from") LocalDateTime from,
                                         @Param("to") LocalDateTime to);
}
