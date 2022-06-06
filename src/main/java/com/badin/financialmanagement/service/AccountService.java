package com.badin.financialmanagement.service;

import com.badin.financialmanagement.model.Account;
import com.badin.financialmanagement.model.Transaction;

import javax.transaction.Transactional;
import java.util.List;

public interface AccountService {
    @Transactional
    public List<Account> findAll();
    @Transactional
    public Account findById(Integer accountId);
    @Transactional
    public Account save(Account account);
    @Transactional
    public void delete(Account account);
}
