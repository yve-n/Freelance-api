package com.cda.freely.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address", nullable = false)
    private Long id_address;

    @Column(name = "address", length = 40, nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String address;

    @Column(name = "zip_code", length = 32, nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String zip_code;

    @Column(name = "city", length = 32,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String city;

    @Column(name = "country", length = 32,nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String country;

    @ManyToOne
    @JoinColumn(name = "id_company")
    private Company id_company;

    public Company getId_company() {
        return id_company;
    }

    public void setId_company(Company id_company) {
        this.id_company = id_company;
    }

    public Address() {
    }

    public Address(Long id, String address, String zip_code, String city, String country, Company id_company) {
        this.id_address = id;
        this.address = address;
        this.zip_code = zip_code;
        this.city = city;
        this.country = country;
        this.id_company = id_company;
    }
    public Long getId() {

        return id_address;
    }

    public void setId(Long id) {
        this.id_address = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



}