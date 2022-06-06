package com.badin.financialmanagement.service;

import com.badin.financialmanagement.model.Account;
import com.badin.financialmanagement.model.Category;

import javax.transaction.Transactional;
import java.util.List;

public interface CategoryService {
    @Transactional
    public List<Category> findAll();
    @Transactional
    public Category findById(Integer categoryId);
    @Transactional
    public Category save(Category category);
    @Transactional
    public void delete(Category category);
}
