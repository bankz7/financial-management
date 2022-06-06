package com.badin.financialmanagement.repository;

import com.badin.financialmanagement.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    public List<Category> findAllByOwnerOrOwnerIsNull(String owner);
}
