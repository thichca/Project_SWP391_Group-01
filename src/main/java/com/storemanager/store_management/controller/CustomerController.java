package com.storemanager.store_management.controller;


import com.storemanager.store_management.entity.Customer;
import com.storemanager.store_management.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Hiển thị danh sách khách hàng + Form thêm khách hàng ngay trên trang
    @GetMapping
    public String listCustomers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "addressFilter", required = false) String addressFilter, // Filter theo địa chỉ
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customersPage;

        if (keyword != null && !keyword.isEmpty()) {
            customersPage = customerService.searchCustomers(keyword, pageable);
        } else if (addressFilter != null && !addressFilter.isEmpty()) {
            customersPage = customerService.getCustomersByAddress(addressFilter, pageable); // Lọc theo địa chỉ
        } else {
            customersPage = customerService.getAllCustomers(pageable);
        }

        // Lấy tất cả các địa chỉ duy nhất từ cơ sở dữ liệu
        List<String> addressList = customerService.getAllDistinctAddresses();

        model.addAttribute("customers", customersPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", customersPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("keyword", keyword);
        model.addAttribute("addressFilter", addressFilter); // Giữ lại giá trị lọc
        model.addAttribute("addressList", addressList); // Gửi danh sách địa chỉ vào view
        model.addAttribute("customer", new Customer());

        return "customers";
    }

    // Xử lý khi thêm khách hàng
    @PostMapping("/add")
    public String addCustomer(@ModelAttribute("customer") Customer customer, RedirectAttributes redirectAttributes) {
        customerService.saveCustomer(customer);
        redirectAttributes.addFlashAttribute("message", "Thêm khách hàng thành công!");
        return "redirect:/customers"; // Reload lại trang danh sách khách hàng
    }

    // Xử lý khi xóa khách hàng
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        customerService.deleteCustomer(id);
        redirectAttributes.addFlashAttribute("message", "Xóa khách hàng thành công!");
        return "redirect:/customers";
    }

    // Chỉnh sửa thông tin khách hàng
    @PostMapping("/edit")
    public String editCustomer(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("debtBalance") Double debtBalance,
            RedirectAttributes redirectAttributes) {

        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            customer.setName(name);
            customer.setPhone(phone);
            customer.setAddress(address);
            customer.setDebtBalance(debtBalance);
            customerService.saveCustomer(customer);
            redirectAttributes.addFlashAttribute("message", "Cập nhật khách hàng thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy khách hàng!");
        }
        return "redirect:/customers";
    }
}
