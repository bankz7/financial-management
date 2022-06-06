package com.badin.financialmanagement.controller;

import com.badin.financialmanagement.model.ExpenseStats;
import com.badin.financialmanagement.model.MonthlyData;
import com.badin.financialmanagement.model.Transaction;
import com.badin.financialmanagement.model.TransactionType;
import com.badin.financialmanagement.repository.TransactionRepository;
import com.badin.financialmanagement.service.TransactionService;
import com.badin.financialmanagement.specification.TransactionSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.badin.financialmanagement.specification.TransactionSpecification.*;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    TransactionService transactionService;

    @GetMapping("")
    public String getStatisticsPage(Model model, Authentication authentication){
        String username = authentication.getName();
        LocalDate now = LocalDate.now();
        List<ExpenseStats> transactionList = transactionService.getMonthlyExpenseStats(username, now);
        model.addAttribute("expenses", transactionList);
        List<MonthlyData> monthlyData = transactionService.getMonthlyData(username);
        model.addAttribute("monthlyData", monthlyData);

        return "statistics/main";
    }

}
