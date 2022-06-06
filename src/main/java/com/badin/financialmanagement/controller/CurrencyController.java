package com.badin.financialmanagement.controller;

import com.badin.financialmanagement.model.Currency;
import com.badin.financialmanagement.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/currencies")
public class CurrencyController {
    @Autowired
    CurrencyService currencyService;

    @GetMapping("")
    public String getAccountList(Model model){
        List<Currency> currencyList = currencyService.findAll();
        model.addAttribute("currencies", currencyList);
        return "currency/all";
    }

    @GetMapping("/add-form")
    public String getAddPage(Model model){
        Currency c = new Currency();
        model.addAttribute("currency", c);
        return "currency/form";
    }

    @GetMapping("/delete/{currency_id}")
    public String delete(Model model, @PathVariable Integer currency_id){
        Currency account = currencyService.findById(currency_id);
        currencyService.delete(account);
        return "redirect:/currencies";
    }

    @GetMapping("/edit-form/{currency_id}")
    public String getEditPage(Model model, @PathVariable Integer currency_id){
        Currency currency = currencyService.findById(currency_id);
        model.addAttribute("currency", currency);
        return "currency/form";
    }

    @PostMapping("/save")
    public String addAccount(Model model, @ModelAttribute("currency") Currency currency, Authentication authentication){
        currency.setOwner(authentication.getName());
        currencyService.save(currency);
        return "redirect:/currencies";
    }

}
