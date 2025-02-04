package com.storemanager.store_management.repository;

import com.storemanager.store_management.entity.DebtRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtRecordRepository extends JpaRepository<DebtRecord, Long> {
    List<DebtRecord> findByCustomerId(Long customerId);
}
