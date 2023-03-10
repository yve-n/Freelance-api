package com.cda.freely.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_service", nullable = false)
    private Long id_service;
    @Column(name = "price")
    @JdbcTypeCode(SqlTypes.FLOAT)
    private Float price;

    @Column(name = "name", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User id_user;

    public Service() {
    }

    public Service(Long id_service, Float price, String name, User id_user) {
        this.id_service = id_service;
        this.price = price;
        this.name = name;
        this.id_user = id_user;
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getId() {
        return id_service;
    }

    public void setId(Long id) {
        this.id_service = id;
    }

}