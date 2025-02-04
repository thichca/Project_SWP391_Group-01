package com.storemanager.store_management.controller;

import com.storemanager.store_management.entity.Customer;
import com.storemanager.store_management.entity.DebtRecord;
import com.storemanager.store_management.service.CustomerService;
import com.storemanager.store_management.service.DebtRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping
    public String listDebts(Model model) {
        model.addAttribute("debtRecords", debtRecordService.getAllDebtRecords());
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("debtRecord", new DebtRecord());
        return "debts";
    }

    @PostMapping("/add")
    public String addDebt(@ModelAttribute("debtRecord") DebtRecord debtRecord,
                          RedirectAttributes redirectAttributes) {
        Customer customer = customerService.getCustomerById(debtRecord.getCustomer().getId());
        if (customer.getDebtBalance() + debtRecord.getAmount() > CREDIT_LIMIT) {
            redirectAttributes.addFlashAttribute("error", "Công nợ vượt hạn mức! Vui lòng kiểm tra lại.");
            return "redirect:/debts";
        }
        debtRecordService.saveDebtRecord(debtRecord);
        redirectAttributes.addFlashAttribute("message", "Ghi nhận công nợ thành công!");
        return "redirect:/debts";
    }

    @GetMapping("/delete/{id}")
    public String deleteDebt(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        debtRecordService.deleteDebtRecord(id);
        redirectAttributes.addFlashAttribute("message", "Xóa công nợ thành công!");
        return "redirect:/debts";
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
