package com.badin.financialmanagement.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "currency", uniqueConstraints = {@UniqueConstraint(columnNames = {"currency_id"})})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Currency implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currency_id", nullable = false)
    public Integer currencyId;

    @Size(min = 1, message = "Currency name is required")
    @Column(name = "currency_name", nullable = false)
    public String currencyName;

    @Size(min = 1, max = 1, message = "Symbol should be 1-2 character(s)")
    @Column(name = "symbol", nullable = false)
    public String symbol = null;

    @Size(min = 1, message = "Owner is required")
    @Column(name = "owner", nullable = false)
    public String owner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Currency that = (Currency) o;
        return currencyId != null && Objects.equals(currencyId, that.currencyId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
