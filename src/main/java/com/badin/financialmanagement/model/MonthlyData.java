package com.badin.financialmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MonthlyData {
    double amount;
    TransactionType transactionType;
    int month;
    int year;
}
