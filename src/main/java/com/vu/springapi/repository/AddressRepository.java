package com.vu.springapi.repository;

import com.vu.springapi.model.Address;
import com.vu.springapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
