package com.badin.financialmanagement.service;

import com.badin.financialmanagement.model.*;
import com.badin.financialmanagement.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.badin.financialmanagement.specification.TransactionSpecification.*;
import static com.badin.financialmanagement.specification.TransactionSpecification.afterDate;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<Transaction> findAll(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return transactionRepository.findAllByFromAccountOwnerOrderByTransactionDate(username);
    }


    public List<Transaction> findByCondition(List<TransactionType> transactionSel, List<Integer> categoryIdList, List<Integer> accountIdList, String fromDate, String toDate){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Specification<Transaction> specification = where(
                                                            fromUser(username)
                                                            .and(transactionTypesIn(transactionSel))
                                                            .and(isCategory(categoryIdList))
                                                            .and(fromOrToAccount(accountIdList))
                                                            .and(beforeDate(fromDate))
                                                            .and(afterDate(toDate))
                                                    );
        return transactionRepository.findAll(specification, Sort.by(Sort.Direction.DESC, "transactionDate"));
    }

    @Override
    public Transaction findById(Integer transactionId){
        return transactionRepository.getById(transactionId);
    }

    @Override
    public Transaction save(Transaction transaction){
        double amount = transaction.getAmount();
        if(transaction.getTransactionType() == TransactionType.INCOME){
            Account fromAccount = transaction.getFromAccount();
            fromAccount.setBalance(fromAccount.getBalance() + amount);
        }
        else if(transaction.getTransactionType() == TransactionType.EXPENSE){
            Account fromAccount = transaction.getFromAccount();
            fromAccount.setBalance(fromAccount.getBalance() - amount);
        }
        else if(transaction.getTransactionType() == TransactionType.TRANSFER){
            Account fromAccount = transaction.getFromAccount();
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            Account toAccount = transaction.getToAccount();
            if(fromAccount.currency.currencyId.equals(toAccount.currency.currencyId)){
                toAccount.setBalance(toAccount.getBalance() + amount);
            }else{
                Double exchangeRate = transaction.getExchangeRate();
                if(exchangeRate == null){
                    throw new RuntimeException("Expect transaction rate for transferring to different currency");
                }
                toAccount.setBalance(toAccount.getBalance() + amount*exchangeRate);
            }
        }
        transaction.setTransactionDate(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    @Override
    public void delete(int transactionId){
        Transaction transaction = findById(transactionId);
        Double amount = transaction.getAmount();
        if(transaction.getTransactionType() == TransactionType.INCOME){
            Account fromAccount = transaction.getFromAccount();
            fromAccount.setBalance(fromAccount.getBalance() - amount);
        }
        else if(transaction.getTransactionType() == TransactionType.EXPENSE){
            Account fromAccount = transaction.getFromAccount();
            fromAccount.setBalance(fromAccount.getBalance() + amount);
        }
        else if(transaction.getTransactionType() == TransactionType.TRANSFER){
            Account fromAccount = transaction.getFromAccount();
            fromAccount.setBalance(fromAccount.getBalance() + amount);
            Account toAccount = transaction.getToAccount();
            if(fromAccount.currency.currencyId.equals(toAccount.currency.currencyId)){
                toAccount.setBalance(toAccount.getBalance() - amount);
            }else{
                Double exchangeRate = transaction.getExchangeRate();
                if(exchangeRate == null){
                    throw new RuntimeException("Expect transaction rate not to be null");
                }
                toAccount.setBalance(toAccount.getBalance() - amount*exchangeRate);
            }
        }
        transactionRepository.delete(transaction);
    }

    @Override
    public List<ExpenseStats> getMonthlyExpenseStats(String username, LocalDate localDate) {
        return transactionRepository.getMonthlyExpenseStats(username, localDate);
    }

    @Override
    public List<MonthlyData> getMonthlyData(String username) {
        return transactionRepository.getMonthlyData(username);
    }
}
