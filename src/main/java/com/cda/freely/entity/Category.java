package com.cda.freely.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category", nullable = false)
    private Long id_category;

    @Column(name = "name", length = 100, nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_family")
    private Family id_family;

    @ManyToMany(mappedBy = "categories")
    private Collection<UserCategory> userCategories = new ArrayList<>();

    public Collection<UserCategory> getUserCategories() {
        return userCategories;
    }

    public void setUserCategories(Collection<UserCategory> userCategories) {
        this.userCategories = userCategories;
    }

    public Family getId_family() {
        return id_family;
    }

    public void setId_family(Family id_family) {
        this.id_family = id_family;
    }


}
