package com.storemanager.store_management.service;



import com.storemanager.store_management.entity.Customer;
import com.storemanager.store_management.repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }


    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
    public List<Customer> searchCustomers(String keyword) {
        return customerRepository.findByNameContainingIgnoreCaseOrPhoneContaining(keyword, keyword);
    }


}

