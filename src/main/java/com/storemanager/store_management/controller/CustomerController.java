package com.storemanager.store_management.controller;




import com.storemanager.store_management.entity.Customer;
import com.storemanager.store_management.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("customer", new Customer()); // Thêm model để bind dữ liệu form
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
    @GetMapping("/search")
    public String searchCustomers(@RequestParam("keyword") String keyword, Model model) {
        List<Customer> customers = customerService.searchCustomers(keyword);
        model.addAttribute("customers", customers);
        model.addAttribute("customer", new Customer()); // Để form thêm khách hàng vẫn hoạt động
        model.addAttribute("keyword", keyword); // Giữ lại từ khóa trong ô tìm kiếm
        return "customers";
    }


}



