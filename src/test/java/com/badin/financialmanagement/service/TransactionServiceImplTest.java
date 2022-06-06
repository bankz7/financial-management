package com.badin.financialmanagement.service;

import com.badin.financialmanagement.model.*;
import com.badin.financialmanagement.repository.AccountRepository;
import com.badin.financialmanagement.repository.CategoryRepository;
import com.badin.financialmanagement.repository.CurrencyRepository;
import com.badin.financialmanagement.repository.TransactionRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class TransactionServiceImplTest {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    @InjectMocks
    TransactionServiceImpl transactionService;

    @Mock
    private Authentication auth;

    List<Transaction> transactionList = new ArrayList<>();
    List<Category> categoryList = new ArrayList<>();

    public TransactionServiceImplTest(){

    }
    @Before
    public void createMocks() {
        MockitoAnnotations.initMocks(this);
        when(auth.getCredentials()).thenReturn("mockedPassword");
    }

    @BeforeEach
    public void init() {
        SecurityContextHolder.getContext().setAuthentication(auth);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("bank");
        transactionList = new ArrayList<>();
        Category category = new Category(null,"Category Name", "owner", null);
        category = categoryRepository.save(category);
        Category category2 = new Category(null,"Category Name 2", "owner", null);
        category2 = categoryRepository.save(category2);

        Currency currency = new Currency(null, "Currency", "C", "bank");
        currency = currencyRepository.save(currency);

        Account account = new Account(10, "Account name",  100.0, currency, "bank");
        account = accountRepository.save(account);
        Account account2 = new Account(11, "Account name 2", 100.0, currency, "bank");
        account2 = accountRepository.save(account2);
        Account account3 = new Account(13, "Account name 3", 100.0, currency, "other owner");
        account3 = accountRepository.save(account3);

        Transaction transaction1 = new Transaction(1, TransactionType.INCOME, category, 1.0, null, account, null, LocalDateTime.now());
        Transaction transaction2 = new Transaction(2, TransactionType.INCOME, category2, 1.0, null, account2, null, LocalDateTime.now());
        Transaction transaction3 = new Transaction(3, TransactionType.EXPENSE, category, 101.5, null, account, null, LocalDateTime.now());
        Transaction transaction4 = new Transaction(4, TransactionType.TRANSFER, category2, 5.0, null, account, account2, LocalDateTime.now());
        Transaction transaction5 = new Transaction(5, TransactionType.EXPENSE, category2, 101.5, null, account3, null, LocalDateTime.now());
        Transaction transaction6 = new Transaction(6, TransactionType.EXPENSE, category2, 10.5, null, account2, null, LocalDateTime.now());

        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);
        transactionRepository.save(transaction3);
        transactionRepository.save(transaction4);
        transactionRepository.save(transaction5);
        transactionRepository.save(transaction6);

        transactionList.add(transaction1);
        transactionList.add(transaction2);
        transactionList.add(transaction3);
        transactionList.add(transaction4);
        transactionList.add(transaction5);
        transactionList.add(transaction6);
        categoryList.add(category);
        categoryList.add(category2);
        System.out.println(111);
    }

    @Test
    @DisplayName("Should return list of transactions based on active user")
    public void testActiveUserTransaction() {
        for(Transaction transaction : transactionList){
            System.out.println(transaction);
        }
        for(Transaction transaction : transactionService.findAll()){
            System.out.println(transaction);
        }
        List<Transaction> expectedTransactionList = new ArrayList<>();
        expectedTransactionList.add(transactionList.get(0));
        expectedTransactionList.add(transactionList.get(1));
        expectedTransactionList.add(transactionList.get(2));
        expectedTransactionList.add(transactionList.get(3));
        expectedTransactionList.add(transactionList.get(5));
        assertIterableEquals(expectedTransactionList, transactionService.findAll());
    }

    @Test
    @DisplayName("Should return list of transactions based on transaction type condition")
    public void shouldReturnBasedOnTransactionType() {

        //Expected
        List<Transaction> expenseTransactionList = new ArrayList<>();
        expenseTransactionList.add(transactionList.get(2));
        expenseTransactionList.add(transactionList.get(5));

        List<Transaction> incomeTransactionList = new ArrayList<>();
        incomeTransactionList.add(transactionList.get(0));
        incomeTransactionList.add(transactionList.get(1));

        List<Transaction> incomeAndTranferTransactionList = new ArrayList<>();
        incomeAndTranferTransactionList.addAll(incomeTransactionList);
        incomeAndTranferTransactionList.add(transactionList.get(3));
        //actual
        List<Transaction> actualExpenseTransactionList = transactionService.findByCondition(Arrays.asList(TransactionType.EXPENSE), null, null, null, null);
        List<Transaction> actualIncomeTransactionList = transactionService.findByCondition(Arrays.asList(TransactionType.INCOME), null, null, null, null);
        List<Transaction> actualIncomeAndTransferTransactionList = transactionService.findByCondition(Arrays.asList(TransactionType.INCOME, TransactionType.TRANSFER), null, null, null, null);

        assertIterableEquals(expenseTransactionList, actualExpenseTransactionList);
        assertIterableEquals(incomeTransactionList, actualIncomeTransactionList);
        assertIterableEquals(incomeAndTranferTransactionList, actualIncomeAndTransferTransactionList);

    }

    @Test
    @DisplayName("Should return list of transactions based on category condition")
    public void shouldReturnBasedOnCategory() {
        List<Transaction> expectedCategoryOne = new ArrayList<>();
        expectedCategoryOne.add(transactionList.get(0));
        expectedCategoryOne.add(transactionList.get(2));
        List<Transaction> expectedCategoryTwo = new ArrayList<>();
        expectedCategoryTwo.add(transactionList.get(1));
        expectedCategoryTwo.add(transactionList.get(3));
        expectedCategoryTwo.add(transactionList.get(5));
        //actual
        List<Transaction> actual1TransactionList = transactionService.findByCondition(null, Arrays.asList(categoryList.get(0).categoryId), null, null, null);
        List<Transaction> actual2TransactionList = transactionService.findByCondition(null, Arrays.asList(categoryList.get(1).categoryId), null, null, null);
        assertEquals(expectedCategoryOne, actual1TransactionList);
        assertEquals(expectedCategoryTwo, actual2TransactionList);
    }

    @Test
    public void getMonthlyExpenseStats() {

        List<MonthlyData> monthlyDataList = transactionService.getMonthlyData("bank");
        for(MonthlyData monthlyData:monthlyDataList){
            if(monthlyData.getTransactionType() == TransactionType.EXPENSE){
                assertEquals(112.0, monthlyData.getAmount());
            }
            if(monthlyData.getTransactionType() == TransactionType.INCOME){
                assertEquals(2.0, monthlyData.getAmount());
            }
        }
    }

}