package com.badin.financialmanagement.service;

import com.badin.financialmanagement.model.*;
import com.badin.financialmanagement.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public interface TransactionService {
    @Transactional
    public List<Transaction> findAll();
    @Transactional
    public List<Transaction> findByCondition(List<TransactionType> transactionSel, List<Integer> categoryIdList, List<Integer> accountIdList, String fromDate, String toDate);
    @Transactional
    public Transaction findById(Integer transactionId);
    @Transactional
    public Transaction save(Transaction transaction);
    @Transactional
    public void delete(int transactionId);
    @Transactional
    public List<ExpenseStats> getMonthlyExpenseStats(String username, LocalDate localDate);
    @Transactional
    public List<MonthlyData> getMonthlyData(String username);
}
