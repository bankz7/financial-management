package com.badin.financialmanagement.controller;

import com.badin.financialmanagement.model.Account;
import com.badin.financialmanagement.model.Category;
import com.badin.financialmanagement.model.Transaction;
import com.badin.financialmanagement.model.TransactionType;
import com.badin.financialmanagement.repository.AccountRepository;
import com.badin.financialmanagement.repository.CategoryRepository;
import com.badin.financialmanagement.service.AccountService;
import com.badin.financialmanagement.service.CategoryService;
import com.badin.financialmanagement.service.TransactionService;
import com.badin.financialmanagement.service.TransactionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @Autowired
    TransactionTypeService transactionTypeService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    AccountService accountService;

    @GetMapping("")
    public String getTransactionList(Model model, @RequestParam(name="accounts", required=false) List<Integer> accountSel, @RequestParam(name="transactions", required=false) List<TransactionType> transactionSel, @RequestParam(name="category", required=false) List<Integer> categoryIdList,
                                 @RequestParam(name="fromDate", required = false) String fromDate, @RequestParam(name="toDate", required = false) String toDate){
        List<Account> accountList = accountService.findAll();
        List<Transaction> transactionList = transactionService.findByCondition(transactionSel, categoryIdList, accountSel, fromDate, toDate);
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("transactions", transactionList);
        model.addAttribute("accountList", accountList);
        model.addAttribute("categoryList", categoryList);
        return "transaction/all";
    }

    @GetMapping("/add-form")
    public String getAddPage(Model model, @RequestParam(name = "accountId", required = false) String accountId){
        Transaction transaction = new Transaction();
        if(!StringUtils.isEmpty(accountId)){
            Account fromAccount = accountService.findById(Integer.parseInt(accountId));
            transaction.setFromAccount(fromAccount);
        }
        List<Category> categoryList = categoryService.findAll();
        List<Account> accountList = accountService.findAll();
        List<TransactionType> transactionTypeList = transactionTypeService.getTransactionTypeOptionList(accountList.size());
        model.addAttribute("transactionTypeList", transactionTypeList);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("accountList", accountList);
        model.addAttribute("transaction", transaction);
        return "transaction/form";
    }

    @GetMapping("/delete/{transaction_id}")
    public String delete(Model model, @PathVariable Integer transaction_id){
        transactionService.delete(transaction_id);
        return "redirect:/transactions";
    }

    @GetMapping("/edit-form/{transaction_id}")
    public String getEditPage(Model model, @PathVariable Integer transaction_id){
        Transaction transaction = transactionService.findById(transaction_id);
        List<Category> categoryList = categoryService.findAll();
        List<Account> accountList = accountService.findAll();
        List<TransactionType> transactionTypeList = transactionTypeService.getTransactionTypeOptionList(accountList.size());
        model.addAttribute("transactionTypeList", transactionTypeList);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("accountList", accountList);
        model.addAttribute("transaction", transaction);
        return "transaction/form";
    }

    @PostMapping("/save")
    @Transactional
    public String addTransaction(Model model, @ModelAttribute("transaction") Transaction transaction, Authentication authentication){
        transactionService.save(transaction);
        return "redirect:/transactions";
    }
}
