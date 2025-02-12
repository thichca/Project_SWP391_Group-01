package com.storemanager.store_management.service;

import com.storemanager.store_management.entity.Customer;
import com.storemanager.store_management.entity.DebtRecord;
import com.storemanager.store_management.repository.CustomerRepository;
import com.storemanager.store_management.repository.DebtRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DebtRecordService {

    @Autowired
    private DebtRecordRepository debtRecordRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private static final double CREDIT_LIMIT = 10000.00; // Hạn mức công nợ

    // Lấy tất cả các bản ghi công nợ
    public Page<DebtRecord> getAllDebtRecords(Pageable pageable) {

        return debtRecordRepository.findAll(pageable);
    }

    // Lấy công nợ theo khách hàng
    public List<DebtRecord> getDebtsByCustomer(Long customerId) {
        return debtRecordRepository.findByCustomerId(customerId);
    }

    // Lưu công nợ mới (Ghi nợ hoặc Trả nợ)
    @Transactional
    public void saveDebtRecord(DebtRecord debtRecord) {
        Customer customer = customerRepository.findById(debtRecord.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));

        if ("GHI_NO".equals(debtRecord.getType())) {
            // Ghi nợ (tăng công nợ)
            if (customer.getDebtBalance() + debtRecord.getAmount() > CREDIT_LIMIT) {
                throw new RuntimeException("Công nợ vượt hạn mức cho phép!");
            }
            customer.setDebtBalance(customer.getDebtBalance() + debtRecord.getAmount());
        } else if ("TRA_NO".equals(debtRecord.getType())) {
            // Trả nợ (giảm công nợ)
            customer.setDebtBalance(customer.getDebtBalance() - debtRecord.getAmount());
        }

        customerRepository.save(customer);
        debtRecordRepository.save(debtRecord);
    }

    // Xóa công nợ
    @Transactional
    public void deleteDebtRecord(Long id) {
        DebtRecord debtRecord = debtRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Công nợ không tồn tại"));

        Customer customer = debtRecord.getCustomer();
        if ("GHI_NO".equals(debtRecord.getType())) {
            customer.setDebtBalance(customer.getDebtBalance() - debtRecord.getAmount()); // Hoàn tác ghi nợ
        } else if ("TRA_NO".equals(debtRecord.getType())) {
            customer.setDebtBalance(customer.getDebtBalance() + debtRecord.getAmount()); // Hoàn tác trả nợ
        }

        customerRepository.save(customer);
        debtRecordRepository.deleteById(id);
    }
}
