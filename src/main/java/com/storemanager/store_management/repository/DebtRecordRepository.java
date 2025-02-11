package com.storemanager.store_management.repository;

import com.storemanager.store_management.entity.DebtRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface DebtRecordRepository extends JpaRepository<DebtRecord, Long> {
    List<DebtRecord> findByCustomerId(Long customerId);

    // Thêm hỗ trợ phân trang
 //   Page<DebtRecord> findAll(Pageable pageable);
}
