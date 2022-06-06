package com.badin.financialmanagement.service;

import com.badin.financialmanagement.model.TransactionType;

import java.util.List;

public interface TransactionTypeService {
    List<TransactionType> getTransactionTypeOptionList(int accountSize);
    List<TransactionType> getTransactionTypeOptionList(List<TransactionType> excludedOptions);
}
