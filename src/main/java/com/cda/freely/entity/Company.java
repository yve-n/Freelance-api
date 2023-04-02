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
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "company_state")
    @JdbcTypeCode(SqlTypes.TINYINT)
    private Integer company_state;


    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }




}
