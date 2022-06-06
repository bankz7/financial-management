package com.badin.financialmanagement.controller;

import com.badin.financialmanagement.model.Category;
import com.badin.financialmanagement.repository.CategoryRepository;
import com.badin.financialmanagement.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public String getAccountList(Model model){
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categories", categoryList);
        return "category/all";
    }

    @GetMapping("/add-form")
    public String getAddPage(Model model){
        Category category = new Category();
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("category", category);
        return "category/form";
    }

    @GetMapping("/edit-form/{category_id}")
    public String getEditPage(Model model, @PathVariable Integer category_id){
        Category category = categoryService.findById(category_id);
        List<Category> categoryList = categoryService.findAll();
        categoryList.remove(category);
        if(category.getParentCategory() != null){
            categoryList.remove(category.getParentCategory());
        }
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("category", category);
        return "category/form";
    }

    @PostMapping("/save")
    public String addAccount(Model model, @ModelAttribute("category") Category category){
        categoryService.save(category);
        return "redirect:/categories";
    }
}
