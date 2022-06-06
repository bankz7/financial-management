package com.badin.financialmanagement.service;

import com.badin.financialmanagement.model.Account;
import com.badin.financialmanagement.model.Currency;
import com.badin.financialmanagement.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService{

    @Autowired
    CurrencyRepository currencyRepository;

    @Override
    public List<Currency> findAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return currencyRepository.findAllByOwnerOrOwnerIsNull(username);
    }

    @Override
    public Currency findById(Integer currencyId) {
        return currencyRepository.getById(currencyId);
    }

    @Override
    public Currency save(Currency currency) {
        return currencyRepository.save(currency);
    }

    @Override
    public void delete(Currency currency) {
        currencyRepository.delete(currency);
    }
}
