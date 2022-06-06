package com.badin.financialmanagement.specification;

import com.badin.financialmanagement.model.Transaction;
import com.badin.financialmanagement.model.TransactionType;
import org.springframework.data.jpa.domain.Specification;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionSpecification {
    public static Specification<Transaction> transactionTypesIn(List<TransactionType> transactionTypeList) {
        return (transaction, cq, cb) -> {
            if(transactionTypeList == null || transactionTypeList.size() == 0){
                return cb.and();
            }
            return cb.in(transaction.get("transactionType")).value(transactionTypeList);};
    }

    public static Specification<Transaction> fromOrToAccount(List<Integer> accountIdList) {
        return (transaction, cq, cb) -> {
            if(accountIdList == null || accountIdList.size() == 0){
                return cb.and();
            }
            return cb.or(cb.in(transaction.get("fromAccount").get("accountId")).value(accountIdList),
                    cb.in(transaction.get("toAccount").get("accountId")).value(accountIdList));};
    }
    public static Specification<Transaction> isCategory(List<Integer> categoryIdList) {
        return (transaction, cq, cb) -> {
            if (categoryIdList == null || categoryIdList.size() == 0) {
                return cb.and();
            }
            return cb.in(transaction.get("category").get("categoryId")).value(categoryIdList);
        };
    }
    public static Specification<Transaction> fromUser(String username) {
        return (transaction, cq, cb) -> cb.equal(transaction.get("fromAccount").get("owner"), username);
    }

    public static Specification<Transaction> beforeDate(String fromDate){
        return (transaction, cq, cb) -> {
            if(StringUtils.isEmptyOrWhitespace(fromDate)){
                return cb.and();
            }
            return cb.greaterThanOrEqualTo(transaction.get("transactionDate"), LocalDate.parse(fromDate));
        };
    }

    public static Specification<Transaction> afterDate(String toDate){
        return (transaction, cq, cb) -> {
            if(StringUtils.isEmptyOrWhitespace(toDate)){
                return cb.and();
            }
            return cb.greaterThanOrEqualTo(transaction.get("transactionDate"), LocalDate.parse(toDate));
        };
    }

    private static void appendOrder(CriteriaQuery<?> query, Order order) {
        List<Order> orders = new ArrayList<>(query.getOrderList());
        orders.add(0, order);
        query.orderBy(orders);
    }

    public static Specification<Transaction> orderByTransactionDate() {
        return (transaction, cq, cb) -> {
            appendOrder(cq, cb.asc(cb.sum(transaction.get("transactionDate"))));
            return null;
        };
    }
}
