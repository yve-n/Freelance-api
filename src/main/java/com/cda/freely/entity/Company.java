package com.cda.freely.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "company")
public class Company {
    public enum Status {
        ACTIVE, INACTIVE
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_company", nullable = false)
    private Long id;

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
    private User user;


    @OneToMany(mappedBy = "id", orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @Column(name = "company_state")
    @Enumerated(EnumType.STRING)
    private Status companyState;


    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User id_user) {
        this.user = user;
    }




}
