package com.storemanager.store_management.service;

import com.storemanager.store_management.entity.Customer;
import com.storemanager.store_management.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Lấy tất cả khách hàng phân trang
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    // Tìm kiếm khách hàng phân trang
    public Page<Customer> searchCustomers(String keyword, Pageable pageable) {
        return customerRepository.findByNameContainingIgnoreCaseOrPhoneContaining(keyword, keyword, pageable);
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
}
