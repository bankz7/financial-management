package com.badin.financialmanagement.service;

import com.badin.financialmanagement.model.Account;
import com.badin.financialmanagement.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<Account> findAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountRepository.findAllByOwner(username);
    }

    @Override
    @Cacheable(value="account", key="#accountId")
    public Account findById(Integer accountId) {
        return accountRepository.getById(accountId);
    }

    @Override
    @CachePut(value="account", key="#account.accountId")
    public Account save(Account account) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        account.setOwner(username);
        return accountRepository.save(account);
    }

    @Override
    @CacheEvict(value="account", key="#account.accountId")
    public void delete(Account account) {
        accountRepository.delete(account);
    }
}
