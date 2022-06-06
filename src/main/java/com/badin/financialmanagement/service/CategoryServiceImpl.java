package com.badin.financialmanagement.service;

import com.badin.financialmanagement.model.Account;
import com.badin.financialmanagement.model.Category;
import com.badin.financialmanagement.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return categoryRepository.findAllByOwnerOrOwnerIsNull(username);
    }

    @Override
    public Category findById(Integer categoryId) {
        return categoryRepository.getById(categoryId);
    }

    @Override
    public Category save(Category category) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        category.setOwner(username);
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Category category) {
        categoryRepository.delete(category);
    }
}
