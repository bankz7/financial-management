package com.badin.financialmanagement.service;

import com.badin.financialmanagement.model.Currency;

import javax.transaction.Transactional;
import java.util.List;

public interface CurrencyService {
    @Transactional
    public List<Currency> findAll();
    @Transactional
    public Currency findById(Integer currencyId);
    @Transactional
    public Currency save(Currency currency);
    @Transactional
    public void delete(Currency currency);
}
