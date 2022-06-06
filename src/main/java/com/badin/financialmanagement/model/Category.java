package com.badin.financialmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "category", uniqueConstraints = {@UniqueConstraint(columnNames = {"category_id"})})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    public Integer categoryId;

    @Size(min = 1, message = "Category name is required")
    @Column(name = "category_name", nullable = false)
    public String categoryName;

    @Size(min = 1, message = "Owner is required")
    @Column(name = "owner", nullable = false)
    public String owner;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "parent_category_id", referencedColumnName = "category_id")
    public Category parentCategory;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Category that = (Category) o;
        return categoryId != null && Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
