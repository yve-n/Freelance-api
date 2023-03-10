package com.cda.freely.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_company", nullable = false)
    private Long id_company;

    @Column(name = "name", length = 50,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String name;

    @Column(name = "siret", unique = true, length = 20)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String siret;

    @Column(name = "number", length = 12,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String number;

    @Column(name = "logo")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String logo;

    @Column(name = "tva")
    @JdbcTypeCode(SqlTypes.TINYINT)
    private Boolean tva;

    @Column(name = "siren", unique = true, length = 20, nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String siren;

    @ManyToOne
    @JoinColumn(name = "id_user",nullable = false)
    private User id_user;


    @OneToMany(mappedBy = "id_company", orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status id_status;

    public Status getId_status() {
        return id_status;
    }

    public void setId_status(Status id_status) {
        this.id_status = id_status;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Company() {
    }

    public Company(Long id_company, String name, String siret, String siren, String number, String logo, Boolean tva, User id_user) {
        this.id_company = id_company;
        this.name = name;
        this.siret = siret;
        this.siren = siren;
        this.number = number;
        this.logo = logo;
        this.tva = tva;
        this.id_user = id_user;
    }
    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    public Boolean getTva() {
        return tva;
    }

    public void setTva(Boolean tva) {
        this.tva = tva;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSiren() {
        return siren;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {

        return id_company;
    }

    public void setId(Long id) {

        this.id_company = id;
    }

}