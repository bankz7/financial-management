package com.badin.financialmanagement.repository;

import com.badin.financialmanagement.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    List<Currency> findAllByOwnerOrOwnerIsNull(String owner);
}
