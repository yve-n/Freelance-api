package com.cda.freely.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status", nullable = false)
    private Long id_status;

    @Column(name = "company_state")
    @JdbcTypeCode(SqlTypes.TINYINT)
    private Boolean company_state;

    @Column(name = "user_account_state")
    @JdbcTypeCode(SqlTypes.TINYINT)
    private Boolean user_account_state;

    @Column(name = "user_availability")
    @JdbcTypeCode(SqlTypes.TINYINT)
    private Boolean user_availability;

    @OneToMany(mappedBy = "id_status", orphanRemoval = true)
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "id_status", orphanRemoval = true)
    private List<Company> companies = new ArrayList<>();

    public Status() {
    }

    public Status(Long id_status, Boolean company_state, Boolean user_account_state, Boolean user_availability) {
        this.id_status = id_status;
        this.company_state = company_state;
        this.user_account_state = user_account_state;
        this.user_availability = user_availability;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Boolean getUser_availability() {
        return user_availability;
    }

    public void setUser_availability(Boolean user_availability) {
        this.user_availability = user_availability;
    }

    public Boolean getUser_account_state() {
        return user_account_state;
    }

    public void setUser_account_state(Boolean user_account_state) {
        this.user_account_state = user_account_state;
    }

    public Boolean getCompany_state() {
        return company_state;
    }

    public void setCompany_state(Boolean company_state) {
        this.company_state = company_state;
    }

    public Long getId() {
        return id_status;
    }

    public void setId(Long id) {
        this.id_status = id;
    }

}