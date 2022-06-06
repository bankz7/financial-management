package com.badin.financialmanagement.controller;

import com.badin.financialmanagement.model.Account;
import com.badin.financialmanagement.model.Currency;
import com.badin.financialmanagement.repository.CurrencyRepository;
import com.badin.financialmanagement.service.AccountService;
import com.badin.financialmanagement.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    AccountService accountService;

    @Autowired
    CurrencyService currencyService;

    @GetMapping("")
    public String getAccountList(Model model){
        List<Account> accountList = accountService.findAll();
        model.addAttribute("accounts", accountList);
        return "account/all";
    }

    @GetMapping("/add-form")
    public String getAddPage(Model model){
        model.addAttribute("account", new Account());
        List<Currency> currencyList = currencyService.findAll();
        model.addAttribute("currencies", currencyList);
        return "account/form";
    }

    @GetMapping("/delete/{account_id}")
    public String delete(Model model, @PathVariable Integer account_id){
        Account account = accountService.findById(account_id);
        accountService.delete(account);
        return "redirect:/accounts";
    }

    @GetMapping("/edit-form/{account_id}")
    public String getEditPage(Model model, @PathVariable Integer account_id){
        Account ac = accountService.findById(account_id);
        model.addAttribute("account", ac);
        List<Currency> currencyList = currencyService.findAll();
        model.addAttribute("currencies", currencyList);
        return "account/form";
    }

    @PostMapping("/save")
    public String addAccount(Model model, @ModelAttribute("account") Account account){
        accountService.save(account);
        return "redirect:/accounts";
    }


}
