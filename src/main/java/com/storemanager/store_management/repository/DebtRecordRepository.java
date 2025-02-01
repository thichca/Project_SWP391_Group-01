package com.storemanager.store_management.repository;


import com.storemanager.store_management.entity.DebtRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebtRecordRepository extends JpaRepository<DebtRecord, Long> {
}

