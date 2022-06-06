package com.badin.financialmanagement.repository;

import com.badin.financialmanagement.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>, JpaSpecificationExecutor<Transaction> {
    @Query("select new com.badin.financialmanagement.model.ExpenseStats(t.category, sum(t.amount) as amount) from Transaction as t " +
            "where t.transactionType = com.badin.financialmanagement.model.TransactionType.EXPENSE and year(t.transactionDate) = year(:localDate) and month(t.transactionDate) = month(:localDate) and t.fromAccount.owner = :username " +
            "group by t.category order by amount desc")
    public List<ExpenseStats> getMonthlyExpenseStats(@Param("username") String username,@Param("localDate")  LocalDate localDate);

    @Query("select new com.badin.financialmanagement.model.MonthlyData(sum(t.amount), t.transactionType, month(t.transactionDate), year(t.transactionDate)) from Transaction t " +
            "where t.transactionDate is not null and (t.transactionType = com.badin.financialmanagement.model.TransactionType.INCOME or t.transactionType = com.badin.financialmanagement.model.TransactionType.EXPENSE) and t.fromAccount.owner = :username " +
            "group by t.transactionType, month(t.transactionDate), year(t.transactionDate)")
    public List<MonthlyData> getMonthlyData(@Param("username") String username);

    public List<Transaction> findAllByFromAccountOwnerOrderByTransactionDate(String owner);
}
