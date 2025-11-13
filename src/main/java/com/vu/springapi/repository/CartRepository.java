package com.vu.springapi.repository;

import com.vu.springapi.model.Address;
import com.vu.springapi.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
