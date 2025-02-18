package com.storemanager.store_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin") // Định nghĩa chung prefix
public class CategoryController {

    @GetMapping("/category")
    public String index() {
        return "admin/category/index";
    }

    @GetMapping("/add-category")
    public String addCategory() {
        return "admin/category/add";
    }
}
