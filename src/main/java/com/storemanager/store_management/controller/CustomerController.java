package com.storemanager.store_management.controller;

import com.storemanager.store_management.entity.Customer;
import com.storemanager.store_management.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

   @GetMapping
    public String listCustomers(Model model) {
       model.addAttribute("customers", customerService.getAllCustomers());
       return "/list";
    }

    @GetMapping ("/add")
    public String addCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "add";
    }
    @PostMapping("/add")
   public String saveCustomer(@ModelAttribute Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/customers"; // Quay lại danh sách sau khi thêm
    }

    @PostMapping("/update")
    public String updateCustomer(@ModelAttribute Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/customers";
    }


    @GetMapping("/edit/{id}")
    public String editCustomerForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }
}
