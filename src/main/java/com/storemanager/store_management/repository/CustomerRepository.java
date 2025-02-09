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
    // Tìm kiếm khách hàng theo tên hoặc số điện thoại
    Page<Customer> findByNameContainingIgnoreCaseOrPhoneContaining(String name, String phone, Pageable pageable);

    // Lọc khách hàng theo địa chỉ
    Page<Customer> findByAddressContainingIgnoreCase(String address, Pageable pageable);

    // Truy vấn tùy chỉnh lấy tất cả các địa chỉ khác nhau (distinct) từ cơ sở dữ liệu
    @Query("SELECT DISTINCT c.address FROM Customer c WHERE c.address IS NOT NULL")
    List<String> findDistinctAddresses();}
