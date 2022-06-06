package com.badin.financialmanagement.service;

import com.badin.financialmanagement.model.TransactionType;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionTypeServiceImpl implements TransactionTypeService{
    @Override
    public List<TransactionType> getTransactionTypeOptionList(int accountSize) {
        List<TransactionType> excludedOptions = new ArrayList<>();
        if(accountSize < 2) {
            excludedOptions.add(TransactionType.TRANSFER);
        }
        return getTransactionTypeOptionList(excludedOptions);
    }

    @Override
    public List<TransactionType> getTransactionTypeOptionList(List<TransactionType> excludedOptions){
        if(excludedOptions == null || excludedOptions.isEmpty()){
            return Arrays.asList(TransactionType.values());
        }else {
            List<TransactionType> transactionTypeList = new ArrayList<>();
            Set<TransactionType> excludedSet = new HashSet<>(excludedOptions);
            for(TransactionType transactionType : TransactionType.values()){
                if(!excludedSet.contains(transactionType)){
                    transactionTypeList.add(transactionType);
                }
            }
            return transactionTypeList;
        }
    }
}
