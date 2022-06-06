package com.badin.financialmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@AllArgsConstructor
@Setter
@Getter
public class ExpenseStats {
    Category category;
    double sum;
}
