package com.badin.financialmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "account", uniqueConstraints = {@UniqueConstraint(columnNames = {"account_id"})})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    public Integer accountId;

    @Size(min = 1, message = "Account name is required")
    @Column(name = "account_name", nullable = false)
    public String accountName;

    @Column(name = "balance", nullable = false)
    public Double balance = 0.0;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_id", referencedColumnName = "currency_id", nullable = false)
    public Currency currency;

    @Size(min = 1, message = "Owner is required")
    @Column(name = "owner", nullable = false)
    public String owner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account that = (Account) o;
        return accountId != null && Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
