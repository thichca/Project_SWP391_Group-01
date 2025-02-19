package com.storemanager.store_management.repository;

import com.storemanager.store_management.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Page<Customer> findByNameContainingIgnoreCaseOrPhoneContaining(String name, String phone, Pageable pageable);

    Page<Customer> findByAddressContainingIgnoreCase(String address, Pageable pageable);

    @Query("SELECT DISTINCT c.address FROM Customer c WHERE c.address IS NOT NULL")
    List<String> findDistinctAddresses();
    // Kiểm tra khách hàng có số điện thoại đã tồn tại chưa
    boolean existsByPhone(String phone);


}
