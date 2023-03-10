package com.cda.freely.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    public Category() {
    }

    public Category(Long id, String name, Family id_family) {
        this.id_category = id;
        this.name = name;
        this.id_family = id_family;
    }

    public Family getId_family() {
        return id_family;
    }

    public void setId_family(Family id_family) {
        this.id_family = id_family;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id_category;
    }

    public void setId(Long id) {
        this.id_category = id;
    }

}