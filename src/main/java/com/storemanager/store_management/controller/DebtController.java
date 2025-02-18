package com.storemanager.store_management.controller;

import com.storemanager.store_management.entity.Customer;
import com.storemanager.store_management.entity.DebtRecord;
import com.storemanager.store_management.entity.User;
import com.storemanager.store_management.service.CustomerService;
import com.storemanager.store_management.service.DebtRecordService;
import com.storemanager.store_management.service.UserServiceIpml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/debts")
public class DebtController {
    @Autowired
    private DebtRecordService debtRecordService;

    @Autowired
    private CustomerService customerService;

    private static final double CREDIT_LIMIT = 10000.00;
    @Autowired
    private UserServiceIpml userServiceIpml;

    @GetMapping
    public String listDebts(@RequestParam(value = "page", defaultValue = "0") int page,
                            @RequestParam(value = "size", defaultValue = "10") int size,
                            Model model) {
        // Tạo Pageable với trang và kích thước
        Pageable pageable = PageRequest.of(page, size);

        // Lấy tất cả khách hàng phân trang
        Page<Customer> customersPage = customerService.getAllCustomers(pageable);
        model.addAttribute("customers", customersPage.getContent()); // Lấy dữ liệu khách hàng của trang hiện tại
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", customersPage.getTotalPages());
        model.addAttribute("size", size);

        model.addAttribute("debtRecords", debtRecordService.getAllDebtRecords(pageable));
        model.addAttribute("debtRecord", new DebtRecord());
        return "debts";
    }

    @PostMapping("/add")
    public String addDebt(@ModelAttribute("debtRecord") DebtRecord debtRecord,
                          RedirectAttributes redirectAttributes) {
        // Lấy thông tin User từ Security Context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            User user = userServiceIpml.findByUsername(username);
            // Gán User cho DebtRecord
            debtRecord.setUser(user);
        } else {
            redirectAttributes.addFlashAttribute("error", "Không thể xác định người dùng!");
            return "redirect:/debts";
        }

        // Tiếp tục xử lý logic thêm DebtRecord (nếu có)
        return "redirect:/debts"; // hoặc trang đích sau khi thêm thành công
    }


    @GetMapping("/customer/{customerId}")
    public String viewCustomerDebts(@PathVariable Long customerId, Model model) {
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            model.addAttribute("error", "Khách hàng không tồn tại!");
            return "debts";
        }
        List<DebtRecord> debtRecords = debtRecordService.getDebtsByCustomer(customerId);
        boolean overLimit = customer.getDebtBalance() > CREDIT_LIMIT;
        model.addAttribute("customer", customer);
        model.addAttribute("debtRecords", debtRecords);
        model.addAttribute("overLimit", overLimit);
        return "customer_debts";
    }
}
