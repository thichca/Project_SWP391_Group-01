package com.storemanager.store_management.service;

import com.storemanager.store_management.entity.Customer;
import com.storemanager.store_management.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Lấy tất cả khách hàng phân trang
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    // Tìm kiếm khách hàng theo tên hoặc số điện thoại
    public Page<Customer> searchCustomers(String keyword, Pageable pageable) {
        return customerRepository.findByNameContainingIgnoreCaseOrPhoneContaining(keyword, keyword, pageable);
    }

    // Lọc khách hàng theo địa chỉ
    public Page<Customer> getCustomersByAddress(String address, Pageable pageable) {
        return customerRepository.findByAddressContainingIgnoreCase(address, pageable);
    }

    // Lấy tất cả các địa chỉ duy nhất từ cơ sở dữ liệu
    public List<String> getAllDistinctAddresses() {
        return customerRepository.findDistinctAddresses();
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
