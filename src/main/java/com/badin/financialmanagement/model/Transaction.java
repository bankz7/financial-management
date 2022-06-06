package com.badin.financialmanagement.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "transaction", uniqueConstraints = {@UniqueConstraint(columnNames = {"transaction_id"})})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", nullable = false)
    public Integer transactionId;

    @Column(name="transaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    public TransactionType transactionType;

    @OneToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name="category_id", referencedColumnName = "category_id", nullable = false)
    public Category category;

    @Column(name="amount", nullable = false)
    public Double amount;

    @Column(name="exchange_rate")
    public Double exchangeRate;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name="from_account_id", referencedColumnName = "account_id", nullable = false)
    public Account fromAccount;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name="to_account_id", referencedColumnName = "account_id")
    public Account toAccount;

    @Column(name="transaction_date", nullable = false)
    public LocalDateTime transactionDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Transaction that = (Transaction) o;
        return transactionId != null && Objects.equals(transactionId, that.transactionId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
